package com.delivery2go.base.dishsize;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishSize;

public class ViewModelDishSizeEdit extends EditViewModel implements IClosable {

	protected IDishSizeRepository dishSizeRepository;

	protected DishSize _item;
	protected int[] _itemId;
	public DishSize getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelDishSizeEdit(IEditView view, IDishSizeRepository dishSizeRepository, int[] id){
		super(view);

		this.dishSizeRepository=dishSizeRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishSizeRepository.get(_itemId);
		}
		else{
			this._item = dishSizeRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.dishSizeRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;


		if(_itemId != null &&  _itemId.length > 0){
			succes=dishSizeRepository.update(this._item);
		}else{
			succes=dishSizeRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}