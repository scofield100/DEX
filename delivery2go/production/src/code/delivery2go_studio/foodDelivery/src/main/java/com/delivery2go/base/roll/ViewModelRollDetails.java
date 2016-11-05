package com.delivery2go.base.roll;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.models.Roll;

public class ViewModelRollDetails extends DataViewModel implements IClosable {

	protected IRollRepository rollRepository;
	protected Roll _item;
	int[] _itemId;
	public Roll getItem(){ return this._item;}

	public ViewModelRollDetails(IDataView view, IRollRepository rollRepository, int[] id){
		super(view);

		this.rollRepository=rollRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = rollRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.rollRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=rollRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}