package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.OrderDish;

public class OrderDishFactory implements IEntityMapInitializer<OrderDish> ,IFactory<OrderDish>{

	protected IEntityContext context;
	public OrderDishFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<OrderDish> map){
		map.setFactory(this);
	}

	@Override
	public OrderDish getInstance(){
		//TODO: return your custum model class here 

		return new OrderDish(context);
	}

}

