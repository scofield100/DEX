package com.delivery2go.entity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.R;
import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.dish.FragmentDishList;
import com.delivery2go.base.dish.ViewModelDishList;
import com.delivery2go.order.OnOrderChangeListener;
import com.enterlib.data.IEntityCursor;
import com.enterlib.databinding.BindingResources;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.widgets.ProgressLayout;

public class FragmentWelcomeDishList extends FragmentDishList {

	private OnOrderChangeListener activity;

	public FragmentWelcomeDishList(){
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (OnOrderChangeListener)activity;
	}

	@Override
	public void onDetach() {
		this.activity = null;

		super.onDetach();
	}

	@Override
	protected BindingResources getBindingResources() {
		// TODO Auto-generated method stub
		return super.getBindingResources()
				.put("CurrencyConverter",  new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
				.put("ImageConverter", new ThumbailDrawableConverter(){
					@Override
					public Object convert(Object value)
							throws ConversionFailException {
						if(value instanceof Integer && (Integer)value == 0){
							return getResources().getDrawable(R.drawable.ic_action_like);
						}
						return super.convert(value);
					}
				})
				.put("RankingConverter", new RantingConverter(getActivity()))
				.put("TimeConverter",new com.enterlib.converters.DateToStringConverter("hh:mm a"));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.dish_fragment_list, container, false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
				
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
		
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}
	
	
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {		
		switch (requestCode) {
		case ViewModelDishList.ENTITY_DETAILS:					
				Intent i =new  Intent(getActivity().getApplicationContext(), ActivityOrderDish.class);
				i.putExtra(ActivityDishDetails.ID, new int[]{((Dish)data).Id});
				startActivityForResult(i,FragmentDishEdit.DISH_EDITED);			
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FragmentDishEdit.DISH_EDITED && resultCode == Activity.RESULT_OK){
			activity.onOrderChanged();
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISH");
		IDishRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISH");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishRepository();
		}
		return new ViewModelWelcomeDishList(this, repository);

	}
	
	class ViewModelWelcomeDishList  extends ViewModelDishList{

		public ViewModelWelcomeDishList(IDataView view,
				IDishRepository dishRepository) {
			super(view, dishRepository);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected IEntityCursor<Dish> createCursor()
				throws InvalidOperationException {
			
			return dishRepository.getCursor("IsAvailable = true and (AvailableCount = null or AvailableCount > 0)");
		}
		
	}
}
