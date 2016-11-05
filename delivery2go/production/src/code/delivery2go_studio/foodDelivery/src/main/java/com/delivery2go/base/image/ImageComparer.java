package com.delivery2go.base.image;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Image;
public class ImageComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Image)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Image)value).Id;
		}
		return itemId == valueId;
	}
}
