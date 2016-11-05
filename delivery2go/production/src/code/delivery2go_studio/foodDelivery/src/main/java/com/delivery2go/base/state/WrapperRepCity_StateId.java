package com.delivery2go.base.state;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.City;
import com.delivery2go.base.data.repository.ICityRepository;

public class WrapperRepCity_StateId extends WrapperRepository<City> implements ICityRepository , IAttachRepository<City>{

	int id;
	String filter;
	public WrapperRepCity_StateId(ICityRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("StateId = %d", id);
	}

	public WrapperRepCity_StateId(ICityRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("StateId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<City> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<City> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(City item){
		item.StateId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<City> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<City> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(City item){
		item.StateId=id;
		return repository.update(item);
	}

	@Override
	public City getInstance(){
		City item = super.getInstance();
		item.StateId = id;
		return item;
	}
}
