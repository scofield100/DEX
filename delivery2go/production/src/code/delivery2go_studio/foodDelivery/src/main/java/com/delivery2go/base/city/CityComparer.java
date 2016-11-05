package com.delivery2go.base.city;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.City;
public class CityComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((City)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((City)value).Id;
		}
		return itemId == valueId;
	}
}
