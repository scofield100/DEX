package com.delivery2go.base.entityimages;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.EntityImages;
public class EntityImagesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((EntityImages)item).EntityId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((EntityImages)value).EntityId;
		}
		return itemId == valueId;
	}
}
