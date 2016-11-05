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
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ImageDbRepository extends EntityMapRepository<Image> implements IImageRepository{

	public ImageDbRepository(EntityMapContext context){
		super(Image.class, context);
	}

	@Override
	public boolean delete(Image item){
		context.getMap(com.delivery2go.base.models.EntityImages.class).delete("ImageId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.DishImages.class).delete("ImageId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Order.class).delete("ClientSignatureId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Entity.class).delete("ImageId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.EntityMenu.class).delete("ImageId="+String.valueOf(item.Id));
		context.getMap(com.delivery2go.base.models.Dish.class).delete("ImageId="+String.valueOf(item.Id));
		return super.delete(item);
	}

	@Override
	public int create(Image item) {
		return super.create(item);
	}

	@Override
	public boolean update(Image item) {
		return super.update(item);
	}

}
