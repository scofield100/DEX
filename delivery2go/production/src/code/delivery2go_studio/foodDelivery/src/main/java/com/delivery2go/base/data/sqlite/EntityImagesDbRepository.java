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
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IEntityImagesRepository;

public class EntityImagesDbRepository extends EntityMapRepository<EntityImages> implements IEntityImagesRepository{

	public EntityImagesDbRepository(EntityMapContext context){
		super(EntityImages.class, context);
	}

	@Override
	public int create(EntityImages item) {
		return super.create(item);
	}

	@Override
	public boolean update(EntityImages item) {
		return super.update(item);
	}

}
