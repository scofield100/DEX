package com.delivery2go.base.state;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class WrapperRepEntity_StateId extends WrapperRepository<Entity> implements IEntityRepository , IAttachRepository<Entity>{

	int id;
	String filter;
	public WrapperRepEntity_StateId(IEntityRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("StateId = %d", id);
	}

	public WrapperRepEntity_StateId(IEntityRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("StateId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<Entity> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<Entity> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(Entity item){
		item.StateId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<Entity> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<Entity> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(Entity item){
		item.StateId=id;
		return repository.update(item);
	}

	@Override
	public Entity getInstance(){
		Entity item = super.getInstance();
		item.StateId = id;
		return item;
	}
}
