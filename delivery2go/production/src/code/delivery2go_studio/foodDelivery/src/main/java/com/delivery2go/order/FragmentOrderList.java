package com.delivery2go.order;

import android.app.Activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.OrderTypeEnum;
import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.models.OrderState;
import com.delivery2go.base.models.OrderType;
import com.enterlib.StringUtils;
import com.enterlib.app.CollectionAdapter;
import com.enterlib.app.UIUtils;
import com.enterlib.converters.CurrencyConverter;
import com.enterlib.converters.DateToStringConverter;
import com.enterlib.converters.IValueConverter;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.databinding.BindingResources;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.fields.Field;
import com.enterlib.fields.ListField;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.SpinnerFilterCondition;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.SelectionCommand;
import com.enterlib.threading.LoaderHandler;
import com.enterlib.widgets.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ansel on 28/08/2016.
 */
public class FragmentOrderList extends ActionBarFilterableFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSearchItemId(R.id.action_search);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_orderlist, menu);
        onInitSearch(menu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.  layout.order_fragment_list, container, false);
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
            case R.id.action_info:
                showNotifications();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
        return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
                ,new StringFilterCondition<Order>("Code", getActivity().getString(R.string.code)){
            protected boolean eval(String prefix, Order item){
                return StringUtils.startsWordWith(prefix, item.Code);
            }
        }
        ,new StringFilterCondition<Order>("EntityName", getActivity().getString(R.string.entityname)){
            protected boolean eval(String prefix, Order item){
                return StringUtils.startsWordWith(prefix, item.EntityName);
            }
        }
		,new SpinnerFilterCondition<Order,OrderState>(getActivity(),"OrderStateId", getActivity().getString(R.string.orderstateid)){
				protected boolean eval(OrderState value, Order item){
					return value.Id==0 || value.Id==item.OrderStateId;
				}
				public void update(){
					ViewModelOrderList viewModel = (ViewModelOrderList)getViewModel();
					setItems(viewModel.getOrderStatesOptional());
				}
			}
			,new SpinnerFilterCondition<Order,OrderType>(getActivity(),"OrderTypeId", getActivity().getString(R.string.ordertypeid)){
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
    protected DataViewModel createViewModel(Bundle savedInstanceState) {
        return new ViewModelOrderList(this);
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
                        int stateId = (Integer)value;
                        Resources res =getResources();
                        int color;
                        switch (stateId){
                            case OrderStateEnum.Open: color = res.getColor(R.color.order_open);break;
                            case OrderStateEnum.Submited: color = res.getColor(R.color.order_sumbited);break;
                            case OrderStateEnum.Ready: color = res.getColor(R.color.order_ready);break;
                            case OrderStateEnum.OnTheWay: color = res.getColor(R.color.order_on_the_way);break;
                            case OrderStateEnum.Delivered: color = res.getColor(R.color.order_delivered);break;
                            case OrderStateEnum.Cancelled: color = res.getColor(R.color.order_cancelled);break;
                            case OrderStateEnum.Unavailable: color = res.getColor(R.color.order_cancelled);break;
                            case OrderStateEnum.Arrived: color = res.getColor(R.color.order_arrived);break;
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

    public class ViewModelOrderList extends DataViewModel implements IClosable, LoaderHandler.Delayer {
        public static final int EDIT_ORDER = 549623;

        public ArrayList<Order> Items = new ArrayList<>();
        private IOrderRepository orderRepository;
        private LoaderHandler loader;
        private Client client;

        protected IOrderStateRepository orderStateRepository;
        protected ArrayList<OrderState> orderStatesOptional;
        public List<OrderState> getOrderStatesOptional(){ return orderStatesOptional; }

        protected IOrderTypeRepository orderTypeRepository;
        protected ArrayList<OrderType> orderTypesOptional;
        public List<OrderType> getOrderTypesOptional(){ return orderTypesOptional; }


        public ViewModelOrderList(IDataView view) {
            super(view);

            orderRepository = RepositoryManager.getInstance().getIOrderRepository();

            loader = new LoaderHandler(true, this);
            loader.setAutofinish(false);

            this.orderStateRepository=RepositoryManager.getInstance().getIOrderStateRepository();
            this.orderTypeRepository=RepositoryManager.getInstance().getIOrderTypeRepository();
        }

        @Override
        protected boolean loadAsync() throws Exception {

            client = DeliveryApp.getServerClient();

            Items = orderRepository.getAll(String.format("OrderStateId!=%d and OrderStateId!=%d and ClientId=%d",
                    OrderStateEnum.Delivered, OrderStateEnum.Cancelled, client.Id), -1, -1);

            this.orderStatesOptional= BaseModel.asOptionalList(OrderState.class, orderStateRepository.getAll(), new OrderState());
            this.orderTypesOptional=BaseModel.asOptionalList(OrderType.class, orderTypeRepository.getAll(), new OrderType());

            return true;
        }

        @Override
        protected void onLoaded(Exception workException) {
            super.onLoaded(workException);

            if(workException!=null)
                return;

            if(Items == null || Items.size() == 0){
                loader.stop();
                return;
            }

            loader.postTask(statusTask);
        }

        public SelectionCommand Selection = new SelectionCommand() {


            @Override
            public void invoke(Field field, AdapterView<?> adapterView, View itemView, int position, long id) {
                Order order = (Order) adapterView.getItemAtPosition(position);

                Intent i =new Intent(getActivity(), ActivityOrderDetails.class).putExtra(com.delivery2go.base.order.ActivityOrderDetails.ID, order.OrderId);
                startActivityForResult(i, EDIT_ORDER);
            }
        };

        @Override
        public void onResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == EDIT_ORDER && resultCode == Activity.RESULT_OK){
                load();
            }
        }

        @Override
        public void close() {
            orderRepository.close();
            this.orderStateRepository.close();
            this.orderTypeRepository.close();
            loader.stop();
        }

        @Override
        public long getPostingDelay() {
            return 10000;
        }

        LoaderHandler.LoadTask statusTask = new LoaderHandler.LoadTask() {
            ArrayList<String>notifications = new ArrayList<>();
            @Override
            public Object runAsync(Object args) throws Exception {

                notifications.clear();

                if(Items == null || Items.size() == 0){
                    return new ArrayList<Order>(0);
                }

                ArrayList<Order> items = orderRepository.getAll(String.format("OrderStateId!=%d and OrderStateId!=%d and ClientId=%d",
                        OrderStateEnum.Delivered, OrderStateEnum.Cancelled, client.Id), -1, -1);

                if(items == null || items.size() == 0)
                    return items;

                String message = null;
               // ArrayList<Order>testList = new ArrayList<>();

                for (Order o : items){
                    if(o.ClientNotified)
                        continue;

                    if(o.OrderStateId == OrderStateEnum.Unavailable){
                        message = String.format("The order %d has problems.", o.OrderId);
                    }else if(o.OrderStateId == OrderStateEnum.Ready){
                        if(o.OrderTypeId == OrderTypeEnum.Pickup)
                            message =String.format("The order %d is ready to pickup.", o.OrderId);
                    }else if(o.OrderStateId == OrderStateEnum.OnTheWay) {
                        message =String.format("The order %d is served and is on its way.", o.OrderId);
                    }else if(o.OrderStateId == OrderStateEnum.Arrived) {
                        message =String.format("The order %d has arrived.", o.OrderId);
                    }


                    if(message != null){
                        if(!StringUtils.isNullOrWhitespace(o.Remarks)){
                            message+="\r\n"+o.Remarks;
                        }

                        o.ClientNotified = true;
                        orderRepository.update(o);
                        notifications.add(message);
                    }

                    //FOR TEST
//                    if(o.DeliveryTimeMinutes == null)
//                        o.DeliveryTimeMinutes = 90;
//
//                    o.DeliveryTimeMinutes--;
//
//                    if(o.OrderStateId == OrderStateEnum.Submited)
//                        o.OrderStateId = OrderStateEnum.Ready;
//                    else if(o.OrderStateId == OrderStateEnum.Ready)
//                        o.OrderStateId = OrderStateEnum.OnTheWay;
//                    else if(o.OrderStateId == OrderStateEnum.OnTheWay)
//                        o.OrderStateId = OrderStateEnum.Arrived;
//                    else if(o.OrderStateId == OrderStateEnum.Arrived)
//                        o.OrderStateId = OrderStateEnum.Delivered;
//
//                    orderRepository.update(o);
//
//                    testList.add(orderRepository.get(o.OrderId));
                }

                return items;
            }

            @Override
            public void onComplete(Object result, Exception e) {
                if(e != null) {
                    showMessage("Refresh the items for start the notification process");
                    return;
                }

                Items =(ArrayList<Order>) result;

                ListField listField = (ListField) getForm().getFieldByBinding("Items");
                CollectionAdapter<Order> adapter = ( CollectionAdapter<Order>) listField.getAdapter();
                if(adapter!=null){
                    adapter.setNotifyOnChange(false);
                    adapter.clear();
                    adapter.setNotifyOnChange(true);
                    adapter.addAll(Items);
                }else{
                    listField.updateSource(ViewModelOrderList.this);
                }

                getNotifications().clear();
                getNotifications().addAll(notifications);

                showNotificationsToast();

                loader.postTask(statusTask);
            }
        };

        public void showNotificationsToast() {
            if (!hasNotifications()) {
                return;
            }

            List<String> notifications = getNotifications();

            if (notifications.size() > 0) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                for (int i = 0; i < notifications.size(); i++) {
                    Toast toast = Toast.makeText(getActivity(), notifications.get(i), Toast.LENGTH_LONG);
                    toast.show();

                    if(vibrator!=null && vibrator.hasVibrator()){
                        vibrator.vibrate(500);
                    }

                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }
}
