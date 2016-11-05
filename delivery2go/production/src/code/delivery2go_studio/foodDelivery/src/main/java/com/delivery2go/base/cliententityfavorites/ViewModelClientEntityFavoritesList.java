package com.delivery2go.base.cliententityfavorites;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.ClientEntityFavorites;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelClientEntityFavoritesList extends EntityCursorViewModel<ClientEntityFavorites>{

	protected IClientEntityFavoritesRepository clientEntityFavoritesRepository;


	//protected IClientRepository clientRepository;
	//protected ArrayList<Client> clientsOptional;
	//public List<Client> getClientsOptional(){ return clientsOptional; }

	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	public ViewModelClientEntityFavoritesList (IDataView view, IClientEntityFavoritesRepository clientEntityFavoritesRepository) {
		super(view);
		
		this.clientEntityFavoritesRepository = clientEntityFavoritesRepository;   

		//this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.clientsOptional=BaseModel.asOptionalList(Client.class, clientRepository.getAll(), new Client());
		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<ClientEntityFavorites> createCursor()
			throws InvalidOperationException {		
		return this.clientEntityFavoritesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.clientEntityFavoritesRepository.close();
		//this.clientRepository.close();
		//this.entityRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(clientEntityFavoritesRepository instanceof IAttachRepository<?>){
			IAttachRepository<ClientEntityFavorites>attachedRep = (IAttachRepository<ClientEntityFavorites>) clientEntityFavoritesRepository;
			if(!attachedRep.attach((ClientEntityFavorites) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			ClientEntityFavorites obj = (ClientEntityFavorites) items.get(i);
			clientEntityFavoritesRepository.delete(obj);
		}
		return true;
	}



}