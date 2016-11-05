package com.delivery2go.base.order;

import java.util.Date;

import android.content.res.Resources;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.OrderStateEnum;
import com.delivery2go.base.models.OrderState;
import com.delivery2go.base.models.OrderType;
import com.enterlib.StringUtils;
import com.enterlib.converters.CurrencyConverter;
import com.enterlib.converters.DateToStringConverter;
import com.enterlib.converters.IValueConverter;
import com.enterlib.exceptions.ConversionFailException;
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
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.order.ViewModelOrderList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Order})
public class FragmentOrderList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_order_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityOrderList.class)
					.putExtra(ActivityOrderDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentOrderEdit.ORDER_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityOrderEdit.class), FragmentOrderEdit.ORDER_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return super.getBindingResources()
				.put("DateConverter", new DateToStringConverter("dd/MM/yyyy HH:mm"))
				.put("CurrencyConverter", new CurrencyConverter(getString(R.string.currency)))
				.put("MinutesConverter", new IValueConverter() {
					@Override
					public Object convert(Object value) throws ConversionFailException {
						Integer minutes = (Integer) value;
						if(minutes == null)
							return "unspecified";

						int h = minutes / 60;
						int m = minutes % 60;
						return String.format("%02d:%02d",h,m);
					}

					@Override
					public Object convertBack(Object value) throws ConversionFailException {
						return null;
					}
				})
				.put("StateToColorConverter", new IValueConverter() {
					@Override
					public Object convert(Object value) throws ConversionFailException {
						int stateId = (Integer) value;
						Resources res = getResources();
						int color;
						switch (stateId) {
							case OrderStateEnum.Open:
								color = res.getColor(R.color.order_open);
								break;
							case OrderStateEnum.Submited:
								color = res.getColor(R.color.order_sumbited);
								break;
							case OrderStateEnum.Ready:
								color = res.getColor(R.color.order_ready);
								break;
							case OrderStateEnum.OnTheWay:
								color = res.getColor(R.color.order_on_the_way);
								break;
							case OrderStateEnum.Delivered:
								color = res.getColor(R.color.order_delivered);
								break;
							case OrderStateEnum.Cancelled:
								color = res.getColor(R.color.order_cancelled);
								break;
							case OrderStateEnum.Unavailable:
								color = res.getColor(R.color.order_cancelled);
								break;
							case OrderStateEnum.Arrived:
								color = res.getColor(R.color.order_arrived);
								break;
							default:
								color = 0xFFFFFFFF;
						}

						return color;
					}

					@Override
					public Object convertBack(Object value) throws ConversionFailException {
						return null;
					}
				});
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ORDER");
		IOrderRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ORDER");
		}

		if(factoryCls!=null){
			try {
				repository=(IOrderRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIOrderRepository();
		}
		return new ViewModelOrderList(this, repository, false);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
				,new IntegerFilterCondition<Order>(getActivity(), "OrderId", getActivity().getString(R.string.code)){
			@Override
			protected boolean eval(Integer value, Order item) {
				return value == null || value == item.OrderId;
			}
		}
				,new StringFilterCondition<Order>("EntityName", getActivity().getString(R.string.entityname)){
			protected boolean eval(String prefix, Order item){
				return StringUtils.startsWordWith(prefix, item.EntityName);
			}
		}
				,new SpinnerFilterCondition<Order,OrderState>(getActivity(),"OrderStateId", getActivity().getString(R.string.orderstateid) ,"Id" ){
			protected boolean eval(OrderState value, Order item){
				return value.Id==0 || value.Id==item.OrderStateId;
			}
			public void update(){
				ViewModelOrderList viewModel = (ViewModelOrderList)getViewModel();
				setItems(viewModel.getOrderStatesOptional());
			}
		}
				,new SpinnerFilterCondition<Order,OrderType>(getActivity(),"OrderTypeId", getActivity().getString(R.string.ordertypeid), "Id"){
			protected boolean eval(OrderType value, Order item){
				return value.Id==0 || value.Id==item.OrderTypeId;
			}
			public void update(){
				ViewModelOrderList viewModel = (ViewModelOrderList)getViewModel();
				setItems(viewModel.getOrderTypesOptional());
			}
		}

				,new IntegerFilterCondition<Order>(getActivity(),"DeliveryTimeMinutes", getActivity().getString(R.string.deliverytimeminutes_from),FilterOp.GREATHER_EQUALS){
			protected boolean eval(Integer value, Order item){
				return value == null || (item.DeliveryTimeMinutes != null && item.DeliveryTimeMinutes >= value);
			}
		}
				,new IntegerFilterCondition<Order>(getActivity(),"DeliveryTimeMinutes", getActivity().getString(R.string.deliverytimeminutes_to), FilterOp.LESS_EQUALS){
			protected boolean eval(Integer value, Order item){
				return value == null || (item.DeliveryTimeMinutes != null && item.DeliveryTimeMinutes <= value);
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
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityOrderDetails.class);
			i.putExtra(ActivityOrderDetails.ID, new int[]{((Order)data).OrderId});
			startActivityForResult(i,FragmentOrderEdit.ORDER_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentOrderEdit.ORDER_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentOrderEdit.ORDER_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentOrderEdit.ORDER_EDITED);
		load();
	}

}
