package com.delivery2go.base.orderdish;

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
import com.enterlib.converters.CurrencyConverter;
import com.enterlib.converters.DateToStringConverter;
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
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.orderdish.ViewModelOrderDishList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.OrderDish})
public class FragmentOrderDishList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDish}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDish}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_orderdish_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityOrderDishList.class)
					.putExtra(ActivityOrderDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentOrderDishEdit.ORDERDISH_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityOrderDishEdit.class), FragmentOrderDishEdit.ORDERDISH_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
				.put("DateConverter", new DateToStringConverter("dd/MM/yyyy HH:mm"))
				.put("CurrencyConverter", new CurrencyConverter(getString(R.string.currency)))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ORDERDISH");
		IOrderDishRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ORDERDISH");
		}

		if(factoryCls!=null){
			try {
				repository=(IOrderDishRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIOrderDishRepository();
		}
		return new ViewModelOrderDishList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			/*,new SpinnerFilterCondition<OrderDish,Order>(getActivity(),"OrderId", getActivity().getString(R.string.orderid)){
				protected boolean eval(Order value, OrderDish item){
					return value.OrderId==0 || value.OrderId==item.OrderId;
				}
				public void update(){
					ViewModelOrderDishList viewModel = ViewModelOrderDishListgetViewModel();
					setItems(viewModel.getOrdersOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<OrderDish,Dish>(getActivity(),"DishId", getActivity().getString(R.string.dishid)){
				protected boolean eval(Dish value, OrderDish item){
					return value.Id==0 || value.Id==item.DishId;
				}
				public void update(){
					ViewModelOrderDishList viewModel = ViewModelOrderDishListgetViewModel();
					setItems(viewModel.getDishsOptional());
				}
			}*/
			,new DoubleFilterCondition<OrderDish>(getActivity(),"DishPrice", getActivity().getString(R.string.dishprice_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || item.DishPrice >= value;
				}
			}
			,new DoubleFilterCondition<OrderDish>(getActivity(),"DishPrice", getActivity().getString(R.string.dishprice_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || item.DishPrice <= value;
				}
			}
			,new IntegerFilterCondition<OrderDish>(getActivity(),"Quantity", getActivity().getString(R.string.quantity_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, OrderDish item){
					return value == null || item.Quantity >= value;
				}
			}
			,new IntegerFilterCondition<OrderDish>(getActivity(),"Quantity", getActivity().getString(R.string.quantity_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, OrderDish item){
					return value == null || item.Quantity <= value;
				}
			}
			/*,new SpinnerFilterCondition<OrderDish,DishSizePrice>(getActivity(),"SizeId", getActivity().getString(R.string.sizeid)){
				protected boolean eval(DishSizePrice value, OrderDish item){
					return value.Id==0 || value.Id==item.SizeId;
				}
				public void update(){
					ViewModelOrderDishList viewModel = ViewModelOrderDishListgetViewModel();
					setItems(viewModel.getDishSizePricesOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<OrderDish,Addons>(getActivity(),"DressingId", getActivity().getString(R.string.dressingid)){
				protected boolean eval(Addons value, OrderDish item){
					return value.Id==0 || value.Id==item.DressingId;
				}
				public void update(){
					ViewModelOrderDishList viewModel = ViewModelOrderDishListgetViewModel();
					setItems(viewModel.getAddonsesOptional());
				}
			}*/
			,new DoubleFilterCondition<OrderDish>(getActivity(),"DressingPrice", getActivity().getString(R.string.dressingprice_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || (item.DressingPrice != null && item.DressingPrice >= value);
				}
			}
			,new DoubleFilterCondition<OrderDish>(getActivity(),"DressingPrice", getActivity().getString(R.string.dressingprice_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || (item.DressingPrice != null && item.DressingPrice <= value);
				}
			}
			,new DoubleFilterCondition<OrderDish>(getActivity(),"SubTotal", getActivity().getString(R.string.subtotal_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || (item.SubTotal != null && item.SubTotal >= value);
				}
			}
			,new DoubleFilterCondition<OrderDish>(getActivity(),"SubTotal", getActivity().getString(R.string.subtotal_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, OrderDish item){
					return value == null || (item.SubTotal != null && item.SubTotal <= value);
				}
			}
			/*,new SpinnerFilterCondition<OrderDish,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, OrderDish item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelOrderDishList viewModel = ViewModelOrderDishListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
			,new StringFilterCondition<OrderDish>("DishName", getActivity().getString(R.string.dishname)){
				protected boolean eval(String prefix, OrderDish item){
					return StringUtils.startsWordWith(prefix, item.DishName);
				}
			}
			,new StringFilterCondition<OrderDish>("DressingName", getActivity().getString(R.string.dressingname)){
				protected boolean eval(String prefix, OrderDish item){
					return StringUtils.startsWordWith(prefix, item.DressingName);
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
//			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityOrderDishDetails.class);
//			i.putExtra(ActivityOrderDishDetails.ID, new int[]{((OrderDish)data).Id});
//			startActivityForResult(i,FragmentOrderDishEdit.ORDERDISH_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentOrderDishEdit.ORDERDISH_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentOrderDishEdit.ORDERDISH_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentOrderDishEdit.ORDERDISH_EDITED);
		load();
	}

}
