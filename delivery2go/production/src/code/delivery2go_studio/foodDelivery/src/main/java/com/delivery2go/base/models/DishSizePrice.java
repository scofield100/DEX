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

@TableMap(name="DishSizePrice")
public class DishSizePrice implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DishSizePrice(){}

	public DishSizePrice(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="SizeId")
	@ForeingKey(to="Id", table="DishSize")
	@Required
	public int SizeId;

	@ColumnMap(column="DishId")
	@ForeingKey(to="Id", table="Dish")
	@Required
	public int DishId;

	@ColumnMap(column="ExtraPrice")
	public double ExtraPrice;

	@ColumnMap(column="AvailableCount")
	public Integer AvailableCount;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@NavigationColumn(fk="SizeId", column="Name")
	public String SizeName;

	@NavigationColumn(fk="DishId", column="Name")
	public String DishName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

	protected DishSize size;

	public DishSize getSize(){
		if(size == null && SizeId > 0 && mapContext!=null){
			size = mapContext.get(DishSize.class, SizeId);
		}
		return size;
	}

	public void setSize(DishSize value){
		size = value;
		if(value == null || value.Id == 0){
			SizeId = 0;
		} else {
			SizeId = value.Id;
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

	protected IEntityCollection<OrderDish> orderDishs;

	public IEntityCollection<OrderDish> getOrderDishs(){
		if(orderDishs == null && mapContext!=null){
			orderDishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishRepository(), OrderDish.class, "SizeId", Id);
		}
		return orderDishs;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return String.valueOf(Id);
	}
}
