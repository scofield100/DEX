package com.delivery2go.base.permission;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Permission;
public class PermissionComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Permission)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Permission)value).Id;
		}
		return itemId == valueId;
	}
}
