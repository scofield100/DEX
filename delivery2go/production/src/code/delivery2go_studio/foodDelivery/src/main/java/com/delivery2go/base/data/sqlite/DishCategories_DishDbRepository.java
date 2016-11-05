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
 import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishRepository;

public class DishCategories_DishDbRepository extends ManyToManyMapRepository<Dish,DishCategories> implements IDishRepository{


	public DishCategories_DishDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, DishCategories.class, Dish.class, id, distint);
	}

}
