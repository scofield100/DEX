package com.delivery2go.base.city;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.models.City;

public class ViewModelCityDetails extends DataViewModel implements IClosable {

	public static final int STATE = 1;

	protected ICityRepository cityRepository;
	protected City _item;
	int[] _itemId;
	public City getItem(){ return this._item;}


	public Command NavigateToState= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(STATE, null, _item.StateId);
			}
		}
	};

	public ViewModelCityDetails(IDataView view, ICityRepository cityRepository, int[] id){
		super(view);

		this.cityRepository=cityRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = cityRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.cityRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=cityRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}