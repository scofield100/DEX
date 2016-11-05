package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Addons;

public class AddonsFactory implements IEntityMapInitializer<Addons> ,IFactory<Addons>{

	protected IEntityContext context;
	public AddonsFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Addons> map){
		map.setFactory(this);
	}

	@Override
	public Addons getInstance(){
		//TODO: return your custum model class here 

		return new Addons(context);
	}

}

