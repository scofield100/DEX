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

@TableMap(name="UserEntities")
public class UserEntities implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public UserEntities(){}

	public UserEntities(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="UserId",key = true, order=0)
	@ForeingKey(to="Id", table="User")
	public int UserId;

	@ColumnMap(column="EntityId",key = true, order=1)
	@ForeingKey(to="Id", table="Entity")
	public int EntityId;

	@NavigationColumn(fk="UserId", column="Name")
	public String UserName;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

/****** START Navigation Properties ******************/

	protected User user;

	public User getUser(){
		if(user == null && UserId > 0 && mapContext!=null){
			user = mapContext.get(User.class, UserId);
		}
		return user;
	}

	public void setUser(User value){
		user = value;
		if(value == null || value.Id == 0){
			UserId = 0;
		} else {
			UserId = value.Id;
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
		return String.valueOf(UserId);
	}
}
