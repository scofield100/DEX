package com.delivery2go.base.orderdish;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderDish;

import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelOrderDishEdit extends EditViewModel implements IClosable {

	protected IOrderDishRepository orderDishRepository;

	protected OrderDish _item;
	protected int[] _itemId;
	public OrderDish getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IOrderRepository orderRepository;
	protected ArrayList<Order> orders;
	public List<Order> getOrders(){ return orders; }

	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	protected IDishSizePriceRepository dishSizePriceRepository;
	protected ArrayList<DishSizePrice> dishSizePricesOptional;
	public List<DishSizePrice> getDishSizePricesOptional(){ return dishSizePricesOptional; }

	protected IAddonsRepository addonsRepository;
	protected ArrayList<Addons> addonsesOptional;
	public List<Addons> getAddonsesOptional(){ return addonsesOptional; }

	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderDishEdit(IEditView view, IOrderDishRepository orderDishRepository, int[] id){
		super(view);

		this.orderDishRepository=orderDishRepository;
		this._itemId=id;
		this.orderRepository=RepositoryManager.getInstance().getIOrderRepository();
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		this.dishSizePriceRepository=RepositoryManager.getInstance().getIDishSizePriceRepository();
		this.addonsRepository=RepositoryManager.getInstance().getIAddonsRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = orderDishRepository.get(_itemId);
		}
		else{
			this._item = orderDishRepository.getInstance();
		}
		this.orders=this.orderRepository.getAll();
		this.dishs=this.dishRepository.getAll();
		this.dishSizePricesOptional=BaseModel.asOptionalList(DishSizePrice.class, dishSizePriceRepository.getAll(), new DishSizePrice());
		this.addonsesOptional=BaseModel.asOptionalList(Addons.class, addonsRepository.getAll(), new Addons());
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return true;
	}



	@Override
	public void close() {
		this.orderDishRepository.close();
		this.orderRepository.close();
		this.dishRepository.close();
		this.dishSizePriceRepository.close();
		this.addonsRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=orderDishRepository.update(this._item);
		}else{
			succes=orderDishRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}