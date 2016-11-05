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
import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;

public class ClientDbRepository extends EntityMapRepository<Client> implements IClientRepository{

	public ClientDbRepository(EntityMapContext context){
		super(Client.class, context);
	}

	@Override
	public boolean delete(Client item){
		context.getMap(com.delivery2go.base.models.ClientDishFavorites.class).delete("ClientId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.ClientEntityFavorites.class).delete("ClientId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Order.class).delete("ClientId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityReview.class).delete("ClientId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishReview.class).delete("ClientId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Client item) {
		return super.create(item);
	}

	@Override
	public boolean update(Client item) {
		return super.update(item);
	}

}
