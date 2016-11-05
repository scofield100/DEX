package com.delivery2go.base.dishsizeprice;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishSizePrice;

import com.delivery2go.base.models.DishSize;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishSizePriceList extends EntityCursorViewModel<DishSizePrice>{

	protected IDishSizePriceRepository dishSizePriceRepository;


	//protected IDishSizeRepository dishSizeRepository;
	//protected ArrayList<DishSize> dishSizesOptional;
	//public List<DishSize> getDishSizesOptional(){ return dishSizesOptional; }

	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelDishSizePriceList (IDataView view, IDishSizePriceRepository dishSizePriceRepository) {
		super(view);
		
		this.dishSizePriceRepository = dishSizePriceRepository;   

		//this.dishSizeRepository=RepositoryManager.getInstance().getIDishSizeRepository();
		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.dishSizesOptional=BaseModel.asOptionalList(DishSize.class, dishSizeRepository.getAll(), new DishSize());
		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishSizePrice> createCursor()
			throws InvalidOperationException {		
		return this.dishSizePriceRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishSizePriceRepository.close();
		//this.dishSizeRepository.close();
		//this.dishRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishSizePriceRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishSizePrice>attachedRep = (IAttachRepository<DishSizePrice>) dishSizePriceRepository;
			if(!attachedRep.attach((DishSizePrice) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishSizePrice obj = (DishSizePrice) items.get(i);
			dishSizePriceRepository.delete(obj);
		}
		return true;
	}



}