package com.delivery2go.base.order;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
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

public class ViewModelOrderEdit extends EditViewModel implements IClosable {

	protected IOrderRepository orderRepository;

	protected Order _item;
	protected int[] _itemId;
	private int previewsOrderStateId;

	public Order getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	protected IClientRepository clientRepository;
	protected ArrayList<Client> clients;
	public List<Client> getClients(){ return clients; }

	protected IOrderStateRepository orderStateRepository;
	protected ArrayList<OrderState> orderStates;
	public List<OrderState> getOrderStates(){ return orderStates; }

	protected IOrderTypeRepository orderTypeRepository;
	protected ArrayList<OrderType> orderTypes;
	public List<OrderType> getOrderTypes(){ return orderTypes; }

	protected IImageRepository imageRepository;
	protected ArrayList<Image> imagesOptional;
	public List<Image> getImagesOptional(){ return imagesOptional; }

	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderEdit(IEditView view, IOrderRepository orderRepository, int[] id){
		super(view);

		this.orderRepository=orderRepository;
		this._itemId=id;
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.orderStateRepository=RepositoryManager.getInstance().getIOrderStateRepository();
		this.orderTypeRepository=RepositoryManager.getInstance().getIOrderTypeRepository();
		this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = orderRepository.get(_itemId);
		}
		else{
			this._item = orderRepository.getInstance();
		}
		this.entities=this.entityRepository.getAll();
		this.clients=this.clientRepository.getAll();
		this.orderStates=this.orderStateRepository.getAll();
		this.orderTypes=this.orderTypeRepository.getAll();

		this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		this.previewsOrderStateId = _item.OrderStateId;

		return true;
	}



	@Override
	public void close() {
		this.orderRepository.close();
		this.entityRepository.close();
		this.clientRepository.close();
		this.orderStateRepository.close();
		this.orderTypeRepository.close();
		this.imageRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{

		if(_item.OrderStateId == OrderStateEnum.Open){
			throw new ValidationException("OrderStateId", "Invalid state only clients can set this state");
		}

		if(_item.OrderStateId == OrderStateEnum.Submited && previewsOrderStateId!=OrderStateEnum.Submited){
			throw new ValidationException("OrderStateId", "The order was already submited");
		}

		boolean succes = false;
		User user = DeliveryApp.getUser();
		if(user!=null){
			_item.UpdateUserId= user.Id;
			_item.UpdateDate = new Date();
		}

		_item.ClientNotified = false;

		if(_itemId != null &&  _itemId.length > 0){
			succes=orderRepository.update(this._item);
		}else{

			_item.CreateDate = _item.UpdateDate;
			succes=orderRepository.create(this._item) > 0;

		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}