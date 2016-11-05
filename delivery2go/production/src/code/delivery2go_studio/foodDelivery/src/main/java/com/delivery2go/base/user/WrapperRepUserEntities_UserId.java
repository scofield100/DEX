package com.delivery2go.base.user;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;

public class WrapperRepUserEntities_UserId extends WrapperRepository<UserEntities> implements IUserEntitiesRepository , IAttachRepository<UserEntities>{

	int id;
	String filter;
	public WrapperRepUserEntities_UserId(IUserEntitiesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("UserId = %d", id);
	}

	public WrapperRepUserEntities_UserId(IUserEntitiesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("UserId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<UserEntities> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<UserEntities> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(UserEntities item){
		item.UserId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<UserEntities> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<UserEntities> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(UserEntities item){
		item.UserId=id;
		return repository.update(item);
	}

	@Override
	public UserEntities getInstance(){
		UserEntities item = super.getInstance();
		item.UserId = id;
		return item;
	}
}
