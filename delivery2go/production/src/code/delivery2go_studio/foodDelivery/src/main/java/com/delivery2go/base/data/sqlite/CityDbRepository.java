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
import com.delivery2go.base.models.City;
import com.delivery2go.base.data.repository.ICityRepository;

public class CityDbRepository extends EntityMapRepository<City> implements ICityRepository{

	public CityDbRepository(EntityMapContext context){
		super(City.class, context);
	}

	@Override
	public boolean delete(City item){
		context.getMap(com.delivery2go.base.models.Entity.class).delete("CityId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(City item) {
		return super.create(item);
	}

	@Override
	public boolean update(City item) {
		return super.update(item);
	}

}
