package com.delivery2go.base.entity;

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
import com.delivery2go.ThumbailDrawableConverter;
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
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.entity.ViewModelEntityList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Entity})
public class FragmentEntityList extends ActionBarFilterableFragment {
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
		View view = inflater.inflate(R.layout.gen_entity_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityEntityList.class)
					.putExtra(ActivityEntityDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentEntityEdit.ENTITY_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityEntityEdit.class), FragmentEntityEdit.ENTITY_EDITED);
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
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ENTITY");
		IEntityRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ENTITY");
		}

		if(factoryCls!=null){
			try {
				repository=(IEntityRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIEntityRepository();
		}
		return new ViewModelEntityList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
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
			/*,new SpinnerFilterCondition<Entity,Image>(getActivity(),"ImageId", getActivity().getString(R.string.imageid)){
				protected boolean eval(Image value, Entity item){
					return value.Id==0 || value.Id==item.ImageId;
				}
				public void update(){
					ViewModelEntityList viewModel = ViewModelEntityListgetViewModel();
					setItems(viewModel.getImagesOptional());
				}
			}*/
			,new StringFilterCondition<Entity>("Adress", getActivity().getString(R.string.adress)){
				protected boolean eval(String prefix, Entity item){
					return StringUtils.startsWordWith(prefix, item.Adress);
				}
			}
			/*,new SpinnerFilterCondition<Entity,City>(getActivity(),"CityId", getActivity().getString(R.string.cityid)){
				protected boolean eval(City value, Entity item){
					return value.Id==0 || value.Id==item.CityId;
				}
				public void update(){
					ViewModelEntityList viewModel = ViewModelEntityListgetViewModel();
					setItems(viewModel.getCitiesOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<Entity,State>(getActivity(),"StateId", getActivity().getString(R.string.stateid)){
				protected boolean eval(State value, Entity item){
					return value.Id==0 || value.Id==item.StateId;
				}
				public void update(){
					ViewModelEntityList viewModel = ViewModelEntityListgetViewModel();
					setItems(viewModel.getStatesOptional());
				}
			}*/
			,new StringFilterCondition<Entity>("Tags", getActivity().getString(R.string.tags)){
				protected boolean eval(String prefix, Entity item){
					return StringUtils.startsWordWith(prefix, item.Tags);
				}
			}
			,new TimeFilterCondition<Entity>(getActivity(),"OpeningTime", getActivity().getString(R.string.openingtime_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, Entity item){
					return date == null || (item.OpeningTime != null && item.OpeningTime.compareTo(date) <= 0);
				}
			}
			,new TimeFilterCondition<Entity>(getActivity(),"CloseTime", getActivity().getString(R.string.closetime_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Date date, Entity item){
					return date == null || (item.CloseTime != null && item.CloseTime.compareTo(date) >= 0);
				}
			}
			,new TimeFilterCondition<Entity>(getActivity(),"CloseTime", getActivity().getString(R.string.closetime_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, Entity item){
					return date == null || (item.CloseTime != null && item.CloseTime.compareTo(date) <= 0);
				}
			}
			,new SwitchCondition<Entity>(getActivity(),"IsAvailable", getActivity().getString(R.string.isavailable), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Entity item){
					return value == null || item.IsAvailable == value;
				}
			}
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
			,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryTimeMin", getActivity().getString(R.string.deliverytimemin_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, Entity item){
					return value == null || (item.DeliveryTimeMin != null && item.DeliveryTimeMin <= value);
				}
			}
			,new DoubleFilterCondition<Entity>(getActivity(),"DeliveryTimeMax", getActivity().getString(R.string.deliverytimemax_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, Entity item){
					return value == null || (item.DeliveryTimeMax != null && item.DeliveryTimeMax >= value);
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
					return value == null || item.HasDelivery == value;
				}
			}
			,new SwitchCondition<Entity>(getActivity(),"HasPickup", getActivity().getString(R.string.haspickup), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Entity item){
					return value == null || item.HasPickup == value;
				}
			}
			/*,new SpinnerFilterCondition<Entity,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, Entity item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelEntityList viewModel = ViewModelEntityListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
			,new StringFilterCondition<Entity>("CityName", getActivity().getString(R.string.cityname)){
				protected boolean eval(String prefix, Entity item){
					return StringUtils.startsWordWith(prefix, item.CityName);
				}
			}
			,new StringFilterCondition<Entity>("StateName", getActivity().getString(R.string.statename)){
				protected boolean eval(String prefix, Entity item){
					return StringUtils.startsWordWith(prefix, item.StateName);
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
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityDetails.class);
			i.putExtra(ActivityEntityDetails.ID, new int[]{((Entity)data).Id});
			startActivityForResult(i,FragmentEntityEdit.ENTITY_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentEntityEdit.ENTITY_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentEntityEdit.ENTITY_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentEntityEdit.ENTITY_EDITED);
		load();
	}

	@Override
	public void onDataLoaded() {
		super.onDataLoaded();
	}
}
