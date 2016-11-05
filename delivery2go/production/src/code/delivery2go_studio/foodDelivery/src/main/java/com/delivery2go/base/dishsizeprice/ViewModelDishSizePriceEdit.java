package com.delivery2go.base.dishsizeprice;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishSizePrice;

import com.delivery2go.base.models.DishSize;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishSizePriceEdit extends EditViewModel implements IClosable {

	protected IDishSizePriceRepository dishSizePriceRepository;

	protected DishSizePrice _item;
	protected int[] _itemId;
	public DishSizePrice getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IDishSizeRepository dishSizeRepository;
	protected ArrayList<DishSize> dishSizes;
	public List<DishSize> getDishSizes(){ return dishSizes; }

	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelDishSizePriceEdit(IEditView view, IDishSizePriceRepository dishSizePriceRepository, int[] id){
		super(view);

		this.dishSizePriceRepository=dishSizePriceRepository;
		this._itemId=id;
		this.dishSizeRepository=RepositoryManager.getInstance().getIDishSizeRepository();
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishSizePriceRepository.get(_itemId);
		}
		else{
			this._item = dishSizePriceRepository.getInstance();
		}
		this.dishSizes=this.dishSizeRepository.getAll();
		this.dishs=this.dishRepository.getAll();
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return true;
	}



	@Override
	public void close() {
		this.dishSizePriceRepository.close();
		this.dishSizeRepository.close();
		this.dishRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;

		DishSizePrice other = dishSizePriceRepository.getFirst(String.format("SizeId=%d and DishId=%d and Id!=%d", _item.SizeId, _item.DishId, _item.Id));
		if(other!=null){
			throw new ValidationException("SizeId", getView().getString(R.string.val_unique_size_price));
		}

		_item.UpdateDate = new Date();
		_item.UpdateUserId = DeliveryApp.getUser().Id;

		if(_itemId != null &&  _itemId.length > 0){
			succes=dishSizePriceRepository.update(this._item);
		}else{
			succes=dishSizePriceRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}