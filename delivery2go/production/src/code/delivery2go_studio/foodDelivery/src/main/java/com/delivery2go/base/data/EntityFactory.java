package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Entity;

public class EntityFactory implements IEntityMapInitializer<Entity> ,IFactory<Entity>{

	protected IEntityContext context;
	public EntityFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Entity> map){
		map.setFactory(this);
	}

	@Override
	public Entity getInstance(){
		//TODO: return your custum model class here 

		return new Entity(context);
	}

}

