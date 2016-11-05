package com.delivery2go.base.state;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.State;

public class ViewModelStateEdit extends EditViewModel implements IClosable {

	protected IStateRepository stateRepository;

	protected State _item;
	protected int[] _itemId;
	public State getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelStateEdit(IEditView view, IStateRepository stateRepository, int[] id){
		super(view);

		this.stateRepository=stateRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = stateRepository.get(_itemId);
		}
		else{
			this._item = stateRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.stateRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=stateRepository.update(this._item);
		}else{
			succes=stateRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}