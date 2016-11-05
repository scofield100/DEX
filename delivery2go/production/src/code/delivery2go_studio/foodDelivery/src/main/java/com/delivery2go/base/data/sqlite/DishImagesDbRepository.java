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
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IDishImagesRepository;

public class DishImagesDbRepository extends EntityMapRepository<DishImages> implements IDishImagesRepository{

	public DishImagesDbRepository(EntityMapContext context){
		super(DishImages.class, context);
	}

	@Override
	public int create(DishImages item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishImages item) {
		return super.update(item);
	}

}
