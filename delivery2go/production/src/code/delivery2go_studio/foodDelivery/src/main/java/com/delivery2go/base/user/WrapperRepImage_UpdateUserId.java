package com.delivery2go.base.user;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class WrapperRepImage_UpdateUserId extends WrapperRepository<Image> implements IImageRepository , IAttachRepository<Image>{

	int id;
	String filter;
	public WrapperRepImage_UpdateUserId(IImageRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("UpdateUserId = %d", id);
	}

	public WrapperRepImage_UpdateUserId(IImageRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("UpdateUserId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<Image> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<Image> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(Image item){
		item.UpdateUserId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<Image> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<Image> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(Image item){
		item.UpdateUserId=id;
		return repository.update(item);
	}

	@Override
	public Image getInstance(){
		Image item = super.getInstance();
		item.UpdateUserId = id;
		return item;
	}
}
