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
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;

public class ClientDishFavoritesDbRepository extends EntityMapRepository<ClientDishFavorites> implements IClientDishFavoritesRepository{

	public ClientDishFavoritesDbRepository(EntityMapContext context){
		super(ClientDishFavorites.class, context);
	}

	@Override
	public int create(ClientDishFavorites item) {
		return super.create(item);
	}

	@Override
	public boolean update(ClientDishFavorites item) {
		return super.update(item);
	}

}
