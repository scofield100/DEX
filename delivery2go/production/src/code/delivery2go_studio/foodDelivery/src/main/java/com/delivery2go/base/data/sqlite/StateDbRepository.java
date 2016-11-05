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
import com.delivery2go.base.models.State;
import com.delivery2go.base.data.repository.IStateRepository;

public class StateDbRepository extends EntityMapRepository<State> implements IStateRepository{

	public StateDbRepository(EntityMapContext context){
		super(State.class, context);
	}

	@Override
	public boolean delete(State item){
		context.getMap(com.delivery2go.base.models.City.class).delete("StateId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Entity.class).delete("StateId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(State item) {
		return super.create(item);
	}

	@Override
	public boolean update(State item) {
		return super.update(item);
	}

}
