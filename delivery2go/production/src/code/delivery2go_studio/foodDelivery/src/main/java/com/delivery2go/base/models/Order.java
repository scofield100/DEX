package com.delivery2go.base.models;
import java.util.Date;

import java.io.Serializable;

import com.enterlib.data.EntityObject;
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
import com.enterlib.databinding.NotifyPropertyChanged;

@TableMap(name="Order")
public class Order extends NotifyPropertyChanged implements Serializable  {

	protected IEntityContext mapContext;

	public Order(){ }

	public Order(IEntityContext mapContext){
		this.mapContext = mapContext;

	}

	public boolean update(){
		if(mapContext == null)
			return  false;
		return  mapContext.update(Order.class, this);
	}

	public boolean delete(){
		if(mapContext == null)
			return  false;
		return  mapContext.delete(Order.class, this);
	}

	public boolean create(){
		if(mapContext == null)
			return false;
		return mapContext.create(Order.class, this) > 0;
	}



/********* START COLUMNS *******************/

	@ColumnMap(column="OrderId",key = true, order=0,writable = false)
	public int OrderId;

	@ColumnMap(column="Code")
	@Required
	@Filterable(isDefault = true)
	public String Code;

	@ColumnMap(column="EntityId")
	@ForeingKey(to="Id", table="Entity")
	@Required
	public int EntityId;

	@ColumnMap(column="ClientId")
	@ForeingKey(to="Id", table="Client")
	public int ClientId;

	@ColumnMap(column="CreateDate")
	@Required
	public Date CreateDate;

	@ColumnMap(column="TotalAmount")
	public double TotalAmount;

	@ColumnMap(column="DeliveryCharge")
	@Required
	public double DeliveryCharge;

	@ColumnMap(column="TaxAmount")
	public double TaxAmount;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="DeliveryAdress")
	public String DeliveryAdress;

	@ColumnMap(column="Phone")
	public String Phone;

	@ColumnMap(column="DeliveryLat")
	public Double DeliveryLat;

	@ColumnMap(column="DeliveryLng")
	public Double DeliveryLng;

	@ColumnMap(column="OrderStateId")
	@ForeingKey(to="Id", table="OrderState")
	public int OrderStateId;

	@ColumnMap(column="OrderTypeId")
	@ForeingKey(to="Id", table="OrderType")
	public int OrderTypeId;

	@ColumnMap(column="PaymentRef")
	public String PaymentRef;

	@ColumnMap(column="ClientSignatureId")
	@ForeingKey(to="Id", table="Image")
	public Integer ClientSignatureId;

	@ColumnMap(column="DeliveryTimeMinutes")
	public Integer DeliveryTimeMinutes;

	@ColumnMap(column="Remarks")
	public String Remarks;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@ColumnMap(column="ClientNotified")
	public boolean ClientNotified;

	@ColumnMap(column="EntityNotified")
	public boolean EntityNotified;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

	@NavigationColumn(fk="ClientId", column="Name")
	public String ClientName;

	@NavigationColumn(fk="OrderStateId", column="Name")
	public String OrderStateName;

	@NavigationColumn(fk="OrderTypeId", column="Name")
	public String OrderTypeName;

	@NavigationColumn(fk="ClientSignatureId", column="Location")
	public String ClientSignatureLocation;

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

	protected Client client;

	public Client getClient(){
		if(client == null && ClientId > 0 && mapContext!=null){
			client = mapContext.get(Client.class, ClientId);
		}
		return client;
	}

	public void setClient(Client value){
		client = value;
		if(value == null || value.Id == 0){
			ClientId = 0;
		} else {
			ClientId = value.Id;
		}
	}

	protected OrderState orderState;

	public OrderState getOrderState(){
		if(orderState == null && OrderStateId > 0 && mapContext!=null){
			orderState = mapContext.get(OrderState.class, OrderStateId);
		}
		return orderState;
	}

	public void setOrderState(OrderState value){
		orderState = value;
		if(value == null || value.Id == 0){
			OrderStateId = 0;
		} else {
			OrderStateId = value.Id;
		}
	}

	protected OrderType orderType;

	public OrderType getOrderType(){
		if(orderType == null && OrderTypeId > 0 && mapContext!=null){
			orderType = mapContext.get(OrderType.class, OrderTypeId);
		}
		return orderType;
	}

	public void setOrderType(OrderType value){
		orderType = value;
		if(value == null || value.Id == 0){
			OrderTypeId = 0;
		} else {
			OrderTypeId = value.Id;
		}
	}

	protected Image clientSignature;

	public Image getClientSignature(){
		if(clientSignature == null && ClientSignatureId!=null && mapContext!=null ){
			clientSignature = mapContext.get(Image.class, ClientSignatureId);
		}
		return clientSignature;
	}

	public void setClientSignature(Image value){
		clientSignature = value;
		if(value == null || value.Id == 0){
			ClientSignatureId = null;
		} else {
			ClientSignatureId = value.Id;
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
			orderDishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishRepository(), OrderDish.class, "OrderId", OrderId);
		}
		return orderDishs;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Code;
	}

	public double getTotalPayment(){
		return TotalAmount + DeliveryCharge + TaxAmount;
	}
}
