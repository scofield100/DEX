package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class DishDbRepository extends EntityMapRepository<Dish> implements IDishRepository{

	public DishDbRepository(EntityMapContext context){
		super(Dish.class, context);
	}

	@Override
	public boolean delete(Dish item){
		context.getMap(com.delivery2go.base.models.DishCategories.class).delete("DishId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishImages.class).delete("DishId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.ClientDishFavorites.class).delete("DishId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishSizePrice.class).delete("DishId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.OrderDish.class).delete("DishId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishReview.class).delete("DishId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Dish item) {
		return super.create(item);
	}

	@Override
	public boolean update(Dish item) {
		return super.update(item);
	}

}
