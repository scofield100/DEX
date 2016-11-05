package com.delivery2go.base.entityimages;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityImagesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityImages;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ViewModelEntityImagesList extends EntityCursorViewModel<EntityImages>{

	protected IEntityImagesRepository entityImagesRepository;


	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	public ViewModelEntityImagesList (IDataView view, IEntityImagesRepository entityImagesRepository) {
		super(view);
		
		this.entityImagesRepository = entityImagesRepository;   

		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<EntityImages> createCursor()
			throws InvalidOperationException {		
		return this.entityImagesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.entityImagesRepository.close();
		//this.entityRepository.close();
		//this.imageRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityImagesRepository instanceof IAttachRepository<?>){
			IAttachRepository<EntityImages>attachedRep = (IAttachRepository<EntityImages>) entityImagesRepository;
			if(!attachedRep.attach((EntityImages) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			EntityImages obj = (EntityImages) items.get(i);
			entityImagesRepository.delete(obj);
		}
		return true;
	}



}