package com.delivery2go.base.orderdishaddons;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderDishAddons;

import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelOrderDishAddonsEdit extends EditViewModel implements IClosable {

	protected IOrderDishAddonsRepository orderDishAddonsRepository;

	protected OrderDishAddons _item;
	protected int[] _itemId;
	public OrderDishAddons getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IOrderDishRepository orderDishRepository;
	protected ArrayList<OrderDish> orderDishs;
	public List<OrderDish> getOrderDishs(){ return orderDishs; }

	protected IAddonsRepository addonsRepository;
	protected ArrayList<Addons> addonses;
	public List<Addons> getAddonses(){ return addonses; }

	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderDishAddonsEdit(IEditView view, IOrderDishAddonsRepository orderDishAddonsRepository, int[] id){
		super(view);

		this.orderDishAddonsRepository=orderDishAddonsRepository;
		this._itemId=id;
		this.orderDishRepository=RepositoryManager.getInstance().getIOrderDishRepository();
		this.addonsRepository=RepositoryManager.getInstance().getIAddonsRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = orderDishAddonsRepository.get(_itemId);
		}
		else{
			this._item = orderDishAddonsRepository.getInstance();
		}
		this.orderDishs=this.orderDishRepository.getAll();
		this.addonses=this.addonsRepository.getAll();
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return true;
	}



	@Override
	public void close() {
		this.orderDishAddonsRepository.close();
		this.orderDishRepository.close();
		this.addonsRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=orderDishAddonsRepository.update(this._item);
		}else{
			succes=orderDishAddonsRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}