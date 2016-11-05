package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;

public class OrderWebRepository extends WebRepository<Order> implements IOrderRepository{

	public OrderWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Order.class, baseUrl, "Order");
		setSessionId(sessionId);
	}

	public Order getInstance() {
		return new Order();
	}

}