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

@TableMap(name="Addons")
public class Addons implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Addons(){}

	public Addons(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="EntityId")
	@ForeingKey(to="Id", table="Entity")
	@Required
	public int EntityId;

	@ColumnMap(column="Price")
	@Required
	public double Price;

	@ColumnMap(column="IsAvailable")
	@Required
	public boolean IsAvailable;

	@ColumnMap(column="IsDressing")
	public boolean IsDressing;

	@ColumnMap(column="AvailableCount")
	public Integer AvailableCount;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="CreateDate")
	public Date CreateDate;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

	protected Entity entity;

	public Entity getEntity(){
		if(entity == null && EntityId > 0 && mapContext!=null){
			entity = mapContext.get(Entity.class, EntityId);
		}
		return entity;
	}

	public void setEntity(Entity value){
		entity = value;
		if(value == null || value.Id == 0){
			EntityId = 0;
		} else {
			EntityId = value.Id;
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
			orderDishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishRepository(), OrderDish.class, "DressingId", Id);
		}
		return orderDishs;
	}

	protected IEntityCollection<OrderDishAddons> orderDishAddonses;

	public IEntityCollection<OrderDishAddons> getOrderDishAddonses(){
		if(orderDishAddonses == null && mapContext!=null){
			orderDishAddonses=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishAddonsRepository(), OrderDishAddons.class, "AddonId", Id);
		}
		return orderDishAddonses;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
