package com.delivery2go.base.entitycategories;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityCategories;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class ViewModelEntityCategoriesList extends EntityCursorViewModel<EntityCategories>{

	protected IEntityCategoriesRepository entityCategoriesRepository;


	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IEntityCategoryRepository entityCategoryRepository;
	//protected ArrayList<EntityCategory> entityCategoriesOptional;
	//public List<EntityCategory> getEntityCategoriesOptional(){ return entityCategoriesOptional; }

	public ViewModelEntityCategoriesList (IDataView view, IEntityCategoriesRepository entityCategoriesRepository) {
		super(view);
		
		this.entityCategoriesRepository = entityCategoriesRepository;   

		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.entityCategoryRepository=RepositoryManager.getInstance().getIEntityCategoryRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.entityCategoriesOptional=BaseModel.asOptionalList(EntityCategory.class, entityCategoryRepository.getAll(), new EntityCategory());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<EntityCategories> createCursor()
			throws InvalidOperationException {		
		return this.entityCategoriesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.entityCategoriesRepository.close();
		//this.entityRepository.close();
		//this.entityCategoryRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityCategoriesRepository instanceof IAttachRepository<?>){
			IAttachRepository<EntityCategories>attachedRep = (IAttachRepository<EntityCategories>) entityCategoriesRepository;
			if(!attachedRep.attach((EntityCategories) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			EntityCategories obj = (EntityCategories) items.get(i);
			entityCategoriesRepository.delete(obj);
		}
		return true;
	}



}