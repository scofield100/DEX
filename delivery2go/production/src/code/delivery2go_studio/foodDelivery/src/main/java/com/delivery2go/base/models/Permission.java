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

@TableMap(name="Permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Permission(){}

	public Permission(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	public Permission(int rollId, int userId, boolean canRead, boolean canWrite, boolean canCreate, boolean canDelete) {
		RollId = rollId;
		UserId = userId;
		CanRead = canRead;
		CanWrite = canWrite;
		CanCreate = canCreate;
		CanDelete = canDelete;
		UpdateDate = new Date();
		CreateDate = UpdateDate;

	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="RollId")
	@ForeingKey(to="Id", table="Roll")
	@Required
	public int RollId;

	@ColumnMap(column="UserId")
	@ForeingKey(to="Id", table="User")
	@Required
	public int UserId;

	@ColumnMap(column="CanRead")
	public boolean CanRead;

	@ColumnMap(column="CanWrite")
	public boolean CanWrite;

	@ColumnMap(column="CanCreate")
	public boolean CanCreate;

	@ColumnMap(column="CanDelete")
	public boolean CanDelete;

	@ColumnMap(column="CreateDate")
	@TimeStamp
	public Date CreateDate;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@NavigationColumn(fk="RollId", column="Name")
	public String RollName;

	@NavigationColumn(fk="UserId", column="Name")
	public String UserName;

/****** START Navigation Properties ******************/

	protected Roll roll;

	public Roll getRoll(){
		if(roll == null && RollId > 0 && mapContext!=null){
			roll = mapContext.get(Roll.class, RollId);
		}
		return roll;
	}

	public void setRoll(Roll value){
		roll = value;
		if(value == null || value.Id == 0){
			RollId = 0;
		} else {
			RollId = value.Id;
		}
	}

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

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return String.valueOf(Id);
	}
}
