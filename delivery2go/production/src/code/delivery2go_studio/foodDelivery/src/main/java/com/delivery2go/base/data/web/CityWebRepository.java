package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.City;
import com.delivery2go.base.data.repository.ICityRepository;

public class CityWebRepository extends WebRepository<City> implements ICityRepository{

	public CityWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, City.class, baseUrl, "City");
		setSessionId(sessionId);
	}

	public City getInstance() {
		return new City();
	}

}