package com.delivery2go.base.dishsize;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishSize;

public class ViewModelDishSizeList extends EntityCursorViewModel<DishSize>{

	protected IDishSizeRepository dishSizeRepository;

	public ViewModelDishSizeList (IDataView view, IDishSizeRepository dishSizeRepository) {
		super(view);
		
		this.dishSizeRepository = dishSizeRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishSize> createCursor()
			throws InvalidOperationException {		
		return this.dishSizeRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishSizeRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishSizeRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishSize>attachedRep = (IAttachRepository<DishSize>) dishSizeRepository;
			if(!attachedRep.attach((DishSize) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishSize obj = (DishSize) items.get(i);
			dishSizeRepository.delete(obj);
		}
		return true;
	}



}