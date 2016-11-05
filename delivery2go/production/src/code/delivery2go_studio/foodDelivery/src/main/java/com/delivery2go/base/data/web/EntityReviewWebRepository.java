package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityReview;
import com.delivery2go.base.data.repository.IEntityReviewRepository;

public class EntityReviewWebRepository extends WebRepository<EntityReview> implements IEntityReviewRepository{

	public EntityReviewWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, EntityReview.class, baseUrl, "EntityReview");
		setSessionId(sessionId);
	}

	public EntityReview getInstance() {
		return new EntityReview();
	}

}