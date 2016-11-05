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
 import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityRepository;

public class EntityCategories_EntityDbRepository extends ManyToManyMapRepository<Entity,EntityCategories> implements IEntityRepository{


	public EntityCategories_EntityDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, EntityCategories.class, Entity.class, id, distint);
	}

}
