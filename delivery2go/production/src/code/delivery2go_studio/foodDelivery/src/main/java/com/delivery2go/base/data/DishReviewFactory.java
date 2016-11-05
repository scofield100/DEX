package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishReview;

public class DishReviewFactory implements IEntityMapInitializer<DishReview> ,IFactory<DishReview>{

	protected IEntityContext context;
	public DishReviewFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<DishReview> map){
		map.setFactory(this);
	}

	@Override
	public DishReview getInstance(){
		//TODO: return your custum model class here 

		return new DishReview(context);
	}

}

