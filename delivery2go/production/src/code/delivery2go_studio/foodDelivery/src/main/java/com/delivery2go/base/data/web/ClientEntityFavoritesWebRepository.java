package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;

public class ClientEntityFavoritesWebRepository extends WebRepository<ClientEntityFavorites> implements IClientEntityFavoritesRepository{

	public ClientEntityFavoritesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, ClientEntityFavorites.class, baseUrl, "ClientEntityFavorites");
		setSessionId(sessionId);
	}

	public ClientEntityFavorites getInstance() {
		return new ClientEntityFavorites();
	}

}