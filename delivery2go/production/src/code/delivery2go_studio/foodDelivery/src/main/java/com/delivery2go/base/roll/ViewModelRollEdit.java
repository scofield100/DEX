package com.delivery2go.base.roll;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Roll;

public class ViewModelRollEdit extends EditViewModel implements IClosable {

	protected IRollRepository rollRepository;

	protected Roll _item;
	protected int[] _itemId;
	public Roll getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelRollEdit(IEditView view, IRollRepository rollRepository, int[] id){
		super(view);

		this.rollRepository=rollRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = rollRepository.get(_itemId);
		}
		else{
			this._item = rollRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.rollRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=rollRepository.update(this._item);
		}else{
			succes=rollRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}