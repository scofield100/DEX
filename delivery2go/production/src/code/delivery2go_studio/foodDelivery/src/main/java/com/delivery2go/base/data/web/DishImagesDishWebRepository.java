package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IDishRepository;

public class DishImagesDishWebRepository extends WebRepository<Dish> implements IDishRepository , IAttachRepository<Dish>{

	public int ImageId;

	boolean distint;

	public DishImagesDishWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Dish.class, baseUrl, "DishImagesDish");
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
	public boolean attach(Dish item){
		DishImages rel = new DishImages();
		rel.ImageId = ImageId;
		rel.DishId = item.Id;
		post(DishImages.class, getUrl("DishImages"), rel);
		return true;
	}

	@Override
	public Dish getInstance(){
		return new Dish();
	}

}
