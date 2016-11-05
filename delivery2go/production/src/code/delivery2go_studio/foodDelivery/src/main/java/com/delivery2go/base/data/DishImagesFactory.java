package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishImages;

public class DishImagesFactory implements IEntityMapInitializer<DishImages> ,IFactory<DishImages>{

	protected IEntityContext context;
	public DishImagesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DishImages> map){
		map.setFactory(this);
	}

	@Override
	public DishImages getInstance(){
		//TODO: return your custum model class here 

		return new DishImages(context);
	}

}

