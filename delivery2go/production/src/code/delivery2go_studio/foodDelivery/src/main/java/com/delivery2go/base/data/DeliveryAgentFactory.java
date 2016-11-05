package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DeliveryAgent;

public class DeliveryAgentFactory implements IEntityMapInitializer<DeliveryAgent> ,IFactory<DeliveryAgent>{

	protected IEntityContext context;
	public DeliveryAgentFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DeliveryAgent> map){
		map.setFactory(this);
	}

	@Override
	public DeliveryAgent getInstance(){
		//TODO: return your custum model class here 

		return new DeliveryAgent(context);
	}

}

