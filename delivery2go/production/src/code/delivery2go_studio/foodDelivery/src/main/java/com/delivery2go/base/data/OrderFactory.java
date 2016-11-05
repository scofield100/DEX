package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Order;

public class OrderFactory implements IEntityMapInitializer<Order> ,IFactory<Order>{

	protected IEntityContext context;
	public OrderFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Order> map){
		map.setFactory(this);
	}

	@Override
	public Order getInstance(){
		//TODO: return your custum model class here 

		return new Order(context);
	}

}

