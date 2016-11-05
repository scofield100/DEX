package com.delivery2go.base.entity;

import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Entity;

import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.City;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.models.State;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelEntityList extends EntityCursorViewModel<Entity>{

    private final IEntityRepository userEntityRepository;
    private final User user;
    protected IEntityRepository entityRepository;


	//protected IImageRepository imageRepository;
	//protected ArrayList<Image> imagesOptional;
	//public List<Image> getImagesOptional(){ return imagesOptional; }

	//protected ICityRepository cityRepository;
	//protected ArrayList<City> citiesOptional;
	//public List<City> getCitiesOptional(){ return citiesOptional; }

	//protected IStateRepository stateRepository;
	//protected ArrayList<State> statesOptional;
	//public List<State> getStatesOptional(){ return statesOptional; }

	protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelEntityList (IDataView view, IEntityRepository entityRepository) {
		super(view);
		
		this.entityRepository = entityRepository;   

		//this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		//this.cityRepository=RepositoryManager.getInstance().getICityRepository();
		//this.stateRepository=RepositoryManager.getInstance().getIStateRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();

        user = DeliveryApp.getUser();
        this.userEntityRepository = RepositoryManager.getInstance().getUserEntities_EntityRepository(user.Id, false);
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.imagesOptional=BaseModel.asOptionalList(Image.class, imageRepository.getAll(), new Image());
		//this.citiesOptional=BaseModel.asOptionalList(City.class, cityRepository.getAll(), new City());
		//this.statesOptional=BaseModel.asOptionalList(State.class, stateRepository.getAll(), new State());
		//this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());


		return super.loadAsync();
	}

	@Override
	public String getCount() {
		return super.getCount();
	}

	@Override
	protected IEntityCursor<Entity> createCursor()
			throws InvalidOperationException {

		if(user.Superadmin)
			return this.entityRepository.getCursor();
		else{
            return userEntityRepository.getCursor();
		}
    }

	@Override
	public void close() {
		super.close();

		this.entityRepository.close();
		//this.imageRepository.close();
		//this.cityRepository.close();
		//this.stateRepository.close();
		this.userRepository.close();
        this.userEntityRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(entityRepository instanceof IAttachRepository<?>){
			IAttachRepository<Entity>attachedRep = (IAttachRepository<Entity>) entityRepository;
			if(!attachedRep.attach((Entity) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Entity obj = (Entity) items.get(i);
			entityRepository.delete(obj);
		}
		return true;
	}



}