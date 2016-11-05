package com.delivery2go.base.userentities;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.UserEntities;
public class UserEntitiesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((UserEntities)item).UserId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((UserEntities)value).UserId;
		}
		return itemId == valueId;
	}
}
