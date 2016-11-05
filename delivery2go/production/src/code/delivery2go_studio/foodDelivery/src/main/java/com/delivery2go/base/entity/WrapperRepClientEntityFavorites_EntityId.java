package com.delivery2go.base.entity;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;

public class WrapperRepClientEntityFavorites_EntityId extends WrapperRepository<ClientEntityFavorites> implements IClientEntityFavoritesRepository , IAttachRepository<ClientEntityFavorites>{

	int id;
	String filter;
	public WrapperRepClientEntityFavorites_EntityId(IClientEntityFavoritesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("EntityId = %d", id);
	}

	public WrapperRepClientEntityFavorites_EntityId(IClientEntityFavoritesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("EntityId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<ClientEntityFavorites> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<ClientEntityFavorites> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(ClientEntityFavorites item){
		item.EntityId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<ClientEntityFavorites> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<ClientEntityFavorites> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(ClientEntityFavorites item){
		item.EntityId=id;
		return repository.update(item);
	}

	@Override
	public ClientEntityFavorites getInstance(){
		ClientEntityFavorites item = super.getInstance();
		item.EntityId = id;
		return item;
	}
}
