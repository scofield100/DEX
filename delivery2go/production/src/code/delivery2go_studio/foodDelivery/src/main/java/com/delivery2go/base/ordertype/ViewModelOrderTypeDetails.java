package com.delivery2go.base.ordertype;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.models.OrderType;

public class ViewModelOrderTypeDetails extends DataViewModel implements IClosable {

	protected IOrderTypeRepository orderTypeRepository;
	protected OrderType _item;
	int[] _itemId;
	public OrderType getItem(){ return this._item;}

	public ViewModelOrderTypeDetails(IDataView view, IOrderTypeRepository orderTypeRepository, int[] id){
		super(view);

		this.orderTypeRepository=orderTypeRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = orderTypeRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.orderTypeRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=orderTypeRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}