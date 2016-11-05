package com.delivery2go.base.entitymenu;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.EntityMenu;
public class EntityMenuComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((EntityMenu)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((EntityMenu)value).Id;
		}
		return itemId == valueId;
	}
}
