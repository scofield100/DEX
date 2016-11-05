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
import com.delivery2go.base.models.DeliveryAgent;
import com.delivery2go.base.data.repository.IDeliveryAgentRepository;

public class DeliveryAgentDbRepository extends EntityMapRepository<DeliveryAgent> implements IDeliveryAgentRepository{

	public DeliveryAgentDbRepository(EntityMapContext context){
		super(DeliveryAgent.class, context);
	}

	@Override
	public boolean delete(DeliveryAgent item){
		context.getMap(com.delivery2go.base.models.DeliveryTarif.class).delete("DeliveryAgentId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(DeliveryAgent item) {
		return super.create(item);
	}

	@Override
	public boolean update(DeliveryAgent item) {
		return super.update(item);
	}

}
