package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Image;

public class ImageFactory implements IEntityMapInitializer<Image> ,IFactory<Image>{

	protected IEntityContext context;
	public ImageFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Image> map){
		map.setFactory(this);
	}

	@Override
	public Image getInstance(){
		//TODO: return your custum model class here 

		return new Image(context);
	}

}

