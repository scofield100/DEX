package com.delivery2go.base.dish;

import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.Dish;

public class ViewModelDishDetails extends DataViewModel implements IClosable {

	public static final int IMAGE = 1;
	public static final int ENTITY = 2;
	public static final int MENU = 3;
	public static final int UPDATEUSER = 4;

	protected IDishRepository dishRepository;
	protected Dish _item;
	int[] _itemId;
	public Dish getItem(){ return this._item;}


	public Command NavigateToImage= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.ImageId!=null){
				getNavigator().navigateTo(IMAGE, null, _item.ImageId);
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

	public Command NavigateToMenu= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.MenuId!=null){
				getNavigator().navigateTo(MENU, null, _item.MenuId);
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

	public ViewModelDishDetails(IDataView view, IDishRepository dishRepository, int[] id){
		super(view);

		this.dishRepository=dishRepository;
		this._itemId=id;

		if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.READ)){
			NavigateToMenu.setEnabled(false);
		}

        if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.User}, Access.READ)){
            NavigateToUpdateUser.setEnabled(false);
        }

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}