package com.delivery2go.base.permission;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.models.Permission;

public class ViewModelPermissionDetails extends DataViewModel implements IClosable {

	public static final int ROLL = 1;
	public static final int USER = 2;

	protected IPermissionRepository permissionRepository;
	protected Permission _item;
	int[] _itemId;
	public Permission getItem(){ return this._item;}


	public Command NavigateToRoll= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ROLL, null, _item.RollId);
			}
		}
	};

	public Command NavigateToUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(USER, null, _item.UserId);
			}
		}
	};

	public ViewModelPermissionDetails(IDataView view, IPermissionRepository permissionRepository, int[] id){
		super(view);

		this.permissionRepository=permissionRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = permissionRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.permissionRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=permissionRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}