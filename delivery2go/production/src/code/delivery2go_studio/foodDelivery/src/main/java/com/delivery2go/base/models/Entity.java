package com.delivery2go.base.models;
import java.util.Date;

import java.io.Serializable;

import com.delivery2go.DeliveryApp;
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
import com.enterlib.annotations.TimeStamp;

@TableMap(name="Entity")
public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Entity(){}

	public Entity(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="Ranking")
	public Integer Ranking;

	@ColumnMap(column="ReviewCount")
	public int ReviewCount;

	@ColumnMap(column="ImageId")
	@ForeingKey(to="Id", table="Image")
	public Integer ImageId;

	@ColumnMap(column="Adress")
	public String Adress;

	@ColumnMap(column="CityId")
	@ForeingKey(to="Id", table="City")
	public Integer CityId;

	@ColumnMap(column="StateId")
	@ForeingKey(to="Id", table="State")
	public Integer StateId;

	@ColumnMap(column="Lat")
	public Double Lat;

	@ColumnMap(column="Lng")
	public Double Lng;

	@ColumnMap(column="Phone")
	public String Phone;

	@ColumnMap(column="Email")
	public String Email;

	@ColumnMap(column="Tags")
	public String Tags;

	@ColumnMap(column="OpeningTime")
	public Date OpeningTime;

	@ColumnMap(column="CloseTime")
	public Date CloseTime;

	@ColumnMap(column="Description")
	public String Description;

	@ColumnMap(column="IsAvailable")
	public boolean IsAvailable;

	@ColumnMap(column="DeliveryPrice")
	public Double DeliveryPrice;

	@ColumnMap(column="DeliveryTimeMin")
	public Double DeliveryTimeMin;

	@ColumnMap(column="DeliveryTimeMax")
	public Double DeliveryTimeMax;

	@ColumnMap(column="MinPrice")
	public Double MinPrice;

	@ColumnMap(column="CreateDate")
	public Date CreateDate;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="HasDelivery")
	public boolean HasDelivery;

	@ColumnMap(column="HasPickup")
	public boolean HasPickup;

	@ColumnMap(column="Account")
	public String Account;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@NavigationColumn(fk="ImageId", column="Location")
	public String ImageLocation;

	@NavigationColumn(fk="CityId", column="Name")
	public String CityName;

	@NavigationColumn(fk="StateId", column="Name")
	public String StateName;

	@NavigationColumn(fk="UpdateUserId", column="Name")
	public String UpdateUserName;

	/****** START Navigation Properties ******************/

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

	protected City city;

	public City getCity(){
		if(city == null && CityId!=null && mapContext!=null ){
			city = mapContext.get(City.class, CityId);
		}
		return city;
	}

	public void setCity(City value){
		city = value;
		if(value == null || value.Id == 0){
			CityId = null;
		} else {
			CityId = value.Id;
		}
	}

	protected State state;

	public State getState(){
		if(state == null && StateId!=null && mapContext!=null ){
			state = mapContext.get(State.class, StateId);
		}
		return state;
	}

	public void setState(State value){
		state = value;
		if(value == null || value.Id == 0){
			StateId = null;
		} else {
			StateId = value.Id;
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

	protected IEntityCollection<Image> images;

	public IEntityCollection<Image> getImages(){
		if(images == null && mapContext!=null){
			images=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getEntityImages_ImageRepository(Id, false));
		}
		return images;
	}

	protected IEntityCollection<EntityCategory> entityCategories;

	public IEntityCollection<EntityCategory> getEntityCategories(){
		if(entityCategories == null && mapContext!=null){
			entityCategories=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getEntityCategories_EntityCategoryRepository(Id, false));
		}
		return entityCategories;
	}

	protected IEntityCollection<Client> clients;

	public IEntityCollection<Client> getClients(){
		if(clients == null && mapContext!=null){
			clients=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getClientEntityFavorites_ClientRepository(Id, false));
		}
		return clients;
	}

	protected IEntityCollection<Addons> addonses;

	public IEntityCollection<Addons> getAddonses(){
		if(addonses == null && mapContext!=null){
			addonses=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIAddonsRepository(), Addons.class, "EntityId", Id);
		}
		return addonses;
	}

	protected IEntityCollection<Dish> dishs;

	public IEntityCollection<Dish> getDishs(){
		if(dishs == null && mapContext!=null){
			dishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishRepository(), Dish.class, "EntityId", Id);
		}
		return dishs;
	}

	protected IEntityCollection<EntityMenu> entityMenus;

	public IEntityCollection<EntityMenu> getEntityMenus(){
		if(entityMenus == null && mapContext!=null){
			entityMenus=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityMenuRepository(), EntityMenu.class, "EntityId", Id);
		}
		return entityMenus;
	}

	protected IEntityCollection<Order> orders;

	public IEntityCollection<Order> getOrders(){
		if(orders == null && mapContext!=null){
			orders=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderRepository(), Order.class, "EntityId", Id);
		}
		return orders;
	}

	protected IEntityCollection<User> users;

	public IEntityCollection<User> getUsers(){
		if(users == null && mapContext!=null){
			users=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getUserEntities_UserRepository(Id, false));
		}
		return users;
	}

	protected IEntityCollection<EntityReview> entityReviews;

	public IEntityCollection<EntityReview> getEntityReviews(){
		if(entityReviews == null && mapContext!=null){
			entityReviews=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityReviewRepository(), EntityReview.class, "EntityId", Id);
		}
		return entityReviews;
	}

	/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}

	/**CODE*/
	public Double Distance;

	public String getDistanceString(){
		if(Distance == null)
			return "undefined";

		double d = Distance;
		String unit = "mil";
		if(d < 1){
			unit = "met";
			d = d * DeliveryApp.MILLE_METERS;
		}

		return StringUtils.parseCurrency(d)+" "+unit;
	}

	public String getReviewCountString(){
		return String.valueOf(ReviewCount)+" reviews";
	}

	public String getDeliveryTime(){
		String value = "(";
		if(DeliveryTimeMin!=null){
			value+=String.valueOf(DeliveryTimeMin);
		}else{
			value+="?";
		}

		value+="-";

		if(DeliveryTimeMax!=null){
			value+=String.valueOf(DeliveryTimeMax);
		}else{
			value+="?";
		}

		value+=" min )";
		return value;
	}

	public String getFullAdress(){
		return String.format("%s ,%s, %s", Adress, CityName, StateName);
	}

    public String getLocationText(){
        if(Lat!=null && Lng!=null) {
            return String.format("Lat:%f ,Lng:%f", Lat, Lng);
        }
        return null;
    }
}



