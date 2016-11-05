package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class EntityCategoryWebRepository extends WebRepository<EntityCategory> implements IEntityCategoryRepository{

	public EntityCategoryWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, EntityCategory.class, baseUrl, "EntityCategory");
		setSessionId(sessionId);
	}

	public EntityCategory getInstance() {
		return new EntityCategory();
	}

}