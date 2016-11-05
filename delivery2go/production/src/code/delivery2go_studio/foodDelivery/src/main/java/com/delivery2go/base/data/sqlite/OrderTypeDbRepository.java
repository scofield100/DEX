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
import com.delivery2go.base.models.OrderType;
import com.delivery2go.base.data.repository.IOrderTypeRepository;

public class OrderTypeDbRepository extends EntityMapRepository<OrderType> implements IOrderTypeRepository{

	public OrderTypeDbRepository(EntityMapContext context){
		super(OrderType.class, context);
	}

	@Override
	public boolean delete(OrderType item){
		context.getMap(com.delivery2go.base.models.Order.class).delete("OrderTypeId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(OrderType item) {
		return super.create(item);
	}

	@Override
	public boolean update(OrderType item) {
		return super.update(item);
	}

}
