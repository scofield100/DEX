package com.delivery2go.base.dishsize;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishSize;
public class DishSizeComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishSize)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishSize)value).Id;
		}
		return itemId == valueId;
	}
}
