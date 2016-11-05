package com.delivery2go.base.permission;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Permission;

import com.delivery2go.base.models.Roll;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelPermissionEdit extends EditViewModel implements IClosable {

	protected IPermissionRepository permissionRepository;

	protected Permission _item;
	protected int[] _itemId;
	public Permission getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IRollRepository rollRepository;
	protected ArrayList<Roll> rolls;
	public List<Roll> getRolls(){ return rolls; }

	protected IUserRepository userRepository;
	protected ArrayList<User> users;
	public List<User> getUsers(){ return users; }

	public ViewModelPermissionEdit(IEditView view, IPermissionRepository permissionRepository, int[] id){
		super(view);

		this.permissionRepository=permissionRepository;
		this._itemId=id;
		this.rollRepository=RepositoryManager.getInstance().getIRollRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = permissionRepository.get(_itemId);
		}
		else{
			this._item = permissionRepository.getInstance();
		}
		this.rolls=this.rollRepository.getAll();
		this.users=this.userRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.permissionRepository.close();
		this.rollRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=permissionRepository.update(this._item);
		}else{
			succes=permissionRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}