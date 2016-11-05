package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.User;
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IUserRepository;

public class UserEntitiesUserWebRepository extends WebRepository<User> implements IUserRepository , IAttachRepository<User>{

	public int EntityId;

	boolean distint;

	public UserEntitiesUserWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, User.class, baseUrl, "UserEntitiesUser");
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
	public boolean attach(User item){
		UserEntities rel = new UserEntities();
		rel.EntityId = EntityId;
		rel.UserId = item.Id;
		post(UserEntities.class, getUrl("UserEntities"), rel);
		return true;
	}

	@Override
	public User getInstance(){
		return new User();
	}

}
