package com.delivery2go.base.cliententityfavorites;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.ClientEntityFavorites;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelClientEntityFavoritesEdit extends EditViewModel implements IClosable {

	protected IClientEntityFavoritesRepository clientEntityFavoritesRepository;

	protected ClientEntityFavorites _item;
	protected int[] _itemId;
	public ClientEntityFavorites getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IClientRepository clientRepository;
	protected ArrayList<Client> clients;
	public List<Client> getClients(){ return clients; }

	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	public ViewModelClientEntityFavoritesEdit(IEditView view, IClientEntityFavoritesRepository clientEntityFavoritesRepository, int[] id){
		super(view);

		this.clientEntityFavoritesRepository=clientEntityFavoritesRepository;
		this._itemId=id;
		this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = clientEntityFavoritesRepository.get(_itemId);
		}
		else{
			this._item = clientEntityFavoritesRepository.getInstance();
		}
		this.clients=this.clientRepository.getAll();
		this.entities=this.entityRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.clientEntityFavoritesRepository.close();
		this.clientRepository.close();
		this.entityRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=clientEntityFavoritesRepository.update(this._item);
		}else{
			succes=clientEntityFavoritesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}