package com.delivery2go.base.addons;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.models.Addons;

public class ViewModelAddonsDetails extends DataViewModel implements IClosable {

	public static final int ENTITY = 1;
	public static final int UPDATEUSER = 2;

	protected IAddonsRepository addonsRepository;
	protected Addons _item;
	int[] _itemId;
	public Addons getItem(){ return this._item;}


	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public ViewModelAddonsDetails(IDataView view, IAddonsRepository addonsRepository, int[] id){
		super(view);

		this.addonsRepository=addonsRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = addonsRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.addonsRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=addonsRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}