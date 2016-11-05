package com.delivery2go.base.user;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.User;
public class UserComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((User)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((User)value).Id;
		}
		return itemId == valueId;
	}
}
