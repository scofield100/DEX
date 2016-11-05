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

@TableMap(name="DeliveryTarif")
public class DeliveryTarif implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DeliveryTarif(){}

	public DeliveryTarif(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="FromMiles")
	public double FromMiles;

	@ColumnMap(column="ToMiles")
	public Double ToMiles;

	@ColumnMap(column="Price")
	@Required
	public double Price;

	@ColumnMap(column="DeliveryAgentId")
	@ForeingKey(to="Id", table="DeliveryAgent")
	@Required
	public int DeliveryAgentId;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="CreateDate")
	@TimeStamp
	public Date CreateDate;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	@Required
	public int UpdateUserId;

	@NavigationColumn(fk="DeliveryAgentId", column="Name")
	public String DeliveryAgentName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

	protected DeliveryAgent deliveryAgent;

	public DeliveryAgent getDeliveryAgent(){
		if(deliveryAgent == null && DeliveryAgentId > 0 && mapContext!=null){
			deliveryAgent = mapContext.get(DeliveryAgent.class, DeliveryAgentId);
		}
		return deliveryAgent;
	}

	public void setDeliveryAgent(DeliveryAgent value){
		deliveryAgent = value;
		if(value == null || value.Id == 0){
			DeliveryAgentId = 0;
		} else {
			DeliveryAgentId = value.Id;
		}
	}

	protected User updateUser;

	public User getUpdateUser(){
		if(updateUser == null && UpdateUserId > 0 && mapContext!=null){
			updateUser = mapContext.get(User.class, UpdateUserId);
		}
		return updateUser;
	}

	public void setUpdateUser(User value){
		updateUser = value;
		if(value == null || value.Id == 0){
			UpdateUserId = 0;
		} else {
			UpdateUserId = value.Id;
		}
	}

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return String.valueOf(Id);
	}
}
