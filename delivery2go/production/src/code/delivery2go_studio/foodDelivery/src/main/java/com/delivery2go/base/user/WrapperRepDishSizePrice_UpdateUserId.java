package com.delivery2go.base.user;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;

public class WrapperRepDishSizePrice_UpdateUserId extends WrapperRepository<DishSizePrice> implements IDishSizePriceRepository , IAttachRepository<DishSizePrice>{

	int id;
	String filter;
	public WrapperRepDishSizePrice_UpdateUserId(IDishSizePriceRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("UpdateUserId = %d", id);
	}

	public WrapperRepDishSizePrice_UpdateUserId(IDishSizePriceRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("UpdateUserId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<DishSizePrice> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<DishSizePrice> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(DishSizePrice item){
		item.UpdateUserId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<DishSizePrice> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<DishSizePrice> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(DishSizePrice item){
		item.UpdateUserId=id;
		return repository.update(item);
	}

	@Override
	public DishSizePrice getInstance(){
		DishSizePrice item = super.getInstance();
		item.UpdateUserId = id;
		return item;
	}
}
