package com.delivery2go.base.orderdishaddons;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderDishAddons;

import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelOrderDishAddonsList extends EntityCursorViewModel<OrderDishAddons>{

	protected IOrderDishAddonsRepository orderDishAddonsRepository;


	//protected IOrderDishRepository orderDishRepository;
	//protected ArrayList<OrderDish> orderDishsOptional;
	//public List<OrderDish> getOrderDishsOptional(){ return orderDishsOptional; }

	//protected IAddonsRepository addonsRepository;
	//protected ArrayList<Addons> addonsesOptional;
	//public List<Addons> getAddonsesOptional(){ return addonsesOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderDishAddonsList (IDataView view, IOrderDishAddonsRepository orderDishAddonsRepository) {
		super(view);
		
		this.orderDishAddonsRepository = orderDishAddonsRepository;   

		//this.orderDishRepository=RepositoryManager.getInstance().getIOrderDishRepository();
		//this.addonsRepository=RepositoryManager.getInstance().getIAddonsRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.orderDishsOptional=BaseModel.asOptionalList(OrderDish.class, orderDishRepository.getAll(), new OrderDish());
		//this.addonsesOptional=BaseModel.asOptionalList(Addons.class, addonsRepository.getAll(), new Addons());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<OrderDishAddons> createCursor()
			throws InvalidOperationException {		
		return this.orderDishAddonsRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.orderDishAddonsRepository.close();
		//this.orderDishRepository.close();
		//this.addonsRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(orderDishAddonsRepository instanceof IAttachRepository<?>){
			IAttachRepository<OrderDishAddons>attachedRep = (IAttachRepository<OrderDishAddons>) orderDishAddonsRepository;
			if(!attachedRep.attach((OrderDishAddons) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			OrderDishAddons obj = (OrderDishAddons) items.get(i);
			orderDishAddonsRepository.delete(obj);
		}
		return true;
	}



}