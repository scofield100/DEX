package com.delivery2go.base.clientdishfavorites;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.models.ClientDishFavorites;

public class ViewModelClientDishFavoritesDetails extends DataViewModel implements IClosable {

	public static final int CLIENT = 1;
	public static final int DISH = 2;

	protected IClientDishFavoritesRepository clientDishFavoritesRepository;
	protected ClientDishFavorites _item;
	int[] _itemId;
	public ClientDishFavorites getItem(){ return this._item;}


	public Command NavigateToClient= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CLIENT, null, _item.ClientId);
			}
		}
	};

	public Command NavigateToDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(DISH, null, _item.DishId);
			}
		}
	};

	public ViewModelClientDishFavoritesDetails(IDataView view, IClientDishFavoritesRepository clientDishFavoritesRepository, int[] id){
		super(view);

		this.clientDishFavoritesRepository=clientDishFavoritesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = clientDishFavoritesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.clientDishFavoritesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=clientDishFavoritesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}