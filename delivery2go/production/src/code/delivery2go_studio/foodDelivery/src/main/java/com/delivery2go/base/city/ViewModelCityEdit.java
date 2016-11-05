package com.delivery2go.base.city;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.City;

import com.delivery2go.base.models.State;
import com.delivery2go.base.data.repository.IStateRepository;

public class ViewModelCityEdit extends EditViewModel implements IClosable {

	protected ICityRepository cityRepository;

	protected City _item;
	protected int[] _itemId;
	public City getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IStateRepository stateRepository;
	protected ArrayList<State> states;
	public List<State> getStates(){ return states; }

	public ViewModelCityEdit(IEditView view, ICityRepository cityRepository, int[] id){
		super(view);

		this.cityRepository=cityRepository;
		this._itemId=id;
		this.stateRepository=RepositoryManager.getInstance().getIStateRepository();

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = cityRepository.get(_itemId);
		}
		else{
			this._item = cityRepository.getInstance();
		}
		this.states=this.stateRepository.getAll();

		return true;
	}



	@Override
	public void close() {
		this.cityRepository.close();
		this.stateRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		if(_itemId != null &&  _itemId.length > 0){
			succes=cityRepository.update(this._item);
		}else{
			succes=cityRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}