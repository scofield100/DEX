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
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;

public class EntityMenuDbRepository extends EntityMapRepository<EntityMenu> implements IEntityMenuRepository{

	public EntityMenuDbRepository(EntityMapContext context){
		super(EntityMenu.class, context);
	}

	@Override
	public boolean delete(EntityMenu item){
		context.getMap(com.delivery2go.base.models.Dish.class).delete("MenuId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(EntityMenu item) {
		return super.create(item);
	}

	@Override
	public boolean update(EntityMenu item) {
		return super.update(item);
	}

}
