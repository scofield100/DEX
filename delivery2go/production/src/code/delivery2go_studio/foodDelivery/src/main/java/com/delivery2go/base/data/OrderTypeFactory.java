package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.OrderType;

public class OrderTypeFactory implements IEntityMapInitializer<OrderType> ,IFactory<OrderType>{

	protected IEntityContext context;
	public OrderTypeFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<OrderType> map){
		map.setFactory(this);
	}

	@Override
	public OrderType getInstance(){
		//TODO: return your custum model class here 

		return new OrderType(context);
	}

}

