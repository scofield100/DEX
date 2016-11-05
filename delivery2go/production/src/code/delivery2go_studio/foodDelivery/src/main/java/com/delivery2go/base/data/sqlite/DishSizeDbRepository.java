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
import com.delivery2go.base.models.DishSize;
import com.delivery2go.base.data.repository.IDishSizeRepository;

public class DishSizeDbRepository extends EntityMapRepository<DishSize> implements IDishSizeRepository{

	public DishSizeDbRepository(EntityMapContext context){
		super(DishSize.class, context);
	}

	@Override
	public boolean delete(DishSize item){
		context.getMap(com.delivery2go.base.models.DishSizePrice.class).delete("SizeId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(DishSize item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishSize item) {
		return super.update(item);
	}

}
