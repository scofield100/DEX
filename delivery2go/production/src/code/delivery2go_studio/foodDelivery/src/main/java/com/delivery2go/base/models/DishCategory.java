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

@TableMap(name="DishCategory")
public class DishCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DishCategory(){}

	public DishCategory(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Code")
	@Required
	public String Code;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

/****** START Navigation Properties ******************/

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

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getDishCategories_DishRepository(Id, false));
		}
		return dishs;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
