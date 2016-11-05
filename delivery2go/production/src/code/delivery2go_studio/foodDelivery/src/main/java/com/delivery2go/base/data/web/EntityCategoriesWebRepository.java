package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;

public class EntityCategoriesWebRepository extends WebRepository<EntityCategories> implements IEntityCategoriesRepository{

	public EntityCategoriesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, EntityCategories.class, baseUrl, "EntityCategories");
		setSessionId(sessionId);
	}

	public EntityCategories getInstance() {
		return new EntityCategories();
	}

}