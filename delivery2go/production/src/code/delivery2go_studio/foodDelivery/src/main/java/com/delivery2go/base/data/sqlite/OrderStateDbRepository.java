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
import com.delivery2go.base.models.OrderState;
import com.delivery2go.base.data.repository.IOrderStateRepository;

public class OrderStateDbRepository extends EntityMapRepository<OrderState> implements IOrderStateRepository{

	public OrderStateDbRepository(EntityMapContext context){
		super(OrderState.class, context);
	}

	@Override
	public boolean delete(OrderState item){
		context.getMap(com.delivery2go.base.models.Order.class).delete("OrderStateId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(OrderState item) {
		return super.create(item);
	}

	@Override
	public boolean update(OrderState item) {
		return super.update(item);
	}

}
