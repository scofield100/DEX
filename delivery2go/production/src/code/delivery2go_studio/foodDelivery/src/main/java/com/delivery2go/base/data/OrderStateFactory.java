package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.OrderState;

public class OrderStateFactory implements IEntityMapInitializer<OrderState> ,IFactory<OrderState>{

	protected IEntityContext context;
	public OrderStateFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<OrderState> map){
		map.setFactory(this);
	}

	@Override
	public OrderState getInstance(){
		//TODO: return your custum model class here 

		return new OrderState(context);
	}

}

