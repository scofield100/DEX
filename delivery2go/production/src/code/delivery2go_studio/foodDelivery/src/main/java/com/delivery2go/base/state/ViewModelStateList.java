package com.delivery2go.base.state;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.State;

public class ViewModelStateList extends EntityCursorViewModel<State>{

	protected IStateRepository stateRepository;

	public ViewModelStateList (IDataView view, IStateRepository stateRepository) {
		super(view);
		
		this.stateRepository = stateRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<State> createCursor()
			throws InvalidOperationException {		
		return this.stateRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.stateRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(stateRepository instanceof IAttachRepository<?>){
			IAttachRepository<State>attachedRep = (IAttachRepository<State>) stateRepository;
			if(!attachedRep.attach((State) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			State obj = (State) items.get(i);
			stateRepository.delete(obj);
		}
		return true;
	}



}