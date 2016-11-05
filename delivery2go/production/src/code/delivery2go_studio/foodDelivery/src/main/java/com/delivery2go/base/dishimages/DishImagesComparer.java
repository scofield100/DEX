package com.delivery2go.base.dishimages;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishImages;
public class DishImagesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishImages)item).DishId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishImages)value).DishId;
		}
		return itemId == valueId;
	}
}
