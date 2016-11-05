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

@TableMap(name="DishImages")
public class DishImages implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DishImages(){}

	public DishImages(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="DishId",key = true, order=0)
	@ForeingKey(to="Id", table="Dish")
	public int DishId;

	@ColumnMap(column="ImageId",key = true, order=1)
	@ForeingKey(to="Id", table="Image")
	public int ImageId;

	@NavigationColumn(fk="DishId", column="Name")
	public String DishName;

	@NavigationColumn(fk="ImageId", column="Location")
	public String ImageLocation;

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

	protected Image image;

	public Image getImage(){
		if(image == null && ImageId > 0 && mapContext!=null){
			image = mapContext.get(Image.class, ImageId);
		}
		return image;
	}

	public void setImage(Image value){
		image = value;
		if(value == null || value.Id == 0){
			ImageId = 0;
		} else {
			ImageId = value.Id;
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
