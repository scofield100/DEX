package com.delivery2go.base.ordertype;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.OrderType;
public class OrderTypeComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((OrderType)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((OrderType)value).Id;
		}
		return itemId == valueId;
	}
}
