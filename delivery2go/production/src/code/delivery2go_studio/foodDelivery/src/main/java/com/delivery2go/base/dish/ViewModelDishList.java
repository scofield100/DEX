package com.delivery2go.base.dish;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;

import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishList extends EntityCursorViewModel<Dish>{

	protected IDishRepository dishRepository;


	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IEntityMenuRepository entityMenuRepository;
	//protected ArrayList<EntityMenu> entityMenusOptional;
	//public List<EntityMenu> getEntityMenusOptional(){ return entityMenusOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelDishList (IDataView view, IDishRepository dishRepository) {
		super(view);
		
		this.dishRepository = dishRepository;   

		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.entityMenuRepository=RepositoryManager.getInstance().getIEntityMenuRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());
		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.entityMenusOptional=BaseModel.asOptionalList(EntityMenu.class, entityMenuRepository.getAll(), new EntityMenu());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Dish> createCursor()
			throws InvalidOperationException {		
		return this.dishRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishRepository.close();
		//this.imageRepository.close();
		//this.entityRepository.close();
		//this.entityMenuRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishRepository instanceof IAttachRepository<?>){
			IAttachRepository<Dish>attachedRep = (IAttachRepository<Dish>) dishRepository;
			if(!attachedRep.attach((Dish) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Dish obj = (Dish) items.get(i);
			dishRepository.delete(obj);
		}
		return true;
	}



}