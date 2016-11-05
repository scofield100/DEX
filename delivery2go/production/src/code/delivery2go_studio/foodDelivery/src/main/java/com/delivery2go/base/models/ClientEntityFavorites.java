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

@TableMap(name="ClientEntityFavorites")
public class ClientEntityFavorites implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public ClientEntityFavorites(){}

	public ClientEntityFavorites(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="ClientId",key = true, order=0)
	@ForeingKey(to="Id", table="Client")
	public int ClientId;

	@ColumnMap(column="EntityId",key = true, order=1)
	@ForeingKey(to="Id", table="Entity")
	public int EntityId;

	@NavigationColumn(fk="ClientId", column="Name")
	public String ClientName;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

/****** START Navigation Properties ******************/

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

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return String.valueOf(ClientId);
	}
}
