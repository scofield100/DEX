package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ImageWebRepository extends WebRepository<Image> implements IImageRepository{

	public ImageWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Image.class, baseUrl, "Image");
		setSessionId(sessionId);
	}

	public Image getInstance() {
		return new Image();
	}

}