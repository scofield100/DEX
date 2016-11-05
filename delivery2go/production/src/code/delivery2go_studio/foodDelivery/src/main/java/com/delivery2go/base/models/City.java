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

@TableMap(name="City")
public class City implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IEntityContext mapContext;

	public City(){}

	public City(IEntityContext mapContext){
		this.mapContext = mapContext;
	}

	@ColumnMap(column="Id",key = true, order=0,writable = false)
	public int Id;

	@ColumnMap(column="Name")
	@Required
	@Filterable(isDefault = true)
	public String Name;

	@ColumnMap(column="StateId")
	@ForeingKey(to="Id", table="State")
	@Required
	public int StateId;

	@NavigationColumn(fk="StateId", column="Name")
	public String StateName;

/****** START Navigation Properties ******************/

	protected State state;

	public State getState(){
		if(state == null && StateId > 0 && mapContext!=null){
			state = mapContext.get(State.class, StateId);
		}
		return state;
	}

	public void setState(State value){
		state = value;
		if(value == null || value.Id == 0){
			StateId = 0;
		} else {
			StateId = value.Id;
		}
	}

/****** END Navigation Properties ******************/

/****** START Inverse Navigation Properties ******************/

	protected IEntityCollection<Entity> entities;

	public IEntityCollection<Entity> getEntities(){
		if(entities == null && mapContext!=null){
			entities=mapContext.getManyToOne(com.delivery2go.base.data.RepositoryManager.getInstance().getIEntityRepository(), Entity.class, "CityId", Id);
		}
		return entities;
	}

/****** END Inverse Navigation Properties ******************/

	@Override
	public String toString() {
		return Name;
	}
}
