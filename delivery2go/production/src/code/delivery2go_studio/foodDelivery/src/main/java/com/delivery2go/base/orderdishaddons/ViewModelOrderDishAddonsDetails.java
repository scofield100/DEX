package com.delivery2go.base.orderdishaddons;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.models.OrderDishAddons;

public class ViewModelOrderDishAddonsDetails extends DataViewModel implements IClosable {

	public static final int ORDERDISH = 1;
	public static final int ADDON = 2;
	public static final int UPDATEUSER = 3;

	protected IOrderDishAddonsRepository orderDishAddonsRepository;
	protected OrderDishAddons _item;
	int[] _itemId;
	public OrderDishAddons getItem(){ return this._item;}


	public Command NavigateToOrderDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ORDERDISH, null, _item.OrderDishId);
			}
		}
	};

	public Command NavigateToAddon= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ADDON, null, _item.AddonId);
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

	public ViewModelOrderDishAddonsDetails(IDataView view, IOrderDishAddonsRepository orderDishAddonsRepository, int[] id){
		super(view);

		this.orderDishAddonsRepository=orderDishAddonsRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = orderDishAddonsRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.orderDishAddonsRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=orderDishAddonsRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}