package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.State;

public class StateFactory implements IEntityMapInitializer<State> ,IFactory<State>{

	protected IEntityContext context;
	public StateFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<State> map){
		map.setFactory(this);
	}

	@Override
	public State getInstance(){
		//TODO: return your custum model class here 

		return new State(context);
	}

}

