package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;

public class ClientWebRepository extends WebRepository<Client> implements IClientRepository{

	public ClientWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Client.class, baseUrl, "Client");
		setSessionId(sessionId);
	}

	public Client getInstance() {
		return new Client();
	}

}