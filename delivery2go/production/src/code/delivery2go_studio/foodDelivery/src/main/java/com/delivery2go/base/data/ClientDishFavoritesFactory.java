package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.ClientDishFavorites;

public class ClientDishFavoritesFactory implements IEntityMapInitializer<ClientDishFavorites> ,IFactory<ClientDishFavorites>{

	protected IEntityContext context;
	public ClientDishFavoritesFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<ClientDishFavorites> map){
		map.setFactory(this);
	}

	@Override
	public ClientDishFavorites getInstance(){
		//TODO: return your custum model class here 

		return new ClientDishFavorites(context);
	}

}

