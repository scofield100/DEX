package com.delivery2go.base.dishcategories;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.models.DishCategories;

public class ViewModelDishCategoriesDetails extends DataViewModel implements IClosable {

	public static final int DISH = 1;
	public static final int CATEGORY = 2;

	protected IDishCategoriesRepository dishCategoriesRepository;
	protected DishCategories _item;
	int[] _itemId;
	public DishCategories getItem(){ return this._item;}


	public Command NavigateToDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(DISH, null, _item.DishId);
			}
		}
	};

	public Command NavigateToCategory= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CATEGORY, null, _item.CategoryId);
			}
		}
	};

	public ViewModelDishCategoriesDetails(IDataView view, IDishCategoriesRepository dishCategoriesRepository, int[] id){
		super(view);

		this.dishCategoriesRepository=dishCategoriesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishCategoriesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishCategoriesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishCategoriesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}