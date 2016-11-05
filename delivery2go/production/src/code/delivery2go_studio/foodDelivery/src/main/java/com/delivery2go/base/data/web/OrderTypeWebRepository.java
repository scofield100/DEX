package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.OrderType;
import com.delivery2go.base.data.repository.IOrderTypeRepository;

public class OrderTypeWebRepository extends WebRepository<OrderType> implements IOrderTypeRepository{

	public OrderTypeWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, OrderType.class, baseUrl, "OrderType");
		setSessionId(sessionId);
	}

	public OrderType getInstance() {
		return new OrderType();
	}

}