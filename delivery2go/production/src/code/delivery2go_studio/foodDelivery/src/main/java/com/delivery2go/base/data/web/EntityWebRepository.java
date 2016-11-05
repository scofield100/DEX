package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class EntityWebRepository extends WebRepository<Entity> implements IEntityRepository{

	public EntityWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Entity.class, baseUrl, "Entity");
		setSessionId(sessionId);
	}

	public Entity getInstance() {
		return new Entity();
	}

}