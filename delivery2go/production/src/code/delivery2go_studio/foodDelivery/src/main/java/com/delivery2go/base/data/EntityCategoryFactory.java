package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.EntityCategory;

public class EntityCategoryFactory implements IEntityMapInitializer<EntityCategory> ,IFactory<EntityCategory>{

	protected IEntityContext context;
	public EntityCategoryFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<EntityCategory> map){
		map.setFactory(this);
	}

	@Override
	public EntityCategory getInstance(){
		//TODO: return your custum model class here 

		return new EntityCategory(context);
	}

}

