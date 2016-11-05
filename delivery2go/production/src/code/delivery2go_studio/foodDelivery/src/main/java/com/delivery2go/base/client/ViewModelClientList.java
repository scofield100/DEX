package com.delivery2go.base.client;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Client;

public class ViewModelClientList extends EntityCursorViewModel<Client>{

	protected IClientRepository clientRepository;

	public ViewModelClientList (IDataView view, IClientRepository clientRepository) {
		super(view);
		
		this.clientRepository = clientRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Client> createCursor()
			throws InvalidOperationException {		
		return this.clientRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.clientRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(clientRepository instanceof IAttachRepository<?>){
			IAttachRepository<Client>attachedRep = (IAttachRepository<Client>) clientRepository;
			if(!attachedRep.attach((Client) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Client obj = (Client) items.get(i);
			clientRepository.delete(obj);
		}
		return true;
	}



}