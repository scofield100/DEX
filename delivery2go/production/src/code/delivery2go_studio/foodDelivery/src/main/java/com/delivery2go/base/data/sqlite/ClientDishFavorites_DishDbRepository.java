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
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IDishRepository;

public class ClientDishFavorites_DishDbRepository extends ManyToManyMapRepository<Dish,ClientDishFavorites> implements IDishRepository{


	public ClientDishFavorites_DishDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, ClientDishFavorites.class, Dish.class, id, distint);
	}

}
