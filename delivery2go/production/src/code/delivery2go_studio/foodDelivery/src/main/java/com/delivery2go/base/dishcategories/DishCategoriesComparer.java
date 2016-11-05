package com.delivery2go.base.dishcategories;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishCategories;
public class DishCategoriesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishCategories)item).DishId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishCategories)value).DishId;
		}
		return itemId == valueId;
	}
}
