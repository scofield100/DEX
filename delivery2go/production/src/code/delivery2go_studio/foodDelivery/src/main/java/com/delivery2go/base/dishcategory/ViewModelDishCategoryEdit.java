package com.delivery2go.base.dishcategory;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategory;

import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishCategoryEdit extends EditViewModel implements IClosable {

	protected IDishCategoryRepository dishCategoryRepository;

	protected DishCategory _item;
	protected int[] _itemId;
	public DishCategory getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}



	public ViewModelDishCategoryEdit(IEditView view, IDishCategoryRepository dishCategoryRepository, int[] id){
		super(view);

		this.dishCategoryRepository=dishCategoryRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishCategoryRepository.get(_itemId);
		}
		else{
			this._item = dishCategoryRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.dishCategoryRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;

		_item.UpdateDate = new Date();

		if(DeliveryApp.getUser()!=null)
			_item.UpdateUserId = DeliveryApp.getUser().Id;

		if(_itemId != null &&  _itemId.length > 0){
			succes=dishCategoryRepository.update(this._item);
		}else{
			succes=dishCategoryRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}