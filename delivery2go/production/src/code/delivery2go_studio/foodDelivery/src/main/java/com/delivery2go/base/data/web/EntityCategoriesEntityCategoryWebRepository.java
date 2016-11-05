package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class EntityCategoriesEntityCategoryWebRepository extends WebRepository<EntityCategory> implements IEntityCategoryRepository , IAttachRepository<EntityCategory>{

	public int EntityId;

	boolean distint;

	public EntityCategoriesEntityCategoryWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, EntityCategory.class, baseUrl, "EntityCategoriesEntityCategory");
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
	public boolean attach(EntityCategory item){
		EntityCategories rel = new EntityCategories();
		rel.EntityId = EntityId;
		rel.CategoryId = item.Id;
		post(EntityCategories.class, getUrl("EntityCategories"), rel);
		return true;
	}

	@Override
	public EntityCategory getInstance(){
		return new EntityCategory();
	}

}
