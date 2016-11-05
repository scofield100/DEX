package com.delivery2go.base.dishsizeprice;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.models.DishSizePrice;

public class ViewModelDishSizePriceDetails extends DataViewModel implements IClosable {

	public static final int SIZE = 1;
	public static final int DISH = 2;
	public static final int UPDATEUSER = 3;

	protected IDishSizePriceRepository dishSizePriceRepository;
	protected DishSizePrice _item;
	int[] _itemId;
	public DishSizePrice getItem(){ return this._item;}


	public Command NavigateToSize= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(SIZE, null, _item.SizeId);
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

	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public ViewModelDishSizePriceDetails(IDataView view, IDishSizePriceRepository dishSizePriceRepository, int[] id){
		super(view);

		this.dishSizePriceRepository=dishSizePriceRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishSizePriceRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishSizePriceRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishSizePriceRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}