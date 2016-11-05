package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.EntityCategories;

public class EntityCategoriesFactory implements IEntityMapInitializer<EntityCategories> ,IFactory<EntityCategories>{

	protected IEntityContext context;
	public EntityCategoriesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<EntityCategories> map){
		map.setFactory(this);
	}

	@Override
	public EntityCategories getInstance(){
		//TODO: return your custum model class here 

		return new EntityCategories(context);
	}

}

