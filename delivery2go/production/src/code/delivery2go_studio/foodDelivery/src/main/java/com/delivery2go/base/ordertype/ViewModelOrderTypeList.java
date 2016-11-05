package com.delivery2go.base.ordertype;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderType;

public class ViewModelOrderTypeList extends EntityCursorViewModel<OrderType>{

	protected IOrderTypeRepository orderTypeRepository;

	public ViewModelOrderTypeList (IDataView view, IOrderTypeRepository orderTypeRepository) {
		super(view);
		
		this.orderTypeRepository = orderTypeRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<OrderType> createCursor()
			throws InvalidOperationException {		
		return this.orderTypeRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.orderTypeRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(orderTypeRepository instanceof IAttachRepository<?>){
			IAttachRepository<OrderType>attachedRep = (IAttachRepository<OrderType>) orderTypeRepository;
			if(!attachedRep.attach((OrderType) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			OrderType obj = (OrderType) items.get(i);
			orderTypeRepository.delete(obj);
		}
		return true;
	}



}