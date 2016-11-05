package com.delivery2go.base.dishimages;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishImages;

import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ViewModelDishImagesEdit extends EditViewModel implements IClosable {

	protected IDishImagesRepository dishImagesRepository;

	protected DishImages _item;
	protected int[] _itemId;
	public DishImages getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	protected IImageRepository imageRepository;
	protected ArrayList<Image> images;
	public List<Image> getImages(){ return images; }

	public ViewModelDishImagesEdit(IEditView view, IDishImagesRepository dishImagesRepository, int[] id){
		super(view);

		this.dishImagesRepository=dishImagesRepository;
		this._itemId=id;
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
		this.imageRepository=RepositoryManager.getInstance().getIImageRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishImagesRepository.get(_itemId);
		}
		else{
			this._item = dishImagesRepository.getInstance();
		}
		this.dishs=this.dishRepository.getAll();
		this.images=this.imageRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.dishImagesRepository.close();
		this.dishRepository.close();
		this.imageRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=dishImagesRepository.update(this._item);
		}else{
			succes=dishImagesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}