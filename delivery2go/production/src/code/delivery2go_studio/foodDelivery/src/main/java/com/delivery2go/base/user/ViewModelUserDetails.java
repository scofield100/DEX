package com.delivery2go.base.user;

import com.delivery2go.DeliveryApp;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.User;

public class ViewModelUserDetails extends DataViewModel implements IClosable {

	protected IUserRepository userRepository;
	protected User _item;
	int[] _itemId;
	public User getItem(){ return this._item;}

	public ViewModelUserDetails(IDataView view, IUserRepository userRepository, int[] id){
		super(view);

		this.userRepository=userRepository;
		this._itemId=id;

	}

	public boolean getShowUsername(){
		User user = DeliveryApp.getUser();
		return  user.Id == _itemId[0];
	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = userRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.userRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=userRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}