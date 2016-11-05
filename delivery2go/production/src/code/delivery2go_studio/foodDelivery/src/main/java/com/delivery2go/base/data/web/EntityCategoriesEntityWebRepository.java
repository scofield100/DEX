package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityRepository;

public class EntityCategoriesEntityWebRepository extends WebRepository<Entity> implements IEntityRepository , IAttachRepository<Entity>{

	public int CategoryId;

	boolean distint;

	public EntityCategoriesEntityWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Entity.class, baseUrl, "EntityCategoriesEntity");
		this.CategoryId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?CategoryId="+String.valueOf(CategoryId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&CategoryId="+String.valueOf(CategoryId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Entity item){
		EntityCategories rel = new EntityCategories();
		rel.CategoryId = CategoryId;
		rel.EntityId = item.Id;
		post(EntityCategories.class, getUrl("EntityCategories"), rel);
		return true;
	}

	@Override
	public Entity getInstance(){
		return new Entity();
	}

}
