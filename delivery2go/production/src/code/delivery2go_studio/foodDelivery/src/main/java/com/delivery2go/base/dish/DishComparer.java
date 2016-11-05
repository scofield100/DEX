package com.delivery2go.base.dish;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Dish;
public class DishComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Dish)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Dish)value).Id;
		}
		return itemId == valueId;
	}
}
