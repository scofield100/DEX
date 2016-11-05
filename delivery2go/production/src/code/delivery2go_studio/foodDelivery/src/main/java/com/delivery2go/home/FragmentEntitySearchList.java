package com.delivery2go.home;

import java.util.ArrayList;
import java.util.Date;
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

import com.delivery2go.R;
import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.delivery2go.base.entity.FragmentEntityEdit;
import com.delivery2go.base.entity.FragmentEntityList;
import com.delivery2go.base.models.Entity;
import com.delivery2go.entity.ActivityEntityWelcome;
import com.enterlib.StringUtils;
import com.enterlib.data.IClosable;
import com.enterlib.databinding.BindingResources;
import com.enterlib.fields.Field;
import com.enterlib.fields.ListField;
import com.enterlib.filtering.DateFilterCondition;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.SelectionCommand;

public class FragmentEntitySearchList extends FragmentEntityList {
		
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
			.put("TimeConverter", new com.enterlib.converters.DateToStringConverter("HH:mm"))
			.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
			.put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.adapter_image),(int)getResources().getDimension(R.dimen.adapter_image)))
			.put("RankingConverter", new RantingConverter(getActivity()))
			;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment_entity_search_list, container, false);		
		return view;
	}

	
	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		ActivitySearchEntities activity = (ActivitySearchEntities) getActivity();
		ViewModelSearchEntity parentViewModel = activity.getViewModel();		
		return new ViewModelEntitySearchList(this, parentViewModel);	
	}
	
	@Override
	protected SearchViewFilterController createFilterController(
			Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
				,new StringFilterCondition<Entity>("Name", getActivity().getString(R.string.name)){
					protected boolean eval(String prefix, Entity item){
						return StringUtils.startsWordWith(prefix, item.Name);
					}
				}
				,new IntegerFilterCondition<Entity>(getActivity(),"Ranking", getActivity().getString(R.string.ranking_from),FilterOp.GREATHER_EQUALS){
					protected boolean eval(Integer value, Entity item){
						return value == null || (item.Ranking != null && item.Ranking >= value);
					}
				}
				,new IntegerFilterCondition<Entity>(getActivity(),"Ranking", getActivity().getString(R.string.ranking_to), FilterOp.LESS_EQUALS){
					protected boolean eval(Integer value, Entity item){
						return value == null || (item.Ranking != null && item.Ranking <= value);
					}
				}
				,new IntegerFilterCondition<Entity>(getActivity(),"ReviewCount", getActivity().getString(R.string.reviewcount_from),FilterOp.GREATHER_EQUALS){
					protected boolean eval(Integer value, Entity item){
						return value == null || item.ReviewCount >= value;
					}
				}
				,new IntegerFilterCondition<Entity>(getActivity(),"ReviewCount", getActivity().getString(R.string.reviewcount_to), FilterOp.LESS_EQUALS){
					protected boolean eval(Integer value, Entity item){
						return value == null || item.ReviewCount <= value;
					}
				}			
				,new StringFilterCondition<Entity>("Adress", getActivity().getString(R.string.adress)){
					protected boolean eval(String prefix, Entity item){
						return StringUtils.startsWordWith(prefix, item.Adress);
					}
				}							
				,new StringFilterCondition<Entity>("Tags", getActivity().getString(R.string.tags)){
					protected boolean eval(String prefix, Entity item){
						return StringUtils.startsWordWith(prefix, item.Tags);
					}
				}
//				,new DateFilterCondition<Entity>(getActivity(),"OpeningTime", getActivity().getString(R.string.openingtime_from),FilterOp.GREATHER_EQUALS){
//					protected boolean eval(Date date, Entity item){
//						return date == null || (item.OpeningTime != null && item.OpeningTime.compareTo(date) >= 0);
//					}
//				}
//				,new DateFilterCondition<Entity>(getActivity(),"CloseTime", getActivity().getString(R.string.closetime_to),FilterOp.LESS_EQUALS){
//					protected boolean eval(Date date, Entity item){
//						return date == null || (item.CloseTime != null && item.CloseTime.compareTo(date) <= 0);
//					}
//				}
				,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryPrice", getActivity().getString(R.string.deliveryprice_from),FilterOp.GREATHER_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.DeliveryPrice != null && item.DeliveryPrice >= value);
					}
				}
				,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryPrice", getActivity().getString(R.string.deliveryprice_to), FilterOp.LESS_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.DeliveryPrice != null && item.DeliveryPrice <= value);
					}
				}
				,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryTimeMin", getActivity().getString(R.string.deliverytimemin_from),FilterOp.GREATHER_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.DeliveryTimeMin != null && item.DeliveryTimeMin >= value);
					}
				}								
				,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryTimeMax", getActivity().getString(R.string.deliverytimemax_to), FilterOp.LESS_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.DeliveryTimeMax != null && item.DeliveryTimeMax <= value);
					}
				}
				,new DoubleFilterCondition<Entity>(getActivity(),"MinPrice", getActivity().getString(R.string.minprice_from),FilterOp.GREATHER_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.MinPrice != null && item.MinPrice >= value);
					}
				}
				,new DoubleFilterCondition<Entity>(getActivity(),"MinPrice", getActivity().getString(R.string.minprice_to), FilterOp.LESS_EQUALS){
					protected boolean eval(Double value, Entity item){
						return value == null || (item.MinPrice != null && item.MinPrice <= value);
					}
				}
				,new SwitchCondition<Entity>(getActivity(),"HasDelivery", getActivity().getString(R.string.hasdelivery), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
					protected boolean eval(Boolean value, Entity item){
						return value == null || (item.HasDelivery == value);
					}
				}
				,new SwitchCondition<Entity>(getActivity(),"HasPickup", getActivity().getString(R.string.haspickup), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
					protected boolean eval(Boolean value, Entity item){
						return value == null || (item.HasPickup == value);
					}
				}	

			);
	}
		
	
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityWelcome.class);
			i.putExtra(ActivityEntityDetails.ID, new int[]{((Entity)data).Id});
			startActivityForResult(i,FragmentEntityEdit.ENTITY_EDITED);
		}
	}
	
	static class ViewModelEntitySearchList extends DataViewModel implements Observer, IClosable{
		
		private ViewModelSearchEntity parentViewModel;

		public ViewModelEntitySearchList(IDataView view, ViewModelSearchEntity parentViewModel) {
			super(view);
			
			this.parentViewModel = parentViewModel;
			parentViewModel.addObserver(this);
		}
		
		public ArrayList<Entity> getItems(){
			return parentViewModel.getItems();
		}
		
		public String Status;

		@Override
		public void load() {
			
		}
		
		@Override
		protected boolean loadAsync() throws Exception {
			return true;
		}
		
		public SelectionCommand Selection = new SelectionCommand() {
			
			@Override
			public void invoke(Field field, AdapterView<?> adapterView, View itemView,
					int position, long id) {
				
				Entity entity = (Entity) adapterView.getItemAtPosition(position);
				getNavigator().navigateTo( EntityCursorViewModel.ENTITY_DETAILS, null, entity);
				
			}
		};

		@Override
		public void update(Observable observable, Object data) {
			Status = String.format(getView().getString(R.string.status_search_entities), getItems().size());
			getView().onDataLoaded();			
		}

		@Override
		public void close() {
			parentViewModel.deleteObserver(this);
			
		}
		
	}

}
