package com.delivery2go.base.dishimages;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.models.DishImages;

public class ViewModelDishImagesDetails extends DataViewModel implements IClosable {

	public static final int DISH = 1;
	public static final int IMAGE = 2;

	protected IDishImagesRepository dishImagesRepository;
	protected DishImages _item;
	int[] _itemId;
	public DishImages getItem(){ return this._item;}


	public Command NavigateToDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(DISH, null, _item.DishId);
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

	public ViewModelDishImagesDetails(IDataView view, IDishImagesRepository dishImagesRepository, int[] id){
		super(view);

		this.dishImagesRepository=dishImagesRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishImagesRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishImagesRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishImagesRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}