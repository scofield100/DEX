package com.delivery2go.base.ordertype;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderType;

public class ViewModelOrderTypeEdit extends EditViewModel implements IClosable {

	protected IOrderTypeRepository orderTypeRepository;

	protected OrderType _item;
	protected int[] _itemId;
	public OrderType getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelOrderTypeEdit(IEditView view, IOrderTypeRepository orderTypeRepository, int[] id){
		super(view);

		this.orderTypeRepository=orderTypeRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = orderTypeRepository.get(_itemId);
		}
		else{
			this._item = orderTypeRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.orderTypeRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=orderTypeRepository.update(this._item);
		}else{
			succes=orderTypeRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}