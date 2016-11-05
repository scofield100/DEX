package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.User;

public class UserFactory implements IEntityMapInitializer<User> ,IFactory<User>{

	protected IEntityContext context;
	public UserFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<User> map){
		map.setFactory(this);
	}

	@Override
	public User getInstance(){
		//TODO: return your custum model class here 

		return new User(context);
	}

}

