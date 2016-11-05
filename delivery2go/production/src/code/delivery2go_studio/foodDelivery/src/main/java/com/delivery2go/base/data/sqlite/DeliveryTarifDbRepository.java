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
import com.delivery2go.base.models.DeliveryTarif;
import com.delivery2go.base.data.repository.IDeliveryTarifRepository;

public class DeliveryTarifDbRepository extends EntityMapRepository<DeliveryTarif> implements IDeliveryTarifRepository{

	public DeliveryTarifDbRepository(EntityMapContext context){
		super(DeliveryTarif.class, context);
	}

	@Override
	public int create(DeliveryTarif item) {
		return super.create(item);
	}

	@Override
	public boolean update(DeliveryTarif item) {
		return super.update(item);
	}

}
