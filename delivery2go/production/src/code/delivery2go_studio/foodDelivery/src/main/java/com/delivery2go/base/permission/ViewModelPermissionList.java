package com.delivery2go.base.permission;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Permission;

import com.delivery2go.base.models.Roll;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelPermissionList extends EntityCursorViewModel<Permission>{

	protected IPermissionRepository permissionRepository;


	//protected IRollRepository rollRepository;
	//protected ArrayList<Roll> rollsOptional;
	//public List<Roll> getRollsOptional(){ return rollsOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelPermissionList (IDataView view, IPermissionRepository permissionRepository) {
		super(view);
		
		this.permissionRepository = permissionRepository;   

		//this.rollRepository=RepositoryManager.getInstance().getIRollRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.rollsOptional=BaseModel.asOptionalList(Roll.class, rollRepository.getAll(), new Roll());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Permission> createCursor()
			throws InvalidOperationException {		
		return this.permissionRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.permissionRepository.close();
		//this.rollRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(permissionRepository instanceof IAttachRepository<?>){
			IAttachRepository<Permission>attachedRep = (IAttachRepository<Permission>) permissionRepository;
			if(!attachedRep.attach((Permission) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Permission obj = (Permission) items.get(i);
			permissionRepository.delete(obj);
		}
		return true;
	}



}