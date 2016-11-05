package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DeliveryTarif;

public class DeliveryTarifFactory implements IEntityMapInitializer<DeliveryTarif> ,IFactory<DeliveryTarif>{

	protected IEntityContext context;
	public DeliveryTarifFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DeliveryTarif> map){
		map.setFactory(this);
	}

	@Override
	public DeliveryTarif getInstance(){
		//TODO: return your custum model class here 

		return new DeliveryTarif(context);
	}

}

