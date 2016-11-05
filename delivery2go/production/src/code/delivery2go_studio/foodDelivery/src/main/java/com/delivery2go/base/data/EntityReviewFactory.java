package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.EntityReview;

public class EntityReviewFactory implements IEntityMapInitializer<EntityReview> ,IFactory<EntityReview>{

	protected IEntityContext context;
	public EntityReviewFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<EntityReview> map){
		map.setFactory(this);
	}

	@Override
	public EntityReview getInstance(){
		//TODO: return your custum model class here 

		return new EntityReview(context);
	}

}

