package com.delivery2go.base.dishsizeprice;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.DishSizePrice;
public class DishSizePriceComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((DishSizePrice)item).Id;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((DishSizePrice)value).Id;
		}
		return itemId == valueId;
	}
}
