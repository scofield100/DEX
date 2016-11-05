package com.delivery2go.base.dishcategory;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.models.DishCategory;

public class ViewModelDishCategoryDetails extends DataViewModel implements IClosable {

	public static final int UPDATEUSER = 1;

	protected IDishCategoryRepository dishCategoryRepository;
	protected DishCategory _item;
	int[] _itemId;
	public DishCategory getItem(){ return this._item;}


	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public ViewModelDishCategoryDetails(IDataView view, IDishCategoryRepository dishCategoryRepository, int[] id){
		super(view);

		this.dishCategoryRepository=dishCategoryRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishCategoryRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishCategoryRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishCategoryRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}