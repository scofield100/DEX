package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishSizePrice;

public class DishSizePriceFactory implements IEntityMapInitializer<DishSizePrice> ,IFactory<DishSizePrice>{

	protected IEntityContext context;
	public DishSizePriceFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DishSizePrice> map){
		map.setFactory(this);
	}

	@Override
	public DishSizePrice getInstance(){
		//TODO: return your custum model class here 

		return new DishSizePrice(context);
	}

}

