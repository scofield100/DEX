package com.delivery2go.base.data.sqlite;

import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import java.util.ArrayList;
import java.util.List;
import com.enterlib.filtering.FilterCondition;
import com.enterlib.data.FilterValue;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.data.EntityMapRepository;
import com.enterlib.data.ManyToManyMapRepository;
 import com.delivery2go.base.models.User;
import com.delivery2go.base.models.UserEntities;
import com.delivery2go.base.data.repository.IUserRepository;

public class UserEntities_UserDbRepository extends ManyToManyMapRepository<User,UserEntities> implements IUserRepository{


	public UserEntities_UserDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, UserEntities.class, User.class, id, distint);
	}

}
