package com.delivery2go.base.entityimages;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityImagesRepository;
import com.delivery2go.base.models.EntityImages;

public class ViewModelEntityImagesDetails extends DataViewModel implements IClosable {

	public static final int ENTITY = 1;
	public static final int IMAGE = 2;

	protected IEntityImagesRepository entityImagesRepository;
	protected EntityImages _item;
	int[] _itemId;
	public EntityImages getItem(){ return this._item;}


	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public Command NavigateToImage= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(IMAGE, null, _item.ImageId);
			}
		}
	};

	public ViewModelEntityImagesDetails(IDataView view, IEntityImagesRepository entityImagesRepository, int[] id){
		super(view);

		this.entityImagesRepository=entityImagesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityImagesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.entityImagesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityImagesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}