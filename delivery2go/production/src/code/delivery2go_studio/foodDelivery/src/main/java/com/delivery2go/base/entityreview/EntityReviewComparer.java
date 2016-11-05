package com.delivery2go.base.entityreview;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.EntityReview;
public class EntityReviewComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((EntityReview)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((EntityReview)value).Id;
		}
		return itemId == valueId;
	}
}
