package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IDishImagesRepository;

public class DishImagesWebRepository extends WebRepository<DishImages> implements IDishImagesRepository{

	public DishImagesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishImages.class, baseUrl, "DishImages");
		setSessionId(sessionId);
	}

	public DishImages getInstance() {
		return new DishImages();
	}

}