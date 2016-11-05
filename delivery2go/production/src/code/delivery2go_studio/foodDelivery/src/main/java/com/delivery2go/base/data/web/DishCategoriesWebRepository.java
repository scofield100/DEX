package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;

public class DishCategoriesWebRepository extends WebRepository<DishCategories> implements IDishCategoriesRepository{

	public DishCategoriesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishCategories.class, baseUrl, "DishCategories");
		setSessionId(sessionId);
	}

	public DishCategories getInstance() {
		return new DishCategories();
	}

}