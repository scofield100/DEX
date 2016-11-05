package com.delivery2go.base.cliententityfavorites;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.models.ClientEntityFavorites;

public class ViewModelClientEntityFavoritesDetails extends DataViewModel implements IClosable {

	public static final int CLIENT = 1;
	public static final int ENTITY = 2;

	protected IClientEntityFavoritesRepository clientEntityFavoritesRepository;
	protected ClientEntityFavorites _item;
	int[] _itemId;
	public ClientEntityFavorites getItem(){ return this._item;}


	public Command NavigateToClient= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CLIENT, null, _item.ClientId);
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

	public ViewModelClientEntityFavoritesDetails(IDataView view, IClientEntityFavoritesRepository clientEntityFavoritesRepository, int[] id){
		super(view);

		this.clientEntityFavoritesRepository=clientEntityFavoritesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = clientEntityFavoritesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.clientEntityFavoritesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=clientEntityFavoritesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}