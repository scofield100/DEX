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
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;

public class EntityCategoriesDbRepository extends EntityMapRepository<EntityCategories> implements IEntityCategoriesRepository{

	public EntityCategoriesDbRepository(EntityMapContext context){
		super(EntityCategories.class, context);
	}

	@Override
	public int create(EntityCategories item) {
		return super.create(item);
	}

	@Override
	public boolean update(EntityCategories item) {
		return super.update(item);
	}

}
