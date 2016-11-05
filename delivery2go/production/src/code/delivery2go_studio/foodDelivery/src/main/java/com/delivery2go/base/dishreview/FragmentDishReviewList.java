package com.delivery2go.base.dishreview;

import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.RantingConverter;
import com.enterlib.StringUtils;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.fields.ListField;
import com.enterlib.databinding.BindingResources;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.SpinnerFilterCondition;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.filtering.DateFilterCondition;
import com.enterlib.filtering.TimeFilterCondition;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.widgets.ProgressLayout;
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.dishreview.ViewModelDishReviewList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Entity})
public class FragmentDishReviewList extends ActionBarFilterableFragment {
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list, menu);
		if(getActivity().getIntent().getBooleanExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE, false)){
			menu.findItem(R.id.delete).setTitle(R.string.select);
			menu.findItem(R.id.delete).setIcon(R.drawable.ic_action_list_2);

			menu.findItem(R.id.create).setEnabled(false);
			menu.findItem(R.id.create).setVisible(false);

			onInitDelete(menu);
		} else {

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Entity}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Entity}, Access.CREATE)){
				menu.findItem(R.id.create).setEnabled(false);
				menu.findItem(R.id.create).setVisible(false);
			}
		}
		onInitSearch(menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSearchItemId(R.id.action_search);
		setDeleteItemId(R.id.delete);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dishreview_fragment_list, container, false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishReviewList.class)
					.putExtra(ActivityDishReviewDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishReviewDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentDishReviewEdit.DISHREVIEW_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityDishReviewEdit.class), FragmentDishReviewEdit.DISHREVIEW_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		.put("RankingConverter", new RantingConverter(getActivity()))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHREVIEW");
		IDishReviewRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHREVIEW");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishReviewRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishReviewRepository();
		}
		return new ViewModelDishReviewList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new IntegerFilterCondition<DishReview>(getActivity(),"Rating", getActivity().getString(R.string.rating_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, DishReview item){
					return value == null || item.Rating >= value;
				}
			}
			,new IntegerFilterCondition<DishReview>(getActivity(),"Rating", getActivity().getString(R.string.rating_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, DishReview item){
					return value == null || item.Rating <= value;
				}
			}
			/*,new SpinnerFilterCondition<DishReview,Client>(getActivity(),"ClientId", getActivity().getString(R.string.clientid)){
				protected boolean eval(Client value, DishReview item){
					return value.Id==0 || value.Id==item.ClientId;
				}
				public void update(){
					ViewModelDishReviewList viewModel = ViewModelDishReviewListgetViewModel();
					setItems(viewModel.getClientsOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<DishReview,Dish>(getActivity(),"DishId", getActivity().getString(R.string.dishid)){
				protected boolean eval(Dish value, DishReview item){
					return value.Id==0 || value.Id==item.DishId;
				}
				public void update(){
					ViewModelDishReviewList viewModel = ViewModelDishReviewListgetViewModel();
					setItems(viewModel.getDishsOptional());
				}
			}*/
			,new DateFilterCondition<DishReview>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Date date, DishReview item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) >= 0);
				}
			}
			,new DateFilterCondition<DishReview>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, DishReview item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) <= 0);
				}
			}
			,new StringFilterCondition<DishReview>("ClientName", getActivity().getString(R.string.clientname)){
				protected boolean eval(String prefix, DishReview item){
					return StringUtils.startsWordWith(prefix, item.ClientName);
				}
			}

		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityDishReviewDetails.class);
			i.putExtra(ActivityDishReviewDetails.ID, new int[]{((DishReview)data).Id});
			startActivityForResult(i,FragmentDishReviewEdit.DISHREVIEW_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishReviewEdit.DISHREVIEW_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentDishReviewEdit.DISHREVIEW_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishReviewEdit.DISHREVIEW_EDITED);
		load();
	}

}
