package com.delivery2go.base.image;

import java.util.ArrayList;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.data.IAttachRepository;
import com.enterlib.filtering.FilterOp;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;

public class WrapperRepOrder_ClientSignatureId extends WrapperRepository<Order> implements IOrderRepository , IAttachRepository<Order>{

	int id;
	String filter;
	public WrapperRepOrder_ClientSignatureId(IOrderRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("ClientSignatureId = %d", id);
	}

	public WrapperRepOrder_ClientSignatureId(IOrderRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("ClientSignatureId %s %d", op == FilterOp.EQUALS ? "=": "!=", id);	}

	@Override
	public ArrayList<Order> getAll() {
		return super.getAll(filter, -1, -1);
	}

	@Override
	public ArrayList<Order> getAll(String condition, int skip, int take){
		return super.getAll(combineWhere(filter, condition), skip, take);
	}

	@Override
	public void deleteAll(String condition){
		super.deleteAll(combineWhere(filter, condition));
	}

	@Override
	public int create(Order item){
		item.ClientSignatureId=id;
		return super.create(item);
	}

	@Override
	public IEntityCursor<Order> getCursor(){
		return super.getCursor(filter, null);
	}

	@Override
	public IEntityCursor<Order> getCursor(String condition, String orderBy){
		return super.getCursor(combineWhere(filter, condition), orderBy);
	}

	@Override
	public int getCount(String condition) {
	return super.getCount(combineWhere(filter, condition));
	}

	@Override
	public boolean attach(Order item){
		item.ClientSignatureId=id;
		return repository.update(item);
	}

	@Override
	public Order getInstance(){
		Order item = super.getInstance();
		item.ClientSignatureId = id;
		return item;
	}
}
