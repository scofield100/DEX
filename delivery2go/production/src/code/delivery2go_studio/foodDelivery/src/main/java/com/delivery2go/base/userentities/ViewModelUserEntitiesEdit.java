package com.delivery2go.base.userentities;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.UserEntities;

import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelUserEntitiesEdit extends EditViewModel implements IClosable {

	protected IUserEntitiesRepository userEntitiesRepository;

	protected UserEntities _item;
	protected int[] _itemId;
	public UserEntities getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IUserRepository userRepository;
	protected ArrayList<User> users;
	public List<User> getUsers(){ return users; }

	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	public ViewModelUserEntitiesEdit(IEditView view, IUserEntitiesRepository userEntitiesRepository, int[] id){
		super(view);

		this.userEntitiesRepository=userEntitiesRepository;
		this._itemId=id;
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = userEntitiesRepository.get(_itemId);
		}
		else{
			this._item = userEntitiesRepository.getInstance();
		}
		this.users=this.userRepository.getAll();
		this.entities=this.entityRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.userEntitiesRepository.close();
		this.userRepository.close();
		this.entityRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=userEntitiesRepository.update(this._item);
		}else{
			succes=userEntitiesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}