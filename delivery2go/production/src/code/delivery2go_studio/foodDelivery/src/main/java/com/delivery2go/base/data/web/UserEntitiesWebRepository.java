package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;

public class UserEntitiesWebRepository extends WebRepository<UserEntities> implements IUserEntitiesRepository{

	public UserEntitiesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, UserEntities.class, baseUrl, "UserEntities");
		setSessionId(sessionId);
	}

	public UserEntities getInstance() {
		return new UserEntities();
	}

}