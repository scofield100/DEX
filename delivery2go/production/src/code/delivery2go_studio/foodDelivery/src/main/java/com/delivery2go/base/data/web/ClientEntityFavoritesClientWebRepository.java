package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IClientRepository;

public class ClientEntityFavoritesClientWebRepository extends WebRepository<Client> implements IClientRepository , IAttachRepository<Client>{

	public int EntityId;

	boolean distint;

	public ClientEntityFavoritesClientWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Client.class, baseUrl, "ClientEntityFavoritesClient");
		this.EntityId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?EntityId="+String.valueOf(EntityId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&EntityId="+String.valueOf(EntityId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Client item){
		ClientEntityFavorites rel = new ClientEntityFavorites();
		rel.EntityId = EntityId;
		rel.ClientId = item.Id;
		post(ClientEntityFavorites.class, getUrl("ClientEntityFavorites"), rel);
		return true;
	}

	@Override
	public Client getInstance(){
		return new Client();
	}

}
