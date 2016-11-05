package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IEntityImagesRepository;

public class EntityImagesWebRepository extends WebRepository<EntityImages> implements IEntityImagesRepository{

	public EntityImagesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, EntityImages.class, baseUrl, "EntityImages");
		setSessionId(sessionId);
	}

	public EntityImages getInstance() {
		return new EntityImages();
	}

}