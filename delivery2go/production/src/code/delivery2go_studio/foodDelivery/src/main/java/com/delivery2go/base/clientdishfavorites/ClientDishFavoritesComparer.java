package com.delivery2go.base.clientdishfavorites;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.ClientDishFavorites;
public class ClientDishFavoritesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((ClientDishFavorites)item).ClientId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((ClientDishFavorites)value).ClientId;
		}
		return itemId == valueId;
	}
}
