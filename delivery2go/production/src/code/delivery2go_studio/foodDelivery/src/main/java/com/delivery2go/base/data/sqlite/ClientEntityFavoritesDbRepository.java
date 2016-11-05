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
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;

public class ClientEntityFavoritesDbRepository extends EntityMapRepository<ClientEntityFavorites> implements IClientEntityFavoritesRepository{

	public ClientEntityFavoritesDbRepository(EntityMapContext context){
		super(ClientEntityFavorites.class, context);
	}

	@Override
	public int create(ClientEntityFavorites item) {
		return super.create(item);
	}

	@Override
	public boolean update(ClientEntityFavorites item) {
		return super.update(item);
	}

}
