package com.delivery2go.base.entitycategories;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;
import com.delivery2go.base.models.EntityCategories;

public class ViewModelEntityCategoriesDetails extends DataViewModel implements IClosable {

	public static final int ENTITY = 1;
	public static final int CATEGORY = 2;

	protected IEntityCategoriesRepository entityCategoriesRepository;
	protected EntityCategories _item;
	int[] _itemId;
	public EntityCategories getItem(){ return this._item;}


	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public Command NavigateToCategory= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CATEGORY, null, _item.CategoryId);
			}
		}
	};

	public ViewModelEntityCategoriesDetails(IDataView view, IEntityCategoriesRepository entityCategoriesRepository, int[] id){
		super(view);

		this.entityCategoriesRepository=entityCategoriesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityCategoriesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.entityCategoriesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityCategoriesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}