package com.delivery2go.base.dishcategories;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategories;

import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class ViewModelDishCategoriesList extends EntityCursorViewModel<DishCategories>{

	protected IDishCategoriesRepository dishCategoriesRepository;


	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	//protected IDishCategoryRepository dishCategoryRepository;
	//protected ArrayList<DishCategory> dishCategoriesOptional;
	//public List<DishCategory> getDishCategoriesOptional(){ return dishCategoriesOptional; }

	public ViewModelDishCategoriesList (IDataView view, IDishCategoriesRepository dishCategoriesRepository) {
		super(view);
		
		this.dishCategoriesRepository = dishCategoriesRepository;   

		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		//this.dishCategoryRepository=RepositoryManager.getInstance().getIDishCategoryRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());
		//this.dishCategoriesOptional=BaseModel.asOptionalList(DishCategory.class, dishCategoryRepository.getAll(), new DishCategory());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishCategories> createCursor()
			throws InvalidOperationException {		
		return this.dishCategoriesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishCategoriesRepository.close();
		//this.dishRepository.close();
		//this.dishCategoryRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishCategoriesRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishCategories>attachedRep = (IAttachRepository<DishCategories>) dishCategoriesRepository;
			if(!attachedRep.attach((DishCategories) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishCategories obj = (DishCategories) items.get(i);
			dishCategoriesRepository.delete(obj);
		}
		return true;
	}



}