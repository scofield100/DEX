package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class DishCategoriesDishCategoryWebRepository extends WebRepository<DishCategory> implements IDishCategoryRepository , IAttachRepository<DishCategory>{

	public int DishId;

	boolean distint;

	public DishCategoriesDishCategoryWebRepository(WebClientContext webContext, String baseUrl, String sessionId, int id, boolean distint){
		super(webContext, DishCategory.class, baseUrl, "DishCategoriesDishCategory");
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
	public boolean attach(DishCategory item){
		DishCategories rel = new DishCategories();
		rel.DishId = DishId;
		rel.CategoryId = item.Id;
		post(DishCategories.class, getUrl("DishCategories"), rel);
		return true;
	}

	@Override
	public DishCategory getInstance(){
		return new DishCategory();
	}

}
