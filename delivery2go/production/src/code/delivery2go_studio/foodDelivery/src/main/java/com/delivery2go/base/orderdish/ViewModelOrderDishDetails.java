package com.delivery2go.base.orderdish;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.models.OrderDish;

public class ViewModelOrderDishDetails extends DataViewModel implements IClosable {

	public static final int ORDER = 1;
	public static final int DISH = 2;
	public static final int SIZE = 3;
	public static final int DRESSING = 4;
	public static final int UPDATEUSER = 5;

	protected IOrderDishRepository orderDishRepository;
	protected OrderDish _item;
	int[] _itemId;
	public OrderDish getItem(){ return this._item;}


	public Command NavigateToOrder= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ORDER, null, _item.OrderId);
			}
		}
	};

	public Command NavigateToDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(DISH, null, _item.DishId);
			}
		}
	};

	public Command NavigateToSize= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.SizeId!=null){
				getNavigator().navigateTo(SIZE, null, _item.SizeId);
			}
		}
	};

	public Command NavigateToDressing= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.DressingId!=null){
				getNavigator().navigateTo(DRESSING, null, _item.DressingId);
			}
		}
	};

	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public ViewModelOrderDishDetails(IDataView view, IOrderDishRepository orderDishRepository, int[] id){
		super(view);

		this.orderDishRepository=orderDishRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = orderDishRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.orderDishRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=orderDishRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}