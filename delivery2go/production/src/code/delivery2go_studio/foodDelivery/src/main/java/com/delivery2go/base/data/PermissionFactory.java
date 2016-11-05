package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Permission;

public class PermissionFactory implements IEntityMapInitializer<Permission> ,IFactory<Permission>{

	protected IEntityContext context;
	public PermissionFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Permission> map){
		map.setFactory(this);
	}

	@Override
	public Permission getInstance(){
		//TODO: return your custum model class here 

		return new Permission(context);
	}

}

