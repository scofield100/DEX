package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Roll;

public class RollFactory implements IEntityMapInitializer<Roll> ,IFactory<Roll>{

	protected IEntityContext context;
	public RollFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Roll> map){
		map.setFactory(this);
	}

	@Override
	public Roll getInstance(){
		//TODO: return your custum model class here 

		return new Roll(context);
	}

}

