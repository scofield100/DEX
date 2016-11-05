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
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class EntityCategoryDbRepository extends EntityMapRepository<EntityCategory> implements IEntityCategoryRepository{

	public EntityCategoryDbRepository(EntityMapContext context){
		super(EntityCategory.class, context);
	}

	@Override
	public boolean delete(EntityCategory item){
		context.getMap(com.delivery2go.base.models.EntityCategories.class).delete("CategoryId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(EntityCategory item) {
		return super.create(item);
	}

	@Override
	public boolean update(EntityCategory item) {
		return super.update(item);
	}

}
