package com.delivery2go.base.state;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.models.State;

public class ViewModelStateDetails extends DataViewModel implements IClosable {

	protected IStateRepository stateRepository;
	protected State _item;
	int[] _itemId;
	public State getItem(){ return this._item;}

	public ViewModelStateDetails(IDataView view, IStateRepository stateRepository, int[] id){
		super(view);

		this.stateRepository=stateRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = stateRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.stateRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=stateRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}