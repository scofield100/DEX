package com.delivery2go.base.orderdishaddons;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.OrderDishAddons;
public class OrderDishAddonsComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((OrderDishAddons)item).OrderDishId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((OrderDishAddons)value).OrderDishId;
		}
		return itemId == valueId;
	}
}
