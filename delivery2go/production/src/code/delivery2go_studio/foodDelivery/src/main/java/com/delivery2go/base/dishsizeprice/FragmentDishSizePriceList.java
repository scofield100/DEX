package com.delivery2go.base.dishsizeprice;

import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.dishsizeprice.ViewModelDishSizePriceList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.DishSizePrice})
public class FragmentDishSizePriceList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishSizePrice}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishSizePrice}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_dishsizeprice_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishSizePriceList.class)
					.putExtra(ActivityDishSizePriceDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishSizePriceDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityDishSizePriceEdit.class), FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		.put("CurrencyConverter", new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHSIZEPRICE");
		IDishSizePriceRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHSIZEPRICE");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishSizePriceRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishSizePriceRepository();
		}
		return new ViewModelDishSizePriceList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
				,new StringFilterCondition<DishSizePrice>("SizeName", getActivity().getString(R.string.sizename)){
					protected boolean eval(String prefix, DishSizePrice item){
						return StringUtils.startsWordWith(prefix, item.SizeName);
					}
				}
						,new StringFilterCondition<DishSizePrice>("DishName", getActivity().getString(R.string.dishname)){
					protected boolean eval(String prefix, DishSizePrice item){
						return StringUtils.startsWordWith(prefix, item.DishName);
					}
				}

			/*,new SpinnerFilterCondition<DishSizePrice,DishSize>(getActivity(),"SizeId", getActivity().getString(R.string.sizeid)){
				protected boolean eval(DishSize value, DishSizePrice item){
					return value.Id==0 || value.Id==item.SizeId;
				}
				public void update(){
					ViewModelDishSizePriceList viewModel = ViewModelDishSizePriceListgetViewModel();
					setItems(viewModel.getDishSizesOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<DishSizePrice,Dish>(getActivity(),"DishId", getActivity().getString(R.string.dishid)){
				protected boolean eval(Dish value, DishSizePrice item){
					return value.Id==0 || value.Id==item.DishId;
				}
				public void update(){
					ViewModelDishSizePriceList viewModel = ViewModelDishSizePriceListgetViewModel();
					setItems(viewModel.getDishsOptional());
				}
			}*/
			,new DoubleFilterCondition<DishSizePrice>(getActivity(),"ExtraPrice", getActivity().getString(R.string.extraprice_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, DishSizePrice item){
					return value == null || item.ExtraPrice >= value;
				}
			}
			,new DoubleFilterCondition<DishSizePrice>(getActivity(),"ExtraPrice", getActivity().getString(R.string.extraprice_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, DishSizePrice item){
					return value == null || item.ExtraPrice <= value;
				}
			}
			,new IntegerFilterCondition<DishSizePrice>(getActivity(),"AvailableCount", getActivity().getString(R.string.availablecount_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, DishSizePrice item){
					return value == null || (item.AvailableCount != null && item.AvailableCount >= value);
				}
			}
			,new IntegerFilterCondition<DishSizePrice>(getActivity(),"AvailableCount", getActivity().getString(R.string.availablecount_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, DishSizePrice item){
					return value == null || (item.AvailableCount != null && item.AvailableCount <= value);
				}
			}
			/*,new SpinnerFilterCondition<DishSizePrice,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, DishSizePrice item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelDishSizePriceList viewModel = ViewModelDishSizePriceListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/

		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityDishSizePriceDetails.class);
			i.putExtra(ActivityDishSizePriceDetails.ID, new int[]{((DishSizePrice)data).Id});
			startActivityForResult(i,FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishSizePriceEdit.DISHSIZEPRICE_EDITED);
		load();
	}

}
