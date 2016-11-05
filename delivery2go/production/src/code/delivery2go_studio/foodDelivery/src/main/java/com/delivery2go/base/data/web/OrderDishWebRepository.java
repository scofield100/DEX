package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;

public class OrderDishWebRepository extends WebRepository<OrderDish> implements IOrderDishRepository{

	public OrderDishWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, OrderDish.class, baseUrl, "OrderDish");
		setSessionId(sessionId);
	}

	public OrderDish getInstance() {
		return new OrderDish();
	}

}