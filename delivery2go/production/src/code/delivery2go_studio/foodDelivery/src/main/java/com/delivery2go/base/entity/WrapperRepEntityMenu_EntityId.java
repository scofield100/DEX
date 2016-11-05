package com.delivery2go.base.entity;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;

public class WrapperRepEntityMenu_EntityId extends WrapperRepository<EntityMenu> implements IEntityMenuRepository , IAttachRepository<EntityMenu>{

	int id;
	String filter;
	public WrapperRepEntityMenu_EntityId(IEntityMenuRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("EntityId = %d", id);
	}

	public WrapperRepEntityMenu_EntityId(IEntityMenuRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("EntityId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<EntityMenu> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<EntityMenu> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(EntityMenu item){
		item.EntityId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<EntityMenu> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<EntityMenu> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(EntityMenu item){
		item.EntityId=id;
		return repository.update(item);
	}

	@Override
	public EntityMenu getInstance(){
		EntityMenu item = super.getInstance();
		item.EntityId = id;
		return item;
	}
}
