package com.delivery2go.base.image;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IEntityImagesRepository;

public class WrapperRepEntityImages_ImageId extends WrapperRepository<EntityImages> implements IEntityImagesRepository , IAttachRepository<EntityImages>{

	int id;
	String filter;
	public WrapperRepEntityImages_ImageId(IEntityImagesRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("ImageId = %d", id);
	}

	public WrapperRepEntityImages_ImageId(IEntityImagesRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("ImageId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<EntityImages> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<EntityImages> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(EntityImages item){
		item.ImageId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<EntityImages> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<EntityImages> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(EntityImages item){
		item.ImageId=id;
		return repository.update(item);
	}

	@Override
	public EntityImages getInstance(){
		EntityImages item = super.getInstance();
		item.ImageId = id;
		return item;
	}
}
