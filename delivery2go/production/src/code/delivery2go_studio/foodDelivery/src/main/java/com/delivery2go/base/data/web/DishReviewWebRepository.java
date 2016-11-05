package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;

public class DishReviewWebRepository extends WebRepository<DishReview> implements IDishReviewRepository{

	public DishReviewWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishReview.class, baseUrl, "DishReview");
		setSessionId(sessionId);
	}

	public DishReview getInstance() {
		return new DishReview();
	}

}