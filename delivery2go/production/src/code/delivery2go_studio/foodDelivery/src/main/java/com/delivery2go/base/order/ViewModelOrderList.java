package com.delivery2go.base.order;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.OrderStateEnum;
import com.delivery2go.OrderTypeEnum;
import com.enterlib.StringUtils;
import com.enterlib.app.CollectionAdapter;
import com.enterlib.data.ArrayEntityCursor;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.BusinessException;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.fields.ListField;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Order;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.OrderState;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.models.OrderType;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;
import com.enterlib.threading.LoaderHandler;

public class ViewModelOrderList extends EntityCursorViewModel<Order> implements LoaderHandler.Delayer{

	public ArrayList<Order> _items = new ArrayList<>();
	private LoaderHandler loader;
    //SparseArray<Order>notifyOrders = new SparseArray<>();
    protected final Object mLock = new Object();


	protected IOrderRepository orderRepository;


	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IClientRepository clientRepository;
	//protected ArrayList<Client> clientsOptional;
	//public List<Client> getClientsOptional(){ return clientsOptional; }

	protected IOrderStateRepository orderStateRepository;
	protected ArrayList<OrderState> orderStatesOptional;
	public List<OrderState> getOrderStatesOptional(){ return orderStatesOptional; }

	protected IOrderTypeRepository orderTypeRepository;
	protected ArrayList<OrderType> orderTypesOptional;
	public List<OrderType> getOrderTypesOptional(){ return orderTypesOptional; }

	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderList (FragmentView view, IOrderRepository orderRepository, boolean checkEnable) {
		super(view);
		
		this.orderRepository = orderRepository;   

		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.orderStateRepository=RepositoryManager.getInstance().getIOrderStateRepository();
		this.orderTypeRepository=RepositoryManager.getInstance().getIOrderTypeRepository();
		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();


        if(checkEnable) {
            loader = new LoaderHandler(true, this);
            loader.setAutofinish(false);
        }
    
	}

    @Override
    public long getPostingDelay() {
        return 10000;
    }

    @Override
    public void load() {
//        synchronized (mLock) {
//            notifyOrders.clear();
//        }

        super.load();
    }

    @Override
	protected boolean loadAsync() throws Exception {               

		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.clientsOptional=BaseModel.asOptionalList(Client.class, clientRepository.getAll(), new Client());
		this.orderStatesOptional= BaseModel.asOptionalList(OrderState.class, orderStateRepository.getAll(), new OrderState());
		this.orderTypesOptional=BaseModel.asOptionalList(OrderType.class, orderTypeRepository.getAll(), new OrderType());
		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Order> createCursor()
			throws InvalidOperationException {
        if(loader!=null) {
            _items = this.orderRepository.getAll();
            return new ArrayEntityCursor<Order>(_items);
        }else{
            return orderRepository.getCursor();
        }
    }

	@Override
	protected void onLoaded(Exception workException) {
		super.onLoaded(workException);

		if(workException!=null)
			return;

		if(loader!=null) {
			if (_items == null || _items.size() == 0) {
				loader.stop();
				return;
			}

			loader.postTask(statusTask);
		}
	}

	@Override
	public void close() {
		super.close();

		this.orderRepository.close();
		//this.entityRepository.close();
		//this.clientRepository.close();
		this.orderStateRepository.close();
		this.orderTypeRepository.close();
		//this.imageRepository.close();
		//this.userRepository.close();

        if(loader!=null)
            loader.stop();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(orderRepository instanceof IAttachRepository<?>){
			IAttachRepository<Order>attachedRep = (IAttachRepository<Order>) orderRepository;
			if(!attachedRep.attach((Order) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		ArrayList<String>messages = new ArrayList<String>();
		for (int i = 0; i < items.size(); i++) {
			Order obj = (Order) items.get(i);
			if (obj.OrderStateId != OrderStateEnum.Delivered && obj.OrderStateId != OrderStateEnum.Cancelled) {
				messages.add("The order " + String.valueOf(obj.OrderId) + " can not be deleted");
			}
		}

		if(messages.size() > 0){
			throw new BusinessException(TextUtils.join("\r\n", messages));
		}

		for (int i = 0; i < items.size(); i++) {
			Order obj = (Order) items.get(i);
			orderRepository.delete(obj);
		}
		return true;
	}

    LoaderHandler.LoadTask statusTask = new LoaderHandler.LoadTask() {

        ArrayList<String>notifications = new ArrayList<>();

        @Override
        public Object runAsync(Object args) throws Exception {

            notifications.clear();

            if(_items == null || _items.size() == 0){
                return new ArrayList<Order>(0);
            }

            ArrayList<Order> items =  orderRepository.getAll();

            for (Order o : items){
                if(o.EntityNotified)
                    continue;

                if(o.OrderStateId == OrderStateEnum.Submited ){
//                    if(notifyOrders.get(o.OrderId)!= null){
//                        continue;
//                    }
//
//                    synchronized (mLock) {
//                        notifyOrders.put(o.OrderId, o);
//                    }
//
                    notifications.add("You have a new order. Id:" + String.valueOf(o.OrderId));
                    o.EntityNotified = true;
                    orderRepository.update(o);
                }
            }

            return items;
        }

        @Override
        public void onComplete(Object result, Exception e) {
            if(e != null) {
                ((FragmentView)getView()).showMessage("Refresh the items for start the notification process");
                return;
            }

            _items.clear();
            _items.addAll((ArrayList<Order>) result);

            cursor.notifyDataChange();

//            ListField listField = (ListField) getForm().getFieldByBinding("Items");
//            CollectionAdapter<Order> adapter = ( CollectionAdapter<Order>) listField.getAdapter();
//            if(adapter!=null){
//                adapter.setNotifyOnChange(false);
//                adapter.clear();
//                adapter.setNotifyOnChange(true);
//                adapter.addAll(Items);
//            }else{
//                listField.updateSource(ViewModelOrderList.this);
//            }

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
        FragmentView view= (FragmentView) getView();
        if(view == null || !view.isValid())
            return;

        if (notifications.size() > 0) {
            Activity activity = view.getActivity();

            if(activity == null)
                return;

            Vibrator vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);

            for (int i = 0; i < notifications.size(); i++) {
                Toast toast = Toast.makeText(view.getActivity(), notifications.get(i), Toast.LENGTH_LONG);
                toast.show();

                if(vibrator!=null && vibrator.hasVibrator()){
                    vibrator.vibrate(500);
                }

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(view.getActivity().getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }



}