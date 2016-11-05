package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishSize;
import com.delivery2go.base.data.repository.IDishSizeRepository;

public class DishSizeWebRepository extends WebRepository<DishSize> implements IDishSizeRepository{

	public DishSizeWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishSize.class, baseUrl, "DishSize");
		setSessionId(sessionId);
	}

	public DishSize getInstance() {
		return new DishSize();
	}

}