package com.delivery2go.base.models;
import java.util.Date;

import java.io.Serializable;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityCollection;
import com.enterlib.annotations.ColumnMap;
import com.enterlib.annotations.Filterable;
import com.enterlib.annotations.TableMap;
import com.enterlib.annotations.ForeingKey;
import com.enterlib.annotations.Required;
import com.enterlib.annotations.ModifiedBitFlag;
import com.enterlib.annotations.NavigationColumn;
import com.enterlib.annotations.TimeStamp;

@TableMap(name="DishSize")
public class DishSize implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public DishSize(){}

	public DishSize(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Code")
	@Required
	public String Code;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

/****** START Navigation Properties ******************/

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

	protected IEntityCollection<DishSizePrice> dishSizePrices;

	public IEntityCollection<DishSizePrice> getDishSizePrices(){
		if(dishSizePrices == null && mapContext!=null){
			dishSizePrices=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIDishSizePriceRepository(), DishSizePrice.class, "SizeId", Id);
		}
		return dishSizePrices;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
