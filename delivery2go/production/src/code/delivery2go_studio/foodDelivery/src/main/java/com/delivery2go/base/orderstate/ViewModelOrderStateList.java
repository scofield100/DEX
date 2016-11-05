package com.delivery2go.base.orderstate;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderState;

public class ViewModelOrderStateList extends EntityCursorViewModel<OrderState>{

	protected IOrderStateRepository orderStateRepository;

	public ViewModelOrderStateList (IDataView view, IOrderStateRepository orderStateRepository) {
		super(view);
		
		this.orderStateRepository = orderStateRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<OrderState> createCursor()
			throws InvalidOperationException {		
		return this.orderStateRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.orderStateRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(orderStateRepository instanceof IAttachRepository<?>){
			IAttachRepository<OrderState>attachedRep = (IAttachRepository<OrderState>) orderStateRepository;
			if(!attachedRep.attach((OrderState) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			OrderState obj = (OrderState) items.get(i);
			orderStateRepository.delete(obj);
		}
		return true;
	}



}