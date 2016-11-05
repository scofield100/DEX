package com.delivery2go.base.dish;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;

public class WrapperRepDishReview_DishId extends WrapperRepository<DishReview> implements IDishReviewRepository , IAttachRepository<DishReview>{

	int id;
	String filter;
	public WrapperRepDishReview_DishId(IDishReviewRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("DishId = %d", id);
	}

	public WrapperRepDishReview_DishId(IDishReviewRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("DishId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<DishReview> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<DishReview> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(DishReview item){
		item.DishId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<DishReview> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<DishReview> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(DishReview item){
		item.DishId=id;
		return repository.update(item);
	}

	@Override
	public DishReview getInstance(){
		DishReview item = super.getInstance();
		item.DishId = id;
		return item;
	}
}
