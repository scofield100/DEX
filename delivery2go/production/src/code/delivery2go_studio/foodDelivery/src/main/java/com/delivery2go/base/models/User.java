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
import com.enterlib.databinding.NotifyPropertyChanged;
import com.enterlib.annotations.TimeStamp;

@TableMap(name="User")
public class User extends NotifyPropertyChanged implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public User(){}

	public User(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	public IEntityContext getEntityContext(){
		return this.mapContext;
	}

	/********* START COLUMNS *******************/
	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="Username")
	@Required
	public String Username;

	@ColumnMap(column="Password")
	@Required
	public String Password;

	@ColumnMap(column="SessionId")
	public String SessionId;

	@ColumnMap(column="Mobile")
	public String Mobile;

	@ColumnMap(column="Phone")
	public String Phone;

	@ColumnMap(column="Adress")
	public String Adress;

	@ColumnMap(column="IsActive")
	public boolean IsActive;

	@ColumnMap(column="CreateDate")
	@TimeStamp
	public Date CreateDate;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="Superadmin")
	public boolean Superadmin;

	/********* END COLUMNS *******************/
	/********* START Navigation Properties ******************/

	/********* END Navigation Properties ******************/

	/********* START Inverse Navigation Properties ******************/

	protected IEntityCollection<DishCategory> dishCategories;

	public IEntityCollection<DishCategory> getDishCategories(){
		if(dishCategories == null && mapContext!=null){
			dishCategories=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishCategoryRepository(), DishCategory.class, "UpdateUserId", Id);
		}
		return dishCategories;
	}

	protected IEntityCollection<DishSizePrice> dishSizePrices;

	public IEntityCollection<DishSizePrice> getDishSizePrices(){
		if(dishSizePrices == null && mapContext!=null){
			dishSizePrices=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishSizePriceRepository(), DishSizePrice.class, "UpdateUserId", Id);
		}
		return dishSizePrices;
	}

	protected IEntityCollection<Image> images;

	public IEntityCollection<Image> getImages(){
		if(images == null && mapContext!=null){
			images=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIImageRepository(), Image.class, "UpdateUserId", Id);
		}
		return images;
	}

	protected IEntityCollection<OrderDish> orderDishs;

	public IEntityCollection<OrderDish> getOrderDishs(){
		if(orderDishs == null && mapContext!=null){
			orderDishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishRepository(), OrderDish.class, "UpdateUserId", Id);
		}
		return orderDishs;
	}

	protected IEntityCollection<OrderDishAddons> orderDishAddonses;

	public IEntityCollection<OrderDishAddons> getOrderDishAddonses(){
		if(orderDishAddonses == null && mapContext!=null){
			orderDishAddonses=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishAddonsRepository(), OrderDishAddons.class, "UpdateUserId", Id);
		}
		return orderDishAddonses;
	}

	protected IEntityCollection<Permission> permissions;

	public IEntityCollection<Permission> getPermissions(){
		if(permissions == null && mapContext!=null){
			permissions=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIPermissionRepository(), Permission.class, "UserId", Id);
		}
		return permissions;
	}

	protected IEntityCollection<Entity> entities;

	public IEntityCollection<Entity> getEntities(){
		if(entities == null && mapContext!=null){
			entities=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getUserEntities_EntityRepository(Id, false));
		}
		return entities;
	}

	protected IEntityCollection<Entity> entityUpdateUser;

	public IEntityCollection<Entity> getEntityUpdateUser(){
		if(entityUpdateUser == null && mapContext!=null){
			entityUpdateUser=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityRepository(), Entity.class, "UpdateUserId", Id);
		}
		return entityUpdateUser;
	}

	protected IEntityCollection<Addons> addonses;

	public IEntityCollection<Addons> getAddonses(){
		if(addonses == null && mapContext!=null){
			addonses=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIAddonsRepository(), Addons.class, "UpdateUserId", Id);
		}
		return addonses;
	}

	protected IEntityCollection<EntityMenu> entityMenus;

	public IEntityCollection<EntityMenu> getEntityMenus(){
		if(entityMenus == null && mapContext!=null){
			entityMenus=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityMenuRepository(), EntityMenu.class, "UpdateUserId", Id);
		}
		return entityMenus;
	}

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishRepository(), Dish.class, "UpdateUserId", Id);
		}
		return dishs;
	}

	protected IEntityCollection<DeliveryAgent> deliveryAgents;

	public IEntityCollection<DeliveryAgent> getDeliveryAgents(){
		if(deliveryAgents == null && mapContext!=null){
			deliveryAgents=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDeliveryAgentRepository(), DeliveryAgent.class, "UpdateUserId", Id);
		}
		return deliveryAgents;
	}

	protected IEntityCollection<DeliveryTarif> deliveryTarifs;

	public IEntityCollection<DeliveryTarif> getDeliveryTarifs(){
		if(deliveryTarifs == null && mapContext!=null){
			deliveryTarifs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDeliveryTarifRepository(), DeliveryTarif.class, "UpdateUserId", Id);
		}
		return deliveryTarifs;
	}

	protected IEntityCollection<Order> orders;

	public IEntityCollection<Order> getOrders(){
		if(orders == null && mapContext!=null){
			orders=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderRepository(), Order.class, "UpdateUserId", Id);
		}
		return orders;
	}

	/********* END Inverse Navigation Properties ******************/

	/********* START MANAGERS **************************/
	public boolean update(){
		if(mapContext == null)
			return false;
		return mapContext.update(User.class, this);
	}

	public boolean delete(){
		if(mapContext == null)
			return false;
		return mapContext.delete(User.class, this);
	}

	public boolean create(){
		if(mapContext == null)
			return false;
		return mapContext.create(User.class, this) > 0;
	}

	/********* END MANAGERS **************************/
	@Override
	public String toString() {
		return Name;
	}
}
