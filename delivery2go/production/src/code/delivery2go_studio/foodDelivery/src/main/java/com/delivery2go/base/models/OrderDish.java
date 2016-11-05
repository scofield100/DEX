package com.delivery2go.base.models;
import java.util.Date;

import java.io.Serializable;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityCollection;
import com.enterlib.annotations.ColumnMap;
import com.enterlib.annotations.Filterable;
import com.enterlib.annotations.TableMap;
import com.enterlib.annotations.ForeingKey;
import com.enterlib.annotations.Required;
import com.enterlib.annotations.ModifiedBitFlag;
import com.enterlib.annotations.NavigationColumn;
import com.enterlib.annotations.TimeStamp;

@TableMap(name="OrderDish")
public class OrderDish implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public OrderDish(){}

	public OrderDish(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="OrderId")
	@ForeingKey(to="OrderId", table="Order")
	@Required
	public int OrderId;

	@ColumnMap(column="DishId")
	@ForeingKey(to="Id", table="Dish")
	@Required
	public int DishId;

	@ColumnMap(column="DishPrice")
	@Required
	public double DishPrice;

	@ColumnMap(column="Quantity")
	@Required
	public int Quantity;

	@ColumnMap(column="SizeId")
	@ForeingKey(to="Id", table="DishSizePrice")
	public Integer SizeId;

	@ColumnMap(column="DressingId")
	@ForeingKey(to="Id", table="Addons")
	public Integer DressingId;

	@ColumnMap(column="DressingPrice")
	public Double DressingPrice;

	@ColumnMap(column="SubTotal")
	public Double SubTotal;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="Remarks")
	@Filterable(isDefault = true)
	public String Remarks;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@NavigationColumn(fk="OrderId", column="Code")
	public String OrderCode;

	@NavigationColumn(fk="DishId", column="Name")
	public String DishName;

	@NavigationColumn(fk="DressingId", column="Name")
	public String DressingName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

	protected Order order;

	public Order getOrder(){
		if(order == null && OrderId > 0 && mapContext!=null){
			order = mapContext.get(Order.class, OrderId);
		}
		return order;
	}

	public void setOrder(Order value){
		order = value;
		if(value == null || value.OrderId == 0){
			OrderId = 0;
		} else {
			OrderId = value.OrderId;
		}
	}

	protected Dish dish;

	public Dish getDish(){
		if(dish == null && DishId > 0 && mapContext!=null){
			dish = mapContext.get(Dish.class, DishId);
		}
		return dish;
	}

	public void setDish(Dish value){
		dish = value;
		if(value == null || value.Id == 0){
			DishId = 0;
		} else {
			DishId = value.Id;
		}
	}

	protected DishSizePrice size;

	public DishSizePrice getSize(){
		if(size == null && SizeId!=null && mapContext!=null ){
			size = mapContext.get(DishSizePrice.class, SizeId);
		}
		return size;
	}

	public void setSize(DishSizePrice value){
		size = value;
		if(value == null || value.Id == 0){
			SizeId = null;
		} else {
			SizeId = value.Id;
		}
	}

	protected Addons dressing;

	public Addons getDressing(){
		if(dressing == null && DressingId!=null && mapContext!=null ){
			dressing = mapContext.get(Addons.class, DressingId);
		}
		return dressing;
	}

	public void setDressing(Addons value){
		dressing = value;
		if(value == null || value.Id == 0){
			DressingId = null;
		} else {
			DressingId = value.Id;
		}
	}

	protected User updateUser;

	public User getUpdateUser(){
		if(updateUser == null && UpdateUserId!=null && mapContext!=null ){
			updateUser = mapContext.get(User.class, UpdateUserId);
		}
		return updateUser;
	}

	public void setUpdateUser(User value){
		updateUser = value;
		if(value == null || value.Id == 0){
			UpdateUserId = null;
		} else {
			UpdateUserId = value.Id;
		}
	}

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

	protected IEntityCollection<OrderDishAddons> orderDishAddonses;

	public IEntityCollection<OrderDishAddons> getOrderDishAddonses(){
		if(orderDishAddonses == null && mapContext!=null){
			orderDishAddonses=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishAddonsRepository(), OrderDishAddons.class, "OrderDishId", Id);
		}
		return orderDishAddonses;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Remarks;
	}
}
