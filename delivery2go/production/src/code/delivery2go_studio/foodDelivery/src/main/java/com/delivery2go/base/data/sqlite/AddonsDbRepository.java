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
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;

public class AddonsDbRepository extends EntityMapRepository<Addons> implements IAddonsRepository{

	public AddonsDbRepository(EntityMapContext context){
		super(Addons.class, context);
	}

	@Override
	public boolean delete(Addons item){
		context.getMap(com.delivery2go.base.models.OrderDish.class).delete("DressingId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.OrderDishAddons.class).delete("AddonId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Addons item) {
		return super.create(item);
	}

	@Override
	public boolean update(Addons item) {
		return super.update(item);
	}

}
