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

@TableMap(name="Dish")
public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public Dish(){}

	public Dish(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="IsAvailable")
	public boolean IsAvailable;

	@ColumnMap(column="ImageId")
	@ForeingKey(to="Id", table="Image")
	public Integer ImageId;

	@ColumnMap(column="Ranking")
	public Integer Ranking;

	@ColumnMap(column="ReviewCount")
	public Integer ReviewCount;

	@ColumnMap(column="OrderCount")
	public int OrderCount;

	@ColumnMap(column="Price")
	@Required
	public double Price;

	@ColumnMap(column="EntityId")
	@ForeingKey(to="Id", table="Entity")
	@Required
	public int EntityId;

	@ColumnMap(column="InWhatsGood")
	public boolean InWhatsGood;

	@ColumnMap(column="Description")
	public String Description;

	@ColumnMap(column="AvailableCount")
	public Integer AvailableCount;

	@ColumnMap(column="Tags")
	public String Tags;

	@ColumnMap(column="MenuId")
	@ForeingKey(to="Id", table="EntityMenu")
	public Integer MenuId;

	@ColumnMap(column="UpdateUserId")
	@ForeingKey(to="Id", table="User")
	public Integer UpdateUserId;

	@ColumnMap(column="UpdateDate")
	@TimeStamp
	public Date UpdateDate;

	@ColumnMap(column="CreateDate")
	public Date CreateDate;

	@NavigationColumn(fk="ImageId", column="Location")
	public String ImageLocation;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

	@NavigationColumn(fk="MenuId", column="Name")
	public String MenuName;

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

	protected EntityMenu menu;

	public EntityMenu getMenu(){
		if(menu == null && MenuId!=null && mapContext!=null ){
			menu = mapContext.get(EntityMenu.class, MenuId);
		}
		return menu;
	}

	public void setMenu(EntityMenu value){
		menu = value;
		if(value == null || value.Id == 0){
			MenuId = null;
		} else {
			MenuId = value.Id;
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

	protected IEntityCollection<DishCategory> dishCategories;

	public IEntityCollection<DishCategory> getDishCategories(){
		if(dishCategories == null && mapContext!=null){
			dishCategories=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getDishCategories_DishCategoryRepository(Id, false));
		}
		return dishCategories;
	}

	protected IEntityCollection<Image> images;

	public IEntityCollection<Image> getImages(){
		if(images == null && mapContext!=null){
			images=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getDishImages_ImageRepository(Id, false));
		}
		return images;
	}

	protected IEntityCollection<Client> clients;

	public IEntityCollection<Client> getClients(){
		if(clients == null && mapContext!=null){
			clients=mapContext.getCollection(com.delivery2go.base.data.RepositoryManager.getInstance().getClientDishFavorites_ClientRepository(Id, false));
		}
		return clients;
	}

	protected IEntityCollection<DishSizePrice> dishSizePrices;

	public IEntityCollection<DishSizePrice> getDishSizePrices(){
		if(dishSizePrices == null && mapContext!=null){
			dishSizePrices=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishSizePriceRepository(), DishSizePrice.class, "DishId", Id);
		}
		return dishSizePrices;
	}

	protected IEntityCollection<OrderDish> orderDishs;

	public IEntityCollection<OrderDish> getOrderDishs(){
		if(orderDishs == null && mapContext!=null){
			orderDishs=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIOrderDishRepository(), OrderDish.class, "DishId", Id);
		}
		return orderDishs;
	}

	protected IEntityCollection<DishReview> dishReviews;

	public IEntityCollection<DishReview> getDishReviews(){
		if(dishReviews == null && mapContext!=null){
			dishReviews=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishReviewRepository(), DishReview.class, "DishId", Id);
		}
		return dishReviews;
	}


	/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
	
	/** CODE */	
	public String getReviewCountString(){
		if(ReviewCount!=null){
			return String.valueOf(ReviewCount)+" reviews";
		}
		return null;
	}
}

