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
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;

public class OrderDbRepository extends EntityMapRepository<Order> implements IOrderRepository{

	public OrderDbRepository(EntityMapContext context){
		super(Order.class, context);
	}

	@Override
	public boolean delete(Order item){
		context.getMap(com.delivery2go.base.models.OrderDish.class).delete("OrderId="+String.valueOf(item.OrderId));
		return super.delete(item);
	}

	@Override
	public int create(Order item) {
		return super.create(item);
	}

	@Override
	public boolean update(Order item) {
		return super.update(item);
	}

}
