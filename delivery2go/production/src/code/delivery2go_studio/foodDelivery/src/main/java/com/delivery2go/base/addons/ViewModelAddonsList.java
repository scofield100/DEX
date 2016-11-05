package com.delivery2go.base.addons;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Addons;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelAddonsList extends EntityCursorViewModel<Addons>{

	protected IAddonsRepository addonsRepository;


	//protected IEntityRepository entityRepository;
	//protected ArrayList<Entity> entitiesOptional;
	//public List<Entity> getEntitiesOptional(){ return entitiesOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelAddonsList (IDataView view, IAddonsRepository addonsRepository) {
		super(view);
		
		this.addonsRepository = addonsRepository;   

		//this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.entitiesOptional=BaseModel.asOptionalList(Entity.class, entityRepository.getAll(), new Entity());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Addons> createCursor()
			throws InvalidOperationException {		
		return this.addonsRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.addonsRepository.close();
		//this.entityRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(addonsRepository instanceof IAttachRepository<?>){
			IAttachRepository<Addons>attachedRep = (IAttachRepository<Addons>) addonsRepository;
			if(!attachedRep.attach((Addons) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Addons obj = (Addons) items.get(i);
			addonsRepository.delete(obj);
		}
		return true;
	}



}