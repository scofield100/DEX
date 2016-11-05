package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ClientEntityFavoritesEntityWebRepository extends WebRepository<Entity> implements IEntityRepository , IAttachRepository<Entity>{

	public int ClientId;

	boolean distint;

	public ClientEntityFavoritesEntityWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Entity.class, baseUrl, "ClientEntityFavoritesEntity");
		this.ClientId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?ClientId="+String.valueOf(ClientId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&ClientId="+String.valueOf(ClientId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Entity item){
		ClientEntityFavorites rel = new ClientEntityFavorites();
		rel.ClientId = ClientId;
		rel.EntityId = item.Id;
		post(ClientEntityFavorites.class, getUrl("ClientEntityFavorites"), rel);
		return true;
	}

	@Override
	public Entity getInstance(){
		return new Entity();
	}

}
