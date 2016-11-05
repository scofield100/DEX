package com.delivery2go.base.orderstate;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.models.OrderState;

public class ViewModelOrderStateDetails extends DataViewModel implements IClosable {

	protected IOrderStateRepository orderStateRepository;
	protected OrderState _item;
	int[] _itemId;
	public OrderState getItem(){ return this._item;}

	public ViewModelOrderStateDetails(IDataView view, IOrderStateRepository orderStateRepository, int[] id){
		super(view);

		this.orderStateRepository=orderStateRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = orderStateRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.orderStateRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=orderStateRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}