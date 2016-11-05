package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Dish;

public class DishFactory implements IEntityMapInitializer<Dish> ,IFactory<Dish>{

	protected IEntityContext context;
	public DishFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Dish> map){
		map.setFactory(this);
	}

	@Override
	public Dish getInstance(){
		//TODO: return your custum model class here 

		return new Dish(context);
	}

}

