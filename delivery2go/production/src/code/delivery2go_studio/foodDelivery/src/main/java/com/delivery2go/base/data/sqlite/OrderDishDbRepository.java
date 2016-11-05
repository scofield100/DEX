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
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;

public class OrderDishDbRepository extends EntityMapRepository<OrderDish> implements IOrderDishRepository{

	public OrderDishDbRepository(EntityMapContext context){
		super(OrderDish.class, context);
	}

	@Override
	public boolean delete(OrderDish item){
		context.getMap(com.delivery2go.base.models.OrderDishAddons.class).delete("OrderDishId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(OrderDish item) {
		return super.create(item);
	}

	@Override
	public boolean update(OrderDish item) {
		return super.update(item);
	}

}
