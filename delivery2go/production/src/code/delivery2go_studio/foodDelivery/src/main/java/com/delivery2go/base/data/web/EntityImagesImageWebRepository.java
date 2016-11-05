package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IImageRepository;

public class EntityImagesImageWebRepository extends WebRepository<Image> implements IImageRepository , IAttachRepository<Image>{

	public int EntityId;

	boolean distint;

	public EntityImagesImageWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Image.class, baseUrl, "EntityImagesImage");
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
	public boolean attach(Image item){
		EntityImages rel = new EntityImages();
		rel.EntityId = EntityId;
		rel.ImageId = item.Id;
		post(EntityImages.class, getUrl("EntityImages"), rel);
		return true;
	}

	@Override
	public Image getInstance(){
		return new Image();
	}

}
