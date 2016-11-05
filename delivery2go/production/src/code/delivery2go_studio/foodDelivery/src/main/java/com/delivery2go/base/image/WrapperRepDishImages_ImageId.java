package com.delivery2go.base.image;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IDishImagesRepository;

public class WrapperRepDishImages_ImageId extends WrapperRepository<DishImages> implements IDishImagesRepository , IAttachRepository<DishImages>{

	int id;
	String filter;
	public WrapperRepDishImages_ImageId(IDishImagesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("ImageId = %d", id);
	}

	public WrapperRepDishImages_ImageId(IDishImagesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("ImageId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<DishImages> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<DishImages> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(DishImages item){
		item.ImageId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<DishImages> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<DishImages> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(DishImages item){
		item.ImageId=id;
		return repository.update(item);
	}

	@Override
	public DishImages getInstance(){
		DishImages item = super.getInstance();
		item.ImageId = id;
		return item;
	}
}
