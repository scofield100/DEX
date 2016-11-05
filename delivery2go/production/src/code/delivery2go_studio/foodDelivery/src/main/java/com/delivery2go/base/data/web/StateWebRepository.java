package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.State;
import com.delivery2go.base.data.repository.IStateRepository;

public class StateWebRepository extends WebRepository<State> implements IStateRepository{

	public StateWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, State.class, baseUrl, "State");
		setSessionId(sessionId);
	}

	public State getInstance() {
		return new State();
	}

}