package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;

public class DishSizePriceWebRepository extends WebRepository<DishSizePrice> implements IDishSizePriceRepository{

	public DishSizePriceWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DishSizePrice.class, baseUrl, "DishSizePrice");
		setSessionId(sessionId);
	}

	public DishSizePrice getInstance() {
		return new DishSizePrice();
	}

}