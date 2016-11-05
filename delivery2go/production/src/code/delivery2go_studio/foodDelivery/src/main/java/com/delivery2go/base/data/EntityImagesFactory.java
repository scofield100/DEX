package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.EntityImages;

public class EntityImagesFactory implements IEntityMapInitializer<EntityImages> ,IFactory<EntityImages>{

	protected IEntityContext context;
	public EntityImagesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<EntityImages> map){
		map.setFactory(this);
	}

	@Override
	public EntityImages getInstance(){
		//TODO: return your custum model class here 

		return new EntityImages(context);
	}

}

