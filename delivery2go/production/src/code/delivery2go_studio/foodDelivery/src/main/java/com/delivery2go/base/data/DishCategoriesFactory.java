package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishCategories;

public class DishCategoriesFactory implements IEntityMapInitializer<DishCategories> ,IFactory<DishCategories>{

	protected IEntityContext context;
	public DishCategoriesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DishCategories> map){
		map.setFactory(this);
	}

	@Override
	public DishCategories getInstance(){
		//TODO: return your custum model class here 

		return new DishCategories(context);
	}

}

