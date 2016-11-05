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

@TableMap(name="DeliveryAgent")
public class DeliveryAgent implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DeliveryAgent(){}

	public DeliveryAgent(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="Account")
	public String Account;

	@ColumnMap(column="DeliveryDiscount")
	public double DeliveryDiscount;

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

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

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

	protected IEntityCollection<DeliveryTarif> deliveryTarifs;

	public IEntityCollection<DeliveryTarif> getDeliveryTarifs(){
		if(deliveryTarifs == null && mapContext!=null){
			deliveryTarifs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDeliveryTarifRepository(), DeliveryTarif.class, "DeliveryAgentId", Id);
		}
		return deliveryTarifs;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
