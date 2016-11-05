package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientRepository;

public class ClientDishFavoritesClientWebRepository extends WebRepository<Client> implements IClientRepository , IAttachRepository<Client>{

	public int DishId;

	boolean distint;

	public ClientDishFavoritesClientWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Client.class, baseUrl, "ClientDishFavoritesClient");
		this.DishId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?DishId="+String.valueOf(DishId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&DishId="+String.valueOf(DishId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Client item){
		ClientDishFavorites rel = new ClientDishFavorites();
		rel.DishId = DishId;
		rel.ClientId = item.Id;
		post(ClientDishFavorites.class, getUrl("ClientDishFavorites"), rel);
		return true;
	}

	@Override
	public Client getInstance(){
		return new Client();
	}

}
