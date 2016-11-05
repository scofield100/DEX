package com.delivery2go.base.orderstate;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderState;

public class ViewModelOrderStateEdit extends EditViewModel implements IClosable {

	protected IOrderStateRepository orderStateRepository;

	protected OrderState _item;
	protected int[] _itemId;
	public OrderState getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelOrderStateEdit(IEditView view, IOrderStateRepository orderStateRepository, int[] id){
		super(view);

		this.orderStateRepository=orderStateRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = orderStateRepository.get(_itemId);
		}
		else{
			this._item = orderStateRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.orderStateRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=orderStateRepository.update(this._item);
		}else{
			succes=orderStateRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}