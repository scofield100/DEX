package com.delivery2go.base.user;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.User;

public class ViewModelUserList extends EntityCursorViewModel<User>{

	protected IUserRepository userRepository;

	public ViewModelUserList (IDataView view, IUserRepository userRepository) {
		super(view);
		
		this.userRepository = userRepository;   

    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               



		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<User> createCursor()
			throws InvalidOperationException {		
		return this.userRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(userRepository instanceof IAttachRepository<?>){
			IAttachRepository<User>attachedRep = (IAttachRepository<User>) userRepository;
			if(!attachedRep.attach((User) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			User obj = (User) items.get(i);
			userRepository.delete(obj);
		}
		return true;
	}



}