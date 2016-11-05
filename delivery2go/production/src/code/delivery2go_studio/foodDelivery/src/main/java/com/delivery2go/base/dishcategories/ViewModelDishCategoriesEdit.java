package com.delivery2go.base.dishcategories;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategories;

import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class ViewModelDishCategoriesEdit extends EditViewModel implements IClosable {

	protected IDishCategoriesRepository dishCategoriesRepository;

	protected DishCategories _item;
	protected int[] _itemId;
	public DishCategories getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	protected IDishCategoryRepository dishCategoryRepository;
	protected ArrayList<DishCategory> dishCategories;
	public List<DishCategory> getDishCategories(){ return dishCategories; }

	public ViewModelDishCategoriesEdit(IEditView view, IDishCategoriesRepository dishCategoriesRepository, int[] id){
		super(view);

		this.dishCategoriesRepository=dishCategoriesRepository;
		this._itemId=id;
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		this.dishCategoryRepository=RepositoryManager.getInstance().getIDishCategoryRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishCategoriesRepository.get(_itemId);
		}
		else{
			this._item = dishCategoriesRepository.getInstance();
		}
		this.dishs=this.dishRepository.getAll();
		this.dishCategories=this.dishCategoryRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.dishCategoriesRepository.close();
		this.dishRepository.close();
		this.dishCategoryRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=dishCategoriesRepository.update(this._item);
		}else{
			succes=dishCategoriesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}