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
 import com.delivery2go.base.models.Image;
import com.delivery2go.base.models.EntityImages;
import com.delivery2go.base.data.repository.IImageRepository;

public class EntityImages_ImageDbRepository extends ManyToManyMapRepository<Image,EntityImages> implements IImageRepository{


	public EntityImages_ImageDbRepository(EntityMapContext context, int id, boolean distint){
		super(context, EntityImages.class, Image.class, id, distint);
	}

}
