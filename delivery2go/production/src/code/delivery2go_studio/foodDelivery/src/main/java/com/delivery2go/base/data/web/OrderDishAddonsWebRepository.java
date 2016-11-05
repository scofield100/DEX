package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.OrderDishAddons;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;

public class OrderDishAddonsWebRepository extends WebRepository<OrderDishAddons> implements IOrderDishAddonsRepository{

	public OrderDishAddonsWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, OrderDishAddons.class, baseUrl, "OrderDishAddons");
		setSessionId(sessionId);
	}

	public OrderDishAddons getInstance() {
		return new OrderDishAddons();
	}

}