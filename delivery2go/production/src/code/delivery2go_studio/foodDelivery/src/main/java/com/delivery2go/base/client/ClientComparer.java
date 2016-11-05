package com.delivery2go.base.client;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.Client;
public class ClientComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((Client)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((Client)value).Id;
		}
		return itemId == valueId;
	}
}
