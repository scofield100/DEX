package com.delivery2go.base.roll;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Roll;

public class ViewModelRollList extends EntityCursorViewModel<Roll>{

	protected IRollRepository rollRepository;

	public ViewModelRollList (IDataView view, IRollRepository rollRepository) {
		super(view);
		
		this.rollRepository = rollRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Roll> createCursor()
			throws InvalidOperationException {		
		return this.rollRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.rollRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(rollRepository instanceof IAttachRepository<?>){
			IAttachRepository<Roll>attachedRep = (IAttachRepository<Roll>) rollRepository;
			if(!attachedRep.attach((Roll) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Roll obj = (Roll) items.get(i);
			rollRepository.delete(obj);
		}
		return true;
	}



}