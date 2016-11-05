package com.delivery2go.base.order;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IDeliveryAgentRepository;
import com.delivery2go.base.models.DeliveryAgent;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.Roll;
import com.delivery2go.base.models.User;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.models.Order;

public class ViewModelOrderDetails extends DataViewModel implements IClosable {

	public static final int ENTITY = 1;
	public static final int CLIENT = 2;
	public static final int ORDERSTATE = 3;
	public static final int ORDERTYPE = 4;
	public static final int CLIENTSIGNATURE = 5;
	public static final int UPDATEUSER = 6;
	private User user;
    private DeliveryAgent deliveryAgent;

    protected IOrderRepository orderRepository;
	protected Order _item;
	int[] _itemId;
	public Order getItem(){ return this._item;}


	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public Command NavigateToClient= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CLIENT, null, _item.ClientId);
			}
		}
	};

	public Command NavigateToOrderState= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ORDERSTATE, null, _item.OrderStateId);
			}
		}
	};

	public Command NavigateToOrderType= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ORDERTYPE, null, _item.OrderTypeId);
			}
		}
	};

	public Command NavigateToClientSignature= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.ClientSignatureId!=null){
				getNavigator().navigateTo(CLIENTSIGNATURE, null, _item.ClientSignatureId);
			}
		}
	};

	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public Command Call = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			if(StringUtils.isNullOrWhitespace(_item.Phone))
				return;

			String uriAddress = "tel:" + _item.Phone;
			uriAddress = uriAddress.replace("(", "")
					.replace(")", "");

			FragmentView fragmentView = (FragmentView) getView();
			try{
				Intent i = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse(uriAddress));
				fragmentView.startActivity(i);
			}catch(Exception e){
				UIUtils.showMessage(fragmentView.getActivity(), fragmentView.getString(R.string.error_calling_phone_number));
				Log.d(getClass().getName(), e.getMessage(), e);
			}

		}
	};

	public boolean getShowContactInfo(){
		return Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DeliveryAgent}, Access.ALL);
	}

    public double getRestaurantPayment(){
        return  _item.TotalAmount - (_item.TotalAmount * (deliveryAgent.DeliveryDiscount / 100.0));
    }

	public ViewModelOrderDetails(IDataView view, IOrderRepository orderRepository, int[] id){
		super(view);

		this.orderRepository=orderRepository;
		this._itemId=id;
        this.user = DeliveryApp.getUser();
	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = orderRepository.get(_itemId);

		IDeliveryAgentRepository agentRepository = RepositoryManager.getInstance().getIDeliveryAgentRepository();
		this.deliveryAgent =  agentRepository.get(DeliveryApp.DELIVERY_ACOUNT_ID);
		agentRepository.close();

		return true;
	}



	@Override
	public void close() {
		this.orderRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=orderRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}




}