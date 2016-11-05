package com.delivery2go.base.userentities;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.UserEntities;

import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelUserEntitiesList extends EntityCursorViewModel<UserEntities>{

	protected IUserEntitiesRepository userEntitiesRepository;


	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	public ViewModelUserEntitiesList (IDataView view, IUserEntitiesRepository userEntitiesRepository) {
		super(view);
		
		this.userEntitiesRepository = userEntitiesRepository;   

		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());
		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<UserEntities> createCursor()
			throws InvalidOperationException {		
		return this.userEntitiesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.userEntitiesRepository.close();
		//this.userRepository.close();
		//this.entityRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(userEntitiesRepository instanceof IAttachRepository<?>){
			IAttachRepository<UserEntities>attachedRep = (IAttachRepository<UserEntities>) userEntitiesRepository;
			if(!attachedRep.attach((UserEntities) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			UserEntities obj = (UserEntities) items.get(i);
			userEntitiesRepository.delete(obj);
		}
		return true;
	}



}