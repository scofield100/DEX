package com.delivery2go.base.roll;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.Permission;
import com.delivery2go.base.data.repository.IPermissionRepository;

public class WrapperRepPermission_RollId extends WrapperRepository<Permission> implements IPermissionRepository , IAttachRepository<Permission>{

	int id;
	String filter;
	public WrapperRepPermission_RollId(IPermissionRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("RollId = %d", id);
	}

	public WrapperRepPermission_RollId(IPermissionRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("RollId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<Permission> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<Permission> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(Permission item){
		item.RollId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<Permission> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<Permission> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(Permission item){
		item.RollId=id;
		return repository.update(item);
	}

	@Override
	public Permission getInstance(){
		Permission item = super.getInstance();
		item.RollId = id;
		return item;
	}
}
