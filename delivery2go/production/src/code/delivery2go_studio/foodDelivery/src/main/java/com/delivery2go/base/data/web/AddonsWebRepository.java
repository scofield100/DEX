package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;

public class AddonsWebRepository extends WebRepository<Addons> implements IAddonsRepository{

	public AddonsWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Addons.class, baseUrl, "Addons");
		setSessionId(sessionId);
	}

	public Addons getInstance() {
		return new Addons();
	}

}