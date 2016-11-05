package com.delivery2go.base.entitycategory;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.EntityCategory;
public class EntityCategoryComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((EntityCategory)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((EntityCategory)value).Id;
		}
		return itemId == valueId;
	}
}
