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

@TableMap(name="EntityCategories")
public class EntityCategories implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public EntityCategories(){}

	public EntityCategories(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="EntityId",key = true, order=0)
	@ForeingKey(to="Id", table="Entity")
	public int EntityId;

	@ColumnMap(column="CategoryId",key = true, order=1)
	@ForeingKey(to="Id", table="EntityCategory")
	public int CategoryId;

	@NavigationColumn(fk="EntityId", column="Name")
	public String EntityName;

	@NavigationColumn(fk="CategoryId", column="Name")
	public String CategoryName;

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

	protected EntityCategory category;

	public EntityCategory getCategory(){
		if(category == null && CategoryId > 0 && mapContext!=null){
			category = mapContext.get(EntityCategory.class, CategoryId);
		}
		return category;
	}

	public void setCategory(EntityCategory value){
		category = value;
		if(value == null || value.Id == 0){
			CategoryId = 0;
		} else {
			CategoryId = value.Id;
		}
	}

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return String.valueOf(EntityId);
	}
}
