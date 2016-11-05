package com.delivery2go.base.roll;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Roll;
public class RollComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Roll)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Roll)value).Id;
		}
		return itemId == valueId;
	}
}
