package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class EntityDbRepository extends EntityMapRepository<Entity> implements IEntityRepository{

	public EntityDbRepository(EntityMapContext context){
		super(Entity.class, context);
	}

	@Override
	public boolean delete(Entity item){
		context.getMap(com.delivery2go.base.models.EntityImages.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityCategories.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.ClientEntityFavorites.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Order.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.UserEntities.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Addons.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityMenu.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityReview.class).delete("EntityId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Dish.class).delete("EntityId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Entity item) {
		return super.create(item);
	}

	@Override
	public boolean update(Entity item) {
		return super.update(item);
	}

}
