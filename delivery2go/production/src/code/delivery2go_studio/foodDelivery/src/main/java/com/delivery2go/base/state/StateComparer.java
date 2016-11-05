package com.delivery2go.base.state;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.State;
public class StateComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((State)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((State)value).Id;
		}
		return itemId == valueId;
	}
}
