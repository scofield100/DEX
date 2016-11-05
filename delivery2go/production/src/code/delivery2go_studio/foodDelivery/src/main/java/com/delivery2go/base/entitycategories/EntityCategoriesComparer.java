package com.delivery2go.base.entitycategories;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.EntityCategories;
public class EntityCategoriesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((EntityCategories)item).EntityId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((EntityCategories)value).EntityId;
		}
		return itemId == valueId;
	}
}
