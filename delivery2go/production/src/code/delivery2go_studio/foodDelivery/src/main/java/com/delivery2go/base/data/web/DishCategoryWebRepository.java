package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class DishCategoryWebRepository extends WebRepository<DishCategory> implements IDishCategoryRepository{

	public DishCategoryWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishCategory.class, baseUrl, "DishCategory");
		setSessionId(sessionId);
	}

	public DishCategory getInstance() {
		return new DishCategory();
	}

}