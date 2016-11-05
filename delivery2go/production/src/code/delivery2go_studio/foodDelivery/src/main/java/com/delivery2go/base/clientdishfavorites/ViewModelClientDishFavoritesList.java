package com.delivery2go.base.clientdishfavorites;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.ClientDishFavorites;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class ViewModelClientDishFavoritesList extends EntityCursorViewModel<ClientDishFavorites>{

	protected IClientDishFavoritesRepository clientDishFavoritesRepository;


	//protected IClientRepository clientRepository;
	//protected ArrayList<Client> clientsOptional;
	//public List<Client> getClientsOptional(){ return clientsOptional; }

	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	public ViewModelClientDishFavoritesList (IDataView view, IClientDishFavoritesRepository clientDishFavoritesRepository) {
		super(view);
		
		this.clientDishFavoritesRepository = clientDishFavoritesRepository;   

		//this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.clientsOptional=BaseModel.asOptionalList(Client.class, clientRepository.getAll(), new Client());
		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<ClientDishFavorites> createCursor()
			throws InvalidOperationException {		
		return this.clientDishFavoritesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.clientDishFavoritesRepository.close();
		//this.clientRepository.close();
		//this.dishRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(clientDishFavoritesRepository instanceof IAttachRepository<?>){
			IAttachRepository<ClientDishFavorites>attachedRep = (IAttachRepository<ClientDishFavorites>) clientDishFavoritesRepository;
			if(!attachedRep.attach((ClientDishFavorites) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			ClientDishFavorites obj = (ClientDishFavorites) items.get(i);
			clientDishFavoritesRepository.delete(obj);
		}
		return true;
	}



}