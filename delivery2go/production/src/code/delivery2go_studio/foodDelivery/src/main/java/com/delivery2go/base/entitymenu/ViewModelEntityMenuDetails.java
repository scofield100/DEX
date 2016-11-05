package com.delivery2go.base.entitymenu;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.models.EntityMenu;

public class ViewModelEntityMenuDetails extends DataViewModel implements IClosable {

	public static final int ENTITY = 1;
	public static final int IMAGE = 2;
	public static final int UPDATEUSER = 3;

	protected IEntityMenuRepository entityMenuRepository;
	protected EntityMenu _item;
	int[] _itemId;
	public EntityMenu getItem(){ return this._item;}


	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public Command NavigateToImage= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.ImageId!=null){
				getNavigator().navigateTo(IMAGE, null, _item.ImageId);
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

	public ViewModelEntityMenuDetails(IDataView view, IEntityMenuRepository entityMenuRepository, int[] id){
		super(view);

		this.entityMenuRepository=entityMenuRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityMenuRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.entityMenuRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityMenuRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}