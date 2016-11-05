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
import com.delivery2go.base.models.EntityReview;
import com.delivery2go.base.data.repository.IEntityReviewRepository;

public class EntityReviewDbRepository extends EntityMapRepository<EntityReview> implements IEntityReviewRepository{

	public EntityReviewDbRepository(EntityMapContext context){
		super(EntityReview.class, context);
	}

	@Override
	public int create(EntityReview item) {
		return super.create(item);
	}

	@Override
	public boolean update(EntityReview item) {
		return super.update(item);
	}

}
