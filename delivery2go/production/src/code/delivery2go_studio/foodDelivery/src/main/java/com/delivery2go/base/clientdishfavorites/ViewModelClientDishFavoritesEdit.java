package com.delivery2go.base.clientdishfavorites;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.ClientDishFavorites;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class ViewModelClientDishFavoritesEdit extends EditViewModel implements IClosable {

	protected IClientDishFavoritesRepository clientDishFavoritesRepository;

	protected ClientDishFavorites _item;
	protected int[] _itemId;
	public ClientDishFavorites getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IClientRepository clientRepository;
	protected ArrayList<Client> clients;
	public List<Client> getClients(){ return clients; }

	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	public ViewModelClientDishFavoritesEdit(IEditView view, IClientDishFavoritesRepository clientDishFavoritesRepository, int[] id){
		super(view);

		this.clientDishFavoritesRepository=clientDishFavoritesRepository;
		this._itemId=id;
		this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = clientDishFavoritesRepository.get(_itemId);
		}
		else{
			this._item = clientDishFavoritesRepository.getInstance();
		}
		this.clients=this.clientRepository.getAll();
		this.dishs=this.dishRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.clientDishFavoritesRepository.close();
		this.clientRepository.close();
		this.dishRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=clientDishFavoritesRepository.update(this._item);
		}else{
			succes=clientDishFavoritesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}