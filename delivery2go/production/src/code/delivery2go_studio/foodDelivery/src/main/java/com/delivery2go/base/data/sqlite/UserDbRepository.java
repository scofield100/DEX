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
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class UserDbRepository extends EntityMapRepository<User> implements IUserRepository{

	public UserDbRepository(EntityMapContext context){
		super(User.class, context);
	}

	@Override
	public boolean delete(User item){
		context.getMap(com.delivery2go.base.models.DishCategory.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishSizePrice.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Image.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Order.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.OrderDish.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.OrderDishAddons.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Permission.class).delete("UserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.UserEntities.class).delete("UserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Entity.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Addons.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityMenu.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Dish.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DeliveryAgent.class).delete("UpdateUserId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DeliveryTarif.class).delete("UpdateUserId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(User item) {
		return super.create(item);
	}

	@Override
	public boolean update(User item) {
		return super.update(item);
	}

}
