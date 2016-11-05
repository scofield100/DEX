package com.delivery2go.base.orderstate;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.OrderState;
public class OrderStateComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((OrderState)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((OrderState)value).Id;
		}
		return itemId == valueId;
	}
}
