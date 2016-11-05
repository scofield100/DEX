package com.delivery2go.base.image;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Image;

import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelImageEdit extends EditViewModel implements IClosable {

	protected IImageRepository imageRepository;

	protected Image _item;
	protected int[] _itemId;
	public Image getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelImageEdit(IEditView view, IImageRepository imageRepository, int[] id){
		super(view);

		this.imageRepository=imageRepository;
		this._itemId=id;
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = imageRepository.get(_itemId);
		}
		else{
			this._item = imageRepository.getInstance();
		}
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return true;
	}



	@Override
	public void close() {
		this.imageRepository.close();
		this.userRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=imageRepository.update(this._item);
		}else{
			succes=imageRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}