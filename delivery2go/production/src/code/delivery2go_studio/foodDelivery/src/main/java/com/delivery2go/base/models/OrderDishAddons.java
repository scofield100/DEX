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

@TableMap(name="OrderDishAddons")
public class OrderDishAddons implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public OrderDishAddons(){}

	public OrderDishAddons(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="OrderDishId",key = true, order=0)
	@ForeingKey(to="Id", table="OrderDish")
	public int OrderDishId;

	@ColumnMap(column="AddonId",key = true, order=1)
	@ForeingKey(to="Id", table="Addons")
	public int AddonId;

	@ColumnMap(column="AddonPrice")
	@Required
	public double AddonPrice;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="Remarks")
	@Filterable(isDefault = true)
	public String Remarks;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@NavigationColumn(fk="OrderDishId", column="Remarks")
	public String OrderDishRemarks;

	@NavigationColumn(fk="AddonId", column="Name")
	public String AddonName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

	protected OrderDish orderDish;

	public OrderDish getOrderDish(){
		if(orderDish == null && OrderDishId > 0 && mapContext!=null){
			orderDish = mapContext.get(OrderDish.class, OrderDishId);
		}
		return orderDish;
	}

	public void setOrderDish(OrderDish value){
		orderDish = value;
		if(value == null || value.Id == 0){
			OrderDishId = 0;
		} else {
			OrderDishId = value.Id;
		}
	}

	protected Addons addon;

	public Addons getAddon(){
		if(addon == null && AddonId > 0 && mapContext!=null){
			addon = mapContext.get(Addons.class, AddonId);
		}
		return addon;
	}

	public void setAddon(Addons value){
		addon = value;
		if(value == null || value.Id == 0){
			AddonId = 0;
		} else {
			AddonId = value.Id;
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

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Remarks;
	}
}
