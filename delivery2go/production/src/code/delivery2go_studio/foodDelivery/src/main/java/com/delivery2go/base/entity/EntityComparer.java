package com.delivery2go.base.entity;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Entity;
public class EntityComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Entity)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Entity)value).Id;
		}
		return itemId == valueId;
	}
}
