package com.delivery2go.base.entity;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class WrapperRepDish_EntityId extends WrapperRepository<Dish> implements IDishRepository , IAttachRepository<Dish>{

	int id;
	String filter;
	public WrapperRepDish_EntityId(IDishRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("EntityId = %d", id);
	}

	public WrapperRepDish_EntityId(IDishRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("EntityId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<Dish> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<Dish> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(Dish item){
		item.EntityId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<Dish> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<Dish> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(Dish item){
		item.EntityId=id;
		return repository.update(item);
	}

	@Override
	public Dish getInstance(){
		Dish item = super.getInstance();
		item.EntityId = id;
		return item;
	}
}
