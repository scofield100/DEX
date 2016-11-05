package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.ManyToManyMapRepository;
 import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class EntityCategories_EntityCategoryDbRepository extends ManyToManyMapRepository<EntityCategory,EntityCategories> implements IEntityCategoryRepository{


	public EntityCategories_EntityCategoryDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, EntityCategories.class, EntityCategory.class, id, distint);
	}

}
