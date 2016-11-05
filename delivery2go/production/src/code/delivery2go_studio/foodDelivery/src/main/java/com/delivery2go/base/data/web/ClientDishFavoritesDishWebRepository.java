package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IDishRepository;

public class ClientDishFavoritesDishWebRepository extends WebRepository<Dish> implements IDishRepository , IAttachRepository<Dish>{

	public int ClientId;

	boolean distint;

	public ClientDishFavoritesDishWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Dish.class, baseUrl, "ClientDishFavoritesDish");
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
	public boolean attach(Dish item){
		ClientDishFavorites rel = new ClientDishFavorites();
		rel.ClientId = ClientId;
		rel.DishId = item.Id;
		post(ClientDishFavorites.class, getUrl("ClientDishFavorites"), rel);
		return true;
	}

	@Override
	public Dish getInstance(){
		return new Dish();
	}

}
