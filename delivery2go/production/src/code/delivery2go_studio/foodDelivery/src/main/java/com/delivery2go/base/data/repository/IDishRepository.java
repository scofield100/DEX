package com.delivery2go.base.data.repository;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.IRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Dish;

public interface IDishRepository extends IRepository<Dish>, IFactory<Dish>{

}
