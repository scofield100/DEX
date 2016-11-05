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

@TableMap(name="Image")
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Image(){}

	public Image(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Location")
	@Required
	@Filterable(isDefault = true)
	public String Location;

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

	protected IEntityCollection<Entity> entities;

	public IEntityCollection<Entity> getEntities(){
		if(entities == null && mapContext!=null){
			entities=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getEntityImages_EntityRepository(Id, false));
		}
		return entities;
	}

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getDishImages_DishRepository(Id, false));
		}
		return dishs;
	}

	protected IEntityCollection<Entity> entityImage;

	public IEntityCollection<Entity> getEntityImage(){
		if(entityImage == null && mapContext!=null){
			entityImage=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityRepository(), Entity.class, "ImageId", Id);
		}
		return entityImage;
	}

	protected IEntityCollection<Dish> dishImage;

	public IEntityCollection<Dish> getDishImage(){
		if(dishImage == null && mapContext!=null){
			dishImage=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishRepository(), Dish.class, "ImageId", Id);
		}
		return dishImage;
	}

	protected IEntityCollection<EntityMenu> entityMenus;

	public IEntityCollection<EntityMenu> getEntityMenus(){
		if(entityMenus == null && mapContext!=null){
			entityMenus=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityMenuRepository(), EntityMenu.class, "ImageId", Id);
		}
		return entityMenus;
	}

	protected IEntityCollection<Order> orders;

	public IEntityCollection<Order> getOrders(){
		if(orders == null && mapContext!=null){
			orders=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderRepository(), Order.class, "ClientSignatureId", Id);
		}
		return orders;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Location;
	}
}
