package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishRepository;

public class DishCategoriesDishWebRepository extends WebRepository<Dish> implements IDishRepository , IAttachRepository<Dish>{

	public int CategoryId;

	boolean distint;

	public DishCategoriesDishWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, Dish.class, baseUrl, "DishCategoriesDish");
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
	public boolean attach(Dish item){
		DishCategories rel = new DishCategories();
		rel.CategoryId = CategoryId;
		rel.DishId = item.Id;
		post(DishCategories.class, getUrl("DishCategories"), rel);
		return true;
	}

	@Override
	public Dish getInstance(){
		return new Dish();
	}

}
