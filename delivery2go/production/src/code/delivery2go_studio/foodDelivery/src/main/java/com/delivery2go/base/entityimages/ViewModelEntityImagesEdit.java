package com.delivery2go.base.entityimages;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IEntityImagesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityImages;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;

public class ViewModelEntityImagesEdit extends EditViewModel implements IClosable {

	protected IEntityImagesRepository entityImagesRepository;

	protected EntityImages _item;
	protected int[] _itemId;
	public EntityImages getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	protected IImageRepository imageRepository;
	protected ArrayList<Image> images;
	public List<Image> getImages(){ return images; }

	public ViewModelEntityImagesEdit(IEditView view, IEntityImagesRepository entityImagesRepository, int[] id){
		super(view);

		this.entityImagesRepository=entityImagesRepository;
		this._itemId=id;
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		this.imageRepository=RepositoryManager.getInstance().getIImageRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = entityImagesRepository.get(_itemId);
		}
		else{
			this._item = entityImagesRepository.getInstance();
		}
		this.entities=this.entityRepository.getAll();
		this.images=this.imageRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.entityImagesRepository.close();
		this.entityRepository.close();
		this.imageRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=entityImagesRepository.update(this._item);
		}else{
			succes=entityImagesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}