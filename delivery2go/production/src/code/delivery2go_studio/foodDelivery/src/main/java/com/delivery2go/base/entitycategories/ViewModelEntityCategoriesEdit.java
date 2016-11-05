package com.delivery2go.base.entitycategories;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityCategories;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;

public class ViewModelEntityCategoriesEdit extends EditViewModel implements IClosable {

	protected IEntityCategoriesRepository entityCategoriesRepository;

	protected EntityCategories _item;
	protected int[] _itemId;
	public EntityCategories getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	protected IEntityCategoryRepository entityCategoryRepository;
	protected ArrayList<EntityCategory> entityCategories;
	public List<EntityCategory> getEntityCategories(){ return entityCategories; }

	public ViewModelEntityCategoriesEdit(IEditView view, IEntityCategoriesRepository entityCategoriesRepository, int[] id){
		super(view);

		this.entityCategoriesRepository=entityCategoriesRepository;
		this._itemId=id;
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();
		this.entityCategoryRepository=RepositoryManager.getInstance().getIEntityCategoryRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = entityCategoriesRepository.get(_itemId);
		}
		else{
			this._item = entityCategoriesRepository.getInstance();
		}
		this.entities=this.entityRepository.getAll();
		this.entityCategories=this.entityCategoryRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.entityCategoriesRepository.close();
		this.entityRepository.close();
		this.entityCategoryRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=entityCategoriesRepository.update(this._item);
		}else{
			succes=entityCategoriesRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}