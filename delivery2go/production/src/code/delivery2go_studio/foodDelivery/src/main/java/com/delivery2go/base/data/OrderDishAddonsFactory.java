package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.OrderDishAddons;

public class OrderDishAddonsFactory implements IEntityMapInitializer<OrderDishAddons> ,IFactory<OrderDishAddons>{

	protected IEntityContext context;
	public OrderDishAddonsFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<OrderDishAddons> map){
		map.setFactory(this);
	}

	@Override
	public OrderDishAddons getInstance(){
		//TODO: return your custum model class here 

		return new OrderDishAddons(context);
	}

}

