package com.delivery2go.base.entityreview;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityReviewRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityReview;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelEntityReviewList extends EntityCursorViewModel<EntityReview>{

	protected IEntityReviewRepository entityReviewRepository;


	//protected IClientRepository clientRepository;
	//protected ArrayList<Client> clientsOptional;
	//public List<Client> getClientsOptional(){ return clientsOptional; }

	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	public ViewModelEntityReviewList (IDataView view, IEntityReviewRepository entityReviewRepository) {
		super(view);
		
		this.entityReviewRepository = entityReviewRepository;   

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
	protected IEntityCursor<EntityReview> createCursor()
			throws InvalidOperationException {		
		return this.entityReviewRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.entityReviewRepository.close();
		//this.clientRepository.close();
		//this.entityRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityReviewRepository instanceof IAttachRepository<?>){
			IAttachRepository<EntityReview>attachedRep = (IAttachRepository<EntityReview>) entityReviewRepository;
			if(!attachedRep.attach((EntityReview) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			EntityReview obj = (EntityReview) items.get(i);
			entityReviewRepository.delete(obj);
		}
		return true;
	}



}