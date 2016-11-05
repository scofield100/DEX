package com.delivery2go.base.entitycategory;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityCategory;

public class ViewModelEntityCategoryEdit extends EditViewModel implements IClosable {

	protected IEntityCategoryRepository entityCategoryRepository;

	protected EntityCategory _item;
	protected int[] _itemId;
	public EntityCategory getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelEntityCategoryEdit(IEditView view, IEntityCategoryRepository entityCategoryRepository, int[] id){
		super(view);

		this.entityCategoryRepository=entityCategoryRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = entityCategoryRepository.get(_itemId);
		}
		else{
			this._item = entityCategoryRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.entityCategoryRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=entityCategoryRepository.update(this._item);
		}else{
			succes=entityCategoryRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}