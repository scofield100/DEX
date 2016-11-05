package com.delivery2go.base.dish;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;

public class WrapperRepClientDishFavorites_DishId extends WrapperRepository<ClientDishFavorites> implements IClientDishFavoritesRepository , IAttachRepository<ClientDishFavorites>{

	int id;
	String filter;
	public WrapperRepClientDishFavorites_DishId(IClientDishFavoritesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("DishId = %d", id);
	}

	public WrapperRepClientDishFavorites_DishId(IClientDishFavoritesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("DishId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<ClientDishFavorites> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<ClientDishFavorites> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(ClientDishFavorites item){
		item.DishId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<ClientDishFavorites> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<ClientDishFavorites> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(ClientDishFavorites item){
		item.DishId=id;
		return repository.update(item);
	}

	@Override
	public ClientDishFavorites getInstance(){
		ClientDishFavorites item = super.getInstance();
		item.DishId = id;
		return item;
	}
}
