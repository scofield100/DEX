package com.delivery2go.base.order;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Order;
public class OrderComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Order)item).OrderId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Order)value).OrderId;
		}
		return itemId == valueId;
	}
}
