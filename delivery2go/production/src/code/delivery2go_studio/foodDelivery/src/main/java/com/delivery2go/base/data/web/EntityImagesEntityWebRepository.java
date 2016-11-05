package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IEntityRepository;

public class EntityImagesEntityWebRepository extends WebRepository<Entity> implements IEntityRepository , IAttachRepository<Entity>{

	public int ImageId;

	boolean distint;

	public EntityImagesEntityWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Entity.class, baseUrl, "EntityImagesEntity");
		this.ImageId = id;
		this.distint = distint;
		setSessionId(sessionId);
	}

	@Override
	protected String getRequestUrl(String segments, String query){
		if(query == null){
			query = "?ImageId="+String.valueOf(ImageId) + "&distint="+String.valueOf(distint);
		}else{
			query+="&ImageId="+String.valueOf(ImageId);
			query+="&distint="+String.valueOf(distint);
		}
		return super.getRequestUrl(segments, query);
	}

	@Override
	public boolean attach(Entity item){
		EntityImages rel = new EntityImages();
		rel.ImageId = ImageId;
		rel.EntityId = item.Id;
		post(EntityImages.class, getUrl("EntityImages"), rel);
		return true;
	}

	@Override
	public Entity getInstance(){
		return new Entity();
	}

}
