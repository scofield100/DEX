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
import com.delivery2go.base.models.OrderDishAddons;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;

public class OrderDishAddonsDbRepository extends EntityMapRepository<OrderDishAddons> implements IOrderDishAddonsRepository{

	public OrderDishAddonsDbRepository(EntityMapContext context){
		super(OrderDishAddons.class, context);
	}

	@Override
	public int create(OrderDishAddons item) {
		return super.create(item);
	}

	@Override
	public boolean update(OrderDishAddons item) {
		return super.update(item);
	}

}
