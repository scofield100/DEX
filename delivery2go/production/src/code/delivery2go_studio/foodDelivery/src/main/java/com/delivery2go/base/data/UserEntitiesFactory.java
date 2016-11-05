package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.UserEntities;

public class UserEntitiesFactory implements IEntityMapInitializer<UserEntities> ,IFactory<UserEntities>{

	protected IEntityContext context;
	public UserEntitiesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<UserEntities> map){
		map.setFactory(this);
	}

	@Override
	public UserEntities getInstance(){
		//TODO: return your custum model class here 

		return new UserEntities(context);
	}

}

