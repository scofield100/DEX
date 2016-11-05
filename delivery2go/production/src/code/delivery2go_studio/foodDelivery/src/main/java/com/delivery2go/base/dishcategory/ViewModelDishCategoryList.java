package com.delivery2go.base.dishcategory;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategory;

import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishCategoryList extends EntityCursorViewModel<DishCategory>{

	protected IDishCategoryRepository dishCategoryRepository;


	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelDishCategoryList (IDataView view, IDishCategoryRepository dishCategoryRepository) {
		super(view);
		
		this.dishCategoryRepository = dishCategoryRepository;   

		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishCategory> createCursor()
			throws InvalidOperationException {		
		return this.dishCategoryRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishCategoryRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishCategoryRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishCategory>attachedRep = (IAttachRepository<DishCategory>) dishCategoryRepository;
			if(!attachedRep.attach((DishCategory) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishCategory obj = (DishCategory) items.get(i);
			dishCategoryRepository.delete(obj);
		}
		return true;
	}



}