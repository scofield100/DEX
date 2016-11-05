package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.ManyToManyMapRepository;
 import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientRepository;

public class ClientDishFavorites_ClientDbRepository extends ManyToManyMapRepository<Client,ClientDishFavorites> implements IClientRepository{


	public ClientDishFavorites_ClientDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, ClientDishFavorites.class, Client.class, id, distint);
	}

}
