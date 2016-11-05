package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.City;

public class CityFactory implements IEntityMapInitializer<City> ,IFactory<City>{

	protected IEntityContext context;
	public CityFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<City> map){
		map.setFactory(this);
	}

	@Override
	public City getInstance(){
		//TODO: return your custum model class here 

		return new City(context);
	}

}

