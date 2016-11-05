package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IEntityRepository;

public class UserEntitiesEntityWebRepository extends WebRepository<Entity> implements IEntityRepository , IAttachRepository<Entity>{

	public int UserId;

	boolean distint;

	public UserEntitiesEntityWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Entity.class, baseUrl, "UserEntitiesEntity");
		this.UserId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?UserId="+String.valueOf(UserId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&UserId="+String.valueOf(UserId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Entity item){
		UserEntities rel = new UserEntities();
		rel.UserId = UserId;
		rel.EntityId = item.Id;
		post(UserEntities.class, getUrl("UserEntities"), rel);
		return true;
	}

	@Override
	public Entity getInstance(){
		return new Entity();
	}

}
