package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class DishWebRepository extends WebRepository<Dish> implements IDishRepository{

	public DishWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Dish.class, baseUrl, "Dish");
		setSessionId(sessionId);
	}

	public Dish getInstance() {
		return new Dish();
	}

}