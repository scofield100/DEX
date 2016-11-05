package com.delivery2go.base.userentities;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;
import com.delivery2go.base.models.UserEntities;

public class ViewModelUserEntitiesDetails extends DataViewModel implements IClosable {

	public static final int USER = 1;
	public static final int ENTITY = 2;

	protected IUserEntitiesRepository userEntitiesRepository;
	protected UserEntities _item;
	int[] _itemId;
	public UserEntities getItem(){ return this._item;}


	public Command NavigateToUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(USER, null, _item.UserId);
			}
		}
	};

	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public ViewModelUserEntitiesDetails(IDataView view, IUserEntitiesRepository userEntitiesRepository, int[] id){
		super(view);

		this.userEntitiesRepository=userEntitiesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = userEntitiesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.userEntitiesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=userEntitiesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}