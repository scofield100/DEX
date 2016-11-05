package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishSize;

public class DishSizeFactory implements IEntityMapInitializer<DishSize> ,IFactory<DishSize>{

	protected IEntityContext context;
	public DishSizeFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DishSize> map){
		map.setFactory(this);
	}

	@Override
	public DishSize getInstance(){
		//TODO: return your custum model class here 

		return new DishSize(context);
	}

}

