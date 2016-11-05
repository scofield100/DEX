package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DeliveryAgent;
import com.delivery2go.base.data.repository.IDeliveryAgentRepository;

public class DeliveryAgentWebRepository extends WebRepository<DeliveryAgent> implements IDeliveryAgentRepository{

	public DeliveryAgentWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DeliveryAgent.class, baseUrl, "DeliveryAgent");
		setSessionId(sessionId);
	}

	public DeliveryAgent getInstance() {
		return new DeliveryAgent();
	}

}