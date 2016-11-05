package com.delivery2go.base.data.repository;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.IRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DeliveryTarif;

public interface IDeliveryTarifRepository extends IRepository<DeliveryTarif>, IFactory<DeliveryTarif>{

}
