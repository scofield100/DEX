package com.delivery2go.base.entity;

import com.delivery2go.OrderStateEnum;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.models.Order;
import com.enterlib.data.IAttachRepository;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.WrapperRepository;
import com.enterlib.filtering.FilterOp;

import java.util.ArrayList;

public class WrapperRepClosedOrder_EntityId extends WrapperRepository<Order> implements IOrderRepository , IAttachRepository<Order>{

	int id;
	String filter;
	public WrapperRepClosedOrder_EntityId(IOrderRepository repository, int id){
		super(repository);
		this.id = id;
		filter = String.format("EntityId = %d and (OrderStateId=%d or OrderStateId=%d)", id, OrderStateEnum.Delivered, OrderStateEnum.Cancelled);
	}

	public WrapperRepClosedOrder_EntityId(IOrderRepository repository, int id, int op){
		this(repository,id);
		filter = String.format("EntityId %s %d and and (OrderStateId=%d or OrderStateId=%d)", op == FilterOp.EQUALS ? "=": "!=", id, OrderStateEnum.Delivered, OrderStateEnum.Cancelled);	}

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
		item.EntityId=id;
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
		item.EntityId=id;
		return repository.update(item);
	}

	@Override
	public Order getInstance(){
		Order item = super.getInstance();
		item.EntityId = id;
		return item;
	}
}
