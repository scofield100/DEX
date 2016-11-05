package com.delivery2go.base.addons;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Addons;
public class AddonsComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Addons)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Addons)value).Id;
		}
		return itemId == valueId;
	}
}
