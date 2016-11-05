package com.delivery2go.base.image;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.Image;

public class ViewModelImageDetails extends DataViewModel implements IClosable {

	public static final int UPDATEUSER = 1;

	protected IImageRepository imageRepository;
	protected Image _item;
	int[] _itemId;
	public Image getItem(){ return this._item;}


	public Command NavigateToUpdateUser= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null && _item.UpdateUserId!=null){
				getNavigator().navigateTo(UPDATEUSER, null, _item.UpdateUserId);
			}
		}
	};

	public ViewModelImageDetails(IDataView view, IImageRepository imageRepository, int[] id){
		super(view);

		this.imageRepository=imageRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = imageRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.imageRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=imageRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}