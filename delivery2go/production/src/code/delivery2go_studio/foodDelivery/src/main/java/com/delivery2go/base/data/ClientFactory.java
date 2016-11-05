package com.delivery2go.base.data;

import com.enterlib.data.EntityMap;
import com.enterlib.data.IEntityContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.Client;

public class ClientFactory implements IEntityMapInitializer<Client> ,IFactory<Client>{

	protected IEntityContext context;
	public ClientFactory(IEntityContext context){
		this.context = context;
	}
	@Override
	public void init(EntityMap<Client> map){
		map.setFactory(this);
	}

	@Override
	public Client getInstance(){
		//TODO: return your custum model class here 

		return new Client(context);
	}

}

