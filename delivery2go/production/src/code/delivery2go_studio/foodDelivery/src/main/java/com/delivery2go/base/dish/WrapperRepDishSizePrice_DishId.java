package com.delivery2go.base.dish;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;

public class WrapperRepDishSizePrice_DishId extends WrapperRepository<DishSizePrice> implements IDishSizePriceRepository , IAttachRepository<DishSizePrice>{

	int id;
	String filter;
	public WrapperRepDishSizePrice_DishId(IDishSizePriceRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("DishId = %d", id);
	}

	public WrapperRepDishSizePrice_DishId(IDishSizePriceRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("DishId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

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
		item.DishId=id;
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
		item.DishId=id;
		return repository.update(item);
	}

	@Override
	public DishSizePrice getInstance(){
		DishSizePrice item = super.getInstance();
		item.DishId = id;
		return item;
	}
}
