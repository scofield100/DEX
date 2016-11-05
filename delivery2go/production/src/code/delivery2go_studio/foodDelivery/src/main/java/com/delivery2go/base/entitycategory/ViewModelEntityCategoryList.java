package com.delivery2go.base.entitycategory;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityCategory;

public class ViewModelEntityCategoryList extends EntityCursorViewModel<EntityCategory>{

	protected IEntityCategoryRepository entityCategoryRepository;

	public ViewModelEntityCategoryList (IDataView view, IEntityCategoryRepository entityCategoryRepository) {
		super(view);
		
		this.entityCategoryRepository = entityCategoryRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<EntityCategory> createCursor()
			throws InvalidOperationException {		
		return this.entityCategoryRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.entityCategoryRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityCategoryRepository instanceof IAttachRepository<?>){
			IAttachRepository<EntityCategory>attachedRep = (IAttachRepository<EntityCategory>) entityCategoryRepository;
			if(!attachedRep.attach((EntityCategory) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			EntityCategory obj = (EntityCategory) items.get(i);
			entityCategoryRepository.delete(obj);
		}
		return true;
	}



}