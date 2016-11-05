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
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;

public class UserEntitiesDbRepository extends EntityMapRepository<UserEntities> implements IUserEntitiesRepository{

	public UserEntitiesDbRepository(EntityMapContext context){
		super(UserEntities.class, context);
	}

	@Override
	public int create(UserEntities item) {
		return super.create(item);
	}

	@Override
	public boolean update(UserEntities item) {
		return super.update(item);
	}

}
