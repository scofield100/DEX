package com.delivery2go.base.data.repository;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.IRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.DishSize;

public interface IDishSizeRepository extends IRepository<DishSize>, IFactory<DishSize>{

}
