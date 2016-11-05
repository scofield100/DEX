package com.delivery2go.base.data.repository;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.IRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.OrderState;

public interface IOrderStateRepository extends IRepository<OrderState>, IFactory<OrderState>{

}
