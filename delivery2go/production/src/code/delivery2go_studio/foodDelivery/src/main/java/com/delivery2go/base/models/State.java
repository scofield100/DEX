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

@TableMap(name="State")
public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public State(){}

	public State(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

/****** START Navigation Properties ******************/

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

	protected IEntityCollection<City> cities;

	public IEntityCollection<City> getCities(){
		if(cities == null && mapContext!=null){
			cities=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getICityRepository(), City.class, "StateId", Id);
		}
		return cities;
	}

	protected IEntityCollection<Entity> entities;

	public IEntityCollection<Entity> getEntities(){
		if(entities == null && mapContext!=null){
			entities=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityRepository(), Entity.class, "StateId", Id);
		}
		return entities;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
