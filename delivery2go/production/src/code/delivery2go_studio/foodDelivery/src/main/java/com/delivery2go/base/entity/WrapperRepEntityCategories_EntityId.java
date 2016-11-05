package com.delivery2go.base.entity;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.EntityCategories;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;

public class WrapperRepEntityCategories_EntityId extends WrapperRepository<EntityCategories> implements IEntityCategoriesRepository , IAttachRepository<EntityCategories>{

	int id;
	String filter;
	public WrapperRepEntityCategories_EntityId(IEntityCategoriesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("EntityId = %d", id);
	}

	public WrapperRepEntityCategories_EntityId(IEntityCategoriesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("EntityId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<EntityCategories> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<EntityCategories> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(EntityCategories item){
		item.EntityId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<EntityCategories> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<EntityCategories> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(EntityCategories item){
		item.EntityId=id;
		return repository.update(item);
	}

	@Override
	public EntityCategories getInstance(){
		EntityCategories item = super.getInstance();
		item.EntityId = id;
		return item;
	}
}
