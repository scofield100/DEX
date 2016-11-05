package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Roll;
import com.delivery2go.base.data.repository.IRollRepository;

public class RollWebRepository extends WebRepository<Roll> implements IRollRepository{

	public RollWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Roll.class, baseUrl, "Roll");
		setSessionId(sessionId);
	}

	public Roll getInstance() {
		return new Roll();
	}

}