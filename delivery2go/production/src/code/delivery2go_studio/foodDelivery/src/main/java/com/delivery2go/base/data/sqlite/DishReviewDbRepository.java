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
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;

public class DishReviewDbRepository extends EntityMapRepository<DishReview> implements IDishReviewRepository{

	public DishReviewDbRepository(EntityMapContext context){
		super(DishReview.class, context);
	}

	@Override
	public int create(DishReview item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishReview item) {
		return super.update(item);
	}

}
