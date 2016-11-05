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
import com.delivery2go.base.models.Permission;
import com.delivery2go.base.data.repository.IPermissionRepository;

public class PermissionDbRepository extends EntityMapRepository<Permission> implements IPermissionRepository{

	public PermissionDbRepository(EntityMapContext context){
		super(Permission.class, context);
	}

	@Override
	public int create(Permission item) {
		return super.create(item);
	}

	@Override
	public boolean update(Permission item) {
		return super.update(item);
	}

}
