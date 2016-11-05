package com.delivery2go.base.dishsize;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.models.DishSize;

public class ViewModelDishSizeDetails extends DataViewModel implements IClosable {

	protected IDishSizeRepository dishSizeRepository;
	protected DishSize _item;
	int[] _itemId;
	public DishSize getItem(){ return this._item;}

	public ViewModelDishSizeDetails(IDataView view, IDishSizeRepository dishSizeRepository, int[] id){
		super(view);

		this.dishSizeRepository=dishSizeRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishSizeRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishSizeRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishSizeRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}