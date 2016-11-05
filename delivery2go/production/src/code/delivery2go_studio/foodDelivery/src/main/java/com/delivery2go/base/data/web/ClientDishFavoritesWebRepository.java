package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;

public class ClientDishFavoritesWebRepository extends WebRepository<ClientDishFavorites> implements IClientDishFavoritesRepository{

	public ClientDishFavoritesWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, ClientDishFavorites.class, baseUrl, "ClientDishFavorites");
		setSessionId(sessionId);
	}

	public ClientDishFavorites getInstance() {
		return new ClientDishFavorites();
	}

}