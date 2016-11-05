package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IImageRepository;

public class DishImagesImageWebRepository extends WebRepository<Image> implements IImageRepository , IAttachRepository<Image>{

	public int DishId;

	boolean distint;

	public DishImagesImageWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Image.class, baseUrl, "DishImagesImage");
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
	public boolean attach(Image item){
		DishImages rel = new DishImages();
		rel.DishId = DishId;
		rel.ImageId = item.Id;
		post(DishImages.class, getUrl("DishImages"), rel);
		return true;
	}

	@Override
	public Image getInstance(){
		return new Image();
	}

}
