package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;

public class EntityMenuWebRepository extends WebRepository<EntityMenu> implements IEntityMenuRepository{

	public EntityMenuWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, EntityMenu.class, baseUrl, "EntityMenu");
		setSessionId(sessionId);
	}

	public EntityMenu getInstance() {
		return new EntityMenu();
	}

}