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
 import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ClientEntityFavorites_EntityDbRepository extends ManyToManyMapRepository<Entity,ClientEntityFavorites> implements IEntityRepository{


	public ClientEntityFavorites_EntityDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, ClientEntityFavorites.class, Entity.class, id, distint);
	}

}
