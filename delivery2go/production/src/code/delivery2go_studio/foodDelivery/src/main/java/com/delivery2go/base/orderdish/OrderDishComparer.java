package com.delivery2go.base.orderdish;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.OrderDish;
public class OrderDishComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((OrderDish)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((OrderDish)value).Id;
		}
		return itemId == valueId;
	}
}
