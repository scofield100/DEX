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
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;

public class DishSizePriceDbRepository extends EntityMapRepository<DishSizePrice> implements IDishSizePriceRepository{

	public DishSizePriceDbRepository(EntityMapContext context){
		super(DishSizePrice.class, context);
	}

	@Override
	public boolean delete(DishSizePrice item){
		context.getMap(com.delivery2go.base.models.OrderDish.class).delete("SizeId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(DishSizePrice item) {
		return super.create(item);
	}

	@Override
	public boolean update(DishSizePrice item) {
		return super.update(item);
	}

}
