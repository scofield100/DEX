package com.delivery2go.base.dishreview;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishReview;
public class DishReviewComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishReview)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishReview)value).Id;
		}
		return itemId == valueId;
	}
}
