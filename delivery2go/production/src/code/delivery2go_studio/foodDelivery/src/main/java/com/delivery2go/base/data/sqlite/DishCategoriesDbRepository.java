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
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;

public class DishCategoriesDbRepository extends EntityMapRepository<DishCategories> implements IDishCategoriesRepository{

	public DishCategoriesDbRepository(EntityMapContext context){
		super(DishCategories.class, context);
	}

	@Override
	public int create(DishCategories item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishCategories item) {
		return super.update(item);
	}

}
