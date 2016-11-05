package com.delivery2go.base.cliententityfavorites;

import com.enterlib.IEqualityComparer;
import com.delivery2go.base.models.ClientEntityFavorites;
public class ClientEntityFavoritesComparer implements IEqualityComparer{
	@Override
	public boolean equals(Object item, Object value){
		if(item == null)
			return value==null;
		int itemId = ((ClientEntityFavorites)item).ClientId;
		int valueId;
		if (value instanceof Integer) {
			valueId = (Integer) value;
		}else{
			valueId = ((ClientEntityFavorites)value).ClientId;
		}
		return itemId == valueId;
	}
}
