package com.delivery2go.base.models;
import java.util.Date;

import java.io.Serializable;

import com.enterlib.StringUtils;
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

@TableMap(name="Client")
public class Client extends NotifyPropertyChanged implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Client(){}

	public Client(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	public IEntityContext getEntityContext(){
		return this.mapContext;
	}

	public void setEntityContext(IEntityContext entityContext) {
		this.mapContext = entityContext;
	}

	/********* START COLUMNS *******************/
	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="Guid")
	@Required
	public String Guid;

	@ColumnMap(column="LastName")
	public String LastName;

	@ColumnMap(column="LastName2")
	public String LastName2;

	@ColumnMap(column="AdressNo")
	public String AdressNo;

	@ColumnMap(column="AddressStreet")
	public String AddressStreet;

	@ColumnMap(column="Email")
	public String Email;

	@ColumnMap(column="Phone")
	public String Phone;

	@ColumnMap(column="Mobile")
	public String Mobile;

	@ColumnMap(column="Username")
	public String Username;

	@ColumnMap(column="Password")
	public String Password;

	@ColumnMap(column="IsRegistered")
	public boolean IsRegistered;

	@ColumnMap(column="Lat")
	public Double Lat;

	@ColumnMap(column="Lng")
	public Double Lng;

	@ColumnMap(column="CreateDate")
	@TimeStamp
	public Date CreateDate;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="DeviceId")
	public String DeviceId;

	@ColumnMap(column="SessionId")
	public String SessionId;

	/********* END COLUMNS *******************/
	/********* START Navigation Properties ******************/

	/********* END Navigation Properties ******************/

	/********* START Inverse Navigation Properties ******************/

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getClientDishFavorites_DishRepository(Id, false));
		}
		return dishs;
	}

	protected IEntityCollection<Entity> entities;

	public IEntityCollection<Entity> getEntities(){
		if(entities == null && mapContext!=null){
			entities=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getClientEntityFavorites_EntityRepository(Id, false));
		}
		return entities;
	}

	protected IEntityCollection<EntityReview> entityReviews;

	public IEntityCollection<EntityReview> getEntityReviews(){
		if(entityReviews == null && mapContext!=null){
			entityReviews=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityReviewRepository(), EntityReview.class, "ClientId", Id);
		}
		return entityReviews;
	}

	protected IEntityCollection<DishReview> dishReviews;

	public IEntityCollection<DishReview> getDishReviews(){
		if(dishReviews == null && mapContext!=null){
			dishReviews=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishReviewRepository(), DishReview.class, "ClientId", Id);
		}
		return dishReviews;
	}

	protected IEntityCollection<Order> orders;

	public IEntityCollection<Order> getOrders(){
		if(orders == null && mapContext!=null){
			orders=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderRepository(), Order.class, "ClientId", Id);
		}
		return orders;
	}

	/********* END Inverse Navigation Properties ******************/

	/********* START MANAGERS **************************/
	public boolean update(){
		if(mapContext == null)
			return false;
		return mapContext.update(Client.class, this);
	}

	public boolean delete(){
		if(mapContext == null)
			return false;
		return mapContext.delete(Client.class, this);
	}

	public boolean create(){
		if(mapContext == null)
			return false;
		return mapContext.create(Client.class, this) > 0;
	}

	/********* END MANAGERS **************************/
	@Override
	public String toString() {
		return Name;
	}

	public  String getAdress(){
		if(!StringUtils.isNullOrWhitespace(AdressNo))
			return AdressNo + ", "+AddressStreet;
		return AddressStreet;
	}


}
