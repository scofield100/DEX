package com.delivery2go.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

import com.delivery2go.R;
import com.delivery2go.R.id;
import com.delivery2go.R.layout;
import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.dish.FragmentDishList;
import com.delivery2go.base.dish.ViewModelDishList;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.Entity;
import com.delivery2go.entity.ActivityOrderDish;
import com.enterlib.data.IClosable;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IRepository;
import com.enterlib.data.WrapperRepository;
import com.enterlib.databinding.AdapterEntityCursorForm;
import com.enterlib.databinding.BindingResources;
import com.enterlib.fields.Field;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.SelectionCommand;

public class FragmentDishWhatsGoodList extends FragmentDishList {
	
	ActivitySearchEntities activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setOnStartViewModelEnable(false);
	}
	
	@Override
	public void onAttach(Activity activity) {	
		super.onAttach(activity);
		
		this.activity = (ActivitySearchEntities)activity;
	}
	
	@Override
	public void onDetach() {	
		super.onDetach();
		
		this.activity = null;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
			return inflater.inflate(R.layout.home_fragment_whatsgood_list, container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
		super.onCreateOptionsMenu(menu, inflater);
				

		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
		
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.refresh){
			activity.load();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("CurrencyConverter",  new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
		.put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.adapter_image),(int)getResources().getDimension(R.dimen.adapter_image)))
		.put("RankingConverter", new RantingConverter(getActivity()));
	}
	
	

	
	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelDishWhatsGood(this,  activity.getViewModel());
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
	
	public class ViewModelDishWhatsGood extends DataViewModel implements Observer, IClosable{
		
		private ViewModelSearchEntity parentViewModel;
		ArrayList<Dish>dishes = new ArrayList<Dish>();
		private boolean loadData;
		
		public ViewModelDishWhatsGood(IDataView view, ViewModelSearchEntity parentViewModel) {
			super(view);
			
			this.parentViewModel = parentViewModel;
			parentViewModel.addObserver(this);
		}
		
		public ArrayList<Dish>getItems(){
			return dishes;
		}
		
		public String Status;
		
		@Override
		public void update(Observable observable, Object data) {
			loadData = true;
			load();	
		}
		
		@Override
		public void load() {
			if(!loadData)
				return;
			
			super.load();
			
			loadData = false;
		}
					
		@Override
		protected boolean loadAsync() throws Exception {						
			dishes = new ArrayList<Dish>();
			
			ArrayList<Entity> entities = parentViewModel.getItems();
			for (Entity entity : entities) {
				for (Dish dish : entity.getDishs()
						.where("InWhatsGood = true and IsAvailable = true and (AvailableCount = null or AvailableCount > 0)")
						.orderBy("Ranking DESC")) {
					
					dishes.add(dish);					
				}
			}
			
			Collections.sort(dishes, new Comparator<Dish>() {

				@Override
				public int compare(Dish lhs, Dish rhs) {
					int value;
					if(lhs.Ranking == rhs.Ranking)
						value = 0;					
					if(lhs.Ranking == null && rhs.Ranking !=null)
						value = -1;
					else if(rhs.Ranking == null && lhs.Ranking!=null)
						value = 1;
					else 
						value = lhs.Ranking.compareTo(rhs.Ranking);
					
					return -1*value;
				}
			});
			
			Status = String.format(getView().getString(R.string.status_search_dishes), dishes.size());
			return true;
		}

		@Override
		public void close() {
			parentViewModel.deleteObserver(this);
			
		}
		
		public SelectionCommand Selection = new SelectionCommand() {
			
			@Override
			public void invoke(Field field, AdapterView<?> adapterView, View itemView,
					int position, long id) {
				
				Dish dish = (Dish) adapterView.getItemAtPosition(position);
				getNavigator().navigateTo(ViewModelDishList.ENTITY_DETAILS, null, dish);
				
			}
		};
	}
	
	
//	@Override
//	public void onDataLoaded() {	
//		super.onDataLoaded();
//		
//		ViewModelDishList vm = (ViewModelDishList)getViewModel();
//		IEntityCursor<?> cursor = vm.getCursor();
//		if(cursor == null){
//			gallery.setAdapter(null);
//			if(detailsListener!=null){
//				detailsListener.showItemDetails(null);			
//			}
//		}
//		else{
//			AdapterEntityCursorForm adapter = new AdapterEntityCursorForm(getActivity(), R.layout.adapter_dish_whatsgood, (IEntityCursor<Object>) cursor);		
//			gallery.setAdapter(adapter);
//			Dish dish = (Dish) gallery.getItemAtPosition(0);
//			navigateTo(ViewModelDishList.ENTITY_DETAILS, null, dish);	
//		}
//	}
		
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		Dish dish = (Dish) parent.getItemAtPosition(position);
//		navigateTo(ViewModelDishList.ENTITY_DETAILS, null, dish);		
//	}
	
//	public class WrapperRepositoryDish extends WrapperRepository<Dish> implements IDishRepository {
//
//		public WrapperRepositoryDish(IRepository<Dish> repository) {
//			super(repository);
//			
//		}
//		
//		@Override
//		public IEntityCursor<Dish> getCursor() {
//			return super.getCursor("InWhatsGood = true");
//		}
//				
//	}

}
