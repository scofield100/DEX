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

@TableMap(name="EntityMenu")
public class EntityMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public EntityMenu(){}

	public EntityMenu(IEntityContext mapContext){
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

	@ColumnMap(column="IsSpecial")
	public boolean IsSpecial;

	@ColumnMap(column="IsAvailable")
	public boolean IsAvailable;

	@ColumnMap(column="ImageId")
	@ForeingKey(to="Id", table="Image")
	public Integer ImageId;

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

	@NavigationColumn(fk="ImageId", column="Location")
	public String ImageLocation;

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

	protected Image image;

	public Image getImage(){
		if(image == null && ImageId!=null && mapContext!=null ){
			image = mapContext.get(Image.class, ImageId);
		}
		return image;
	}

	public void setImage(Image value){
		image = value;
		if(value == null || value.Id == 0){
			ImageId = null;
		} else {
			ImageId = value.Id;
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

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishRepository(), Dish.class, "MenuId", Id);
		}
		return dishs;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
