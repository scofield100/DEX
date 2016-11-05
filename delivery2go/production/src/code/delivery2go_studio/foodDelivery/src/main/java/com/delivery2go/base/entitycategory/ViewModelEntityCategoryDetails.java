package com.delivery2go.base.entitycategory;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;
import com.delivery2go.base.models.EntityCategory;

public class ViewModelEntityCategoryDetails extends DataViewModel implements IClosable {

	protected IEntityCategoryRepository entityCategoryRepository;
	protected EntityCategory _item;
	int[] _itemId;
	public EntityCategory getItem(){ return this._item;}

	public ViewModelEntityCategoryDetails(IDataView view, IEntityCategoryRepository entityCategoryRepository, int[] id){
		super(view);

		this.entityCategoryRepository=entityCategoryRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityCategoryRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.entityCategoryRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityCategoryRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}