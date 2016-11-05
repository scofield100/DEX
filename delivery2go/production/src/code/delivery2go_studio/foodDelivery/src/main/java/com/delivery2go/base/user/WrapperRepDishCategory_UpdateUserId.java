package com.delivery2go.base.user;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;

public class WrapperRepDishCategory_UpdateUserId extends WrapperRepository<DishCategory> implements IDishCategoryRepository , IAttachRepository<DishCategory>{

	int id;
	String filter;
	public WrapperRepDishCategory_UpdateUserId(IDishCategoryRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("UpdateUserId = %d", id);
	}

	public WrapperRepDishCategory_UpdateUserId(IDishCategoryRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("UpdateUserId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<DishCategory> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<DishCategory> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(DishCategory item){
		item.UpdateUserId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<DishCategory> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<DishCategory> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(DishCategory item){
		item.UpdateUserId=id;
		return repository.update(item);
	}

	@Override
	public DishCategory getInstance(){
		DishCategory item = super.getInstance();
		item.UpdateUserId = id;
		return item;
	}
}
