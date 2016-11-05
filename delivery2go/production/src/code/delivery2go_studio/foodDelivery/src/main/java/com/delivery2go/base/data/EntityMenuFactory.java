package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.EntityMenu;

public class EntityMenuFactory implements IEntityMapInitializer<EntityMenu> ,IFactory<EntityMenu>{

	protected IEntityContext context;
	public EntityMenuFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<EntityMenu> map){
		map.setFactory(this);
	}

	@Override
	public EntityMenu getInstance(){
		//TODO: return your custum model class here 

		return new EntityMenu(context);
	}

}

