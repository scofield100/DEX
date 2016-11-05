package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class DishCategoryDbRepository extends EntityMapRepository<DishCategory> implements IDishCategoryRepository{

	public DishCategoryDbRepository(EntityMapContext context){
		super(DishCategory.class, context);
	}

	@Override
	public boolean delete(DishCategory item){
		context.getMap(com.delivery2go.base.models.DishCategories.class).delete("CategoryId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(DishCategory item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishCategory item) {
		return super.update(item);
	}

}
