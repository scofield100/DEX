package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class UserWebRepository extends WebRepository<User> implements IUserRepository{

	public UserWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, User.class, baseUrl, "User");
		setSessionId(sessionId);
	}

	public User getInstance() {
		return new User();
	}

}