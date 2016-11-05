package com.delivery2go.base.orderdish;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.OrderDish;

import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelOrderDishList extends EntityCursorViewModel<OrderDish>{

	protected IOrderDishRepository orderDishRepository;


	//protected IOrderRepository orderRepository;
	//protected ArrayList<Order> ordersOptional;
	//public List<Order> getOrdersOptional(){ return ordersOptional; }

	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	//protected IDishSizePriceRepository dishSizePriceRepository;
	//protected ArrayList<DishSizePrice> dishSizePricesOptional;
	//public List<DishSizePrice> getDishSizePricesOptional(){ return dishSizePricesOptional; }

	//protected IAddonsRepository addonsRepository;
	//protected ArrayList<Addons> addonsesOptional;
	//public List<Addons> getAddonsesOptional(){ return addonsesOptional; }

	//protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelOrderDishList (IDataView view, IOrderDishRepository orderDishRepository) {
		super(view);
		
		this.orderDishRepository = orderDishRepository;   

		//this.orderRepository=RepositoryManager.getInstance().getIOrderRepository();
		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		//this.dishSizePriceRepository=RepositoryManager.getInstance().getIDishSizePriceRepository();
		//this.addonsRepository=RepositoryManager.getInstance().getIAddonsRepository();
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.ordersOptional=BaseModel.asOptionalList(Order.class, orderRepository.getAll(), new Order());
		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());
		//this.dishSizePricesOptional=BaseModel.asOptionalList(DishSizePrice.class, dishSizePriceRepository.getAll(), new DishSizePrice());
		//this.addonsesOptional=BaseModel.asOptionalList(Addons.class, addonsRepository.getAll(), new Addons());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<OrderDish> createCursor()
			throws InvalidOperationException {		
		return this.orderDishRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.orderDishRepository.close();
		//this.orderRepository.close();
		//this.dishRepository.close();
		//this.dishSizePriceRepository.close();
		//this.addonsRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(orderDishRepository instanceof IAttachRepository<?>){
			IAttachRepository<OrderDish>attachedRep = (IAttachRepository<OrderDish>) orderDishRepository;
			if(!attachedRep.attach((OrderDish) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			OrderDish obj = (OrderDish) items.get(i);
			orderDishRepository.delete(obj);
		}
		return true;
	}



}