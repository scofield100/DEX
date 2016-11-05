package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.ClientEntityFavorites;

public class ClientEntityFavoritesFactory implements IEntityMapInitializer<ClientEntityFavorites> ,IFactory<ClientEntityFavorites>{

	protected IEntityContext context;
	public ClientEntityFavoritesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<ClientEntityFavorites> map){
		map.setFactory(this);
	}

	@Override
	public ClientEntityFavorites getInstance(){
		//TODO: return your custum model class here 

		return new ClientEntityFavorites(context);
	}

}

