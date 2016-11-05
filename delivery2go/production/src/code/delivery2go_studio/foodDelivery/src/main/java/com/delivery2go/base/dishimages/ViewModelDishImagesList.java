package com.delivery2go.base.dishimages;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishImages;

import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ViewModelDishImagesList extends EntityCursorViewModel<DishImages>{

	protected IDishImagesRepository dishImagesRepository;


	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	public ViewModelDishImagesList (IDataView view, IDishImagesRepository dishImagesRepository) {
		super(view);
		
		this.dishImagesRepository = dishImagesRepository;   

		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());
		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishImages> createCursor()
			throws InvalidOperationException {		
		return this.dishImagesRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishImagesRepository.close();
		//this.dishRepository.close();
		//this.imageRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishImagesRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishImages>attachedRep = (IAttachRepository<DishImages>) dishImagesRepository;
			if(!attachedRep.attach((DishImages) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishImages obj = (DishImages) items.get(i);
			dishImagesRepository.delete(obj);
		}
		return true;
	}



}