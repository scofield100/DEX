package com.delivery2go.base.dishcategory;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishCategory;
public class DishCategoryComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishCategory)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishCategory)value).Id;
		}
		return itemId == valueId;
	}
}
