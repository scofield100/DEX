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
import com.delivery2go.base.models.Roll;
import com.delivery2go.base.data.repository.IRollRepository;

public class RollDbRepository extends EntityMapRepository<Roll> implements IRollRepository{

	public RollDbRepository(EntityMapContext context){
		super(Roll.class, context);
	}

	@Override
	public boolean delete(Roll item){
		context.getMap(com.delivery2go.base.models.Permission.class).delete("RollId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Roll item) {
		return super.create(item);
	}

	@Override
	public boolean update(Roll item) {
		return super.update(item);
	}

}
