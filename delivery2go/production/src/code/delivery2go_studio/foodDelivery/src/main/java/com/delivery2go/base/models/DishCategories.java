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

@TableMap(name="DishCategories")
public class DishCategories implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DishCategories(){}

	public DishCategories(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="DishId",key = true, order=0)
	@ForeingKey(to="Id", table="Dish")
	public int DishId;

	@ColumnMap(column="CategoryId",key = true, order=1)
	@ForeingKey(to="Id", table="DishCategory")
	public int CategoryId;

	@NavigationColumn(fk="DishId", column="Name")
	public String DishName;

	@NavigationColumn(fk="CategoryId", column="Name")
	public String CategoryName;

/****** START Navigation Properties ******************/

	protected Dish dish;

	public Dish getDish(){
		if(dish == null && DishId > 0 && mapContext!=null){
			dish = mapContext.get(Dish.class, DishId);
		}
		return dish;
	}

	public void setDish(Dish value){
		dish = value;
		if(value == null || value.Id == 0){
			DishId = 0;
		} else {
			DishId = value.Id;
		}
	}

	protected DishCategory category;

	public DishCategory getCategory(){
		if(category == null && CategoryId > 0 && mapContext!=null){
			category = mapContext.get(DishCategory.class, CategoryId);
		}
		return category;
	}

	public void setCategory(DishCategory value){
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
		return String.valueOf(DishId);
	}
}
