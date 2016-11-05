package com.delivery2go.base.city;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.City;

import com.delivery2go.base.models.State;
import com.delivery2go.base.data.repository.IStateRepository;

public class ViewModelCityList extends EntityCursorViewModel<City>{

	protected ICityRepository cityRepository;


	//protected IStateRepository stateRepository;
	//protected ArrayList<State> statesOptional;
	//public List<State> getStatesOptional(){ return statesOptional; }

	public ViewModelCityList (IDataView view, ICityRepository cityRepository) {
		super(view);
		
		this.cityRepository = cityRepository;   

		//this.stateRepository=RepositoryManager.getInstance().getIStateRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.statesOptional=BaseModel.asOptionalList(State.class, stateRepository.getAll(), new State());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<City> createCursor()
			throws InvalidOperationException {		
		return this.cityRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.cityRepository.close();
		//this.stateRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(cityRepository instanceof IAttachRepository<?>){
			IAttachRepository<City>attachedRep = (IAttachRepository<City>) cityRepository;
			if(!attachedRep.attach((City) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			City obj = (City) items.get(i);
			cityRepository.delete(obj);
		}
		return true;
	}



}