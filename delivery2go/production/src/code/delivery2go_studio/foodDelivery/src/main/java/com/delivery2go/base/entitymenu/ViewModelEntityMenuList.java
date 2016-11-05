package com.delivery2go.base.entitymenu;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityMenu;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelEntityMenuList extends EntityCursorViewModel<EntityMenu>{

	protected IEntityMenuRepository entityMenuRepository;


	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelEntityMenuList (IDataView view, IEntityMenuRepository entityMenuRepository) {
		super(view);
		
		this.entityMenuRepository = entityMenuRepository;   

		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<EntityMenu> createCursor()
			throws InvalidOperationException {		
		return this.entityMenuRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.entityMenuRepository.close();
		//this.entityRepository.close();
		//this.imageRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityMenuRepository instanceof IAttachRepository<?>){
			IAttachRepository<EntityMenu>attachedRep = (IAttachRepository<EntityMenu>) entityMenuRepository;
			if(!attachedRep.attach((EntityMenu) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			EntityMenu obj = (EntityMenu) items.get(i);
			entityMenuRepository.delete(obj);
		}
		return true;
	}



}