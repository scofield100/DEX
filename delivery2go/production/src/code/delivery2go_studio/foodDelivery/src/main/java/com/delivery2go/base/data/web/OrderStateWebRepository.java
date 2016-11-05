package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.OrderState;
import com.delivery2go.base.data.repository.IOrderStateRepository;

public class OrderStateWebRepository extends WebRepository<OrderState> implements IOrderStateRepository{

	public OrderStateWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, OrderState.class, baseUrl, "OrderState");
		setSessionId(sessionId);
	}

	public OrderState getInstance() {
		return new OrderState();
	}

}