package com.delivery2go.base.entity;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;
import com.delivery2go.base.models.UserEntities;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
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

public class ViewModelEntityEdit extends EditViewModel implements IClosable {

	private final IUserEntitiesRepository entityuserRepository;
	protected IEntityRepository entityRepository;

	protected Entity _item;
	protected int[] _itemId;
	public Entity getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IImageRepository imageRepository;
	protected ArrayList<Image> imagesOptional;
	public List<Image> getImagesOptional(){ return imagesOptional; }

	protected ICityRepository cityRepository;
	protected ArrayList<City> citiesOptional;
	public List<City> getCitiesOptional(){ return citiesOptional; }

	protected IStateRepository stateRepository;
	protected ArrayList<State> statesOptional;
	public List<State> getStatesOptional(){ return statesOptional; }

	protected IUserRepository userRepository;
	protected ArrayList<User> usersOptional;
	public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelEntityEdit(BindableEditFragment view, IEntityRepository entityRepository, int[] id){
		super(view);

		this.entityRepository=entityRepository;
		this._itemId=id;
		this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		this.cityRepository=RepositoryManager.getInstance().getICityRepository();
		this.stateRepository=RepositoryManager.getInstance().getIStateRepository();
		this.userRepository=RepositoryManager.getInstance().getIUserRepository();
		this.entityuserRepository = RepositoryManager.getInstance().getIUserEntitiesRepository();


	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = entityRepository.get(_itemId);
			ArrayList<Image> images = _item.getImages().toList();
			this.imagesOptional=BaseModel.asOptionalList(Image.class, images, null);
		}
		else{
			this._item = entityRepository.getInstance();
            this.imagesOptional = new ArrayList<>();
            imagesOptional.add(new Image());
		}

		this.citiesOptional=BaseModel.asOptionalList(City.class, cityRepository.getAll(), new City());
		this.statesOptional=BaseModel.asOptionalList(State.class, stateRepository.getAll(), new State());
		this.usersOptional=BaseModel.asOptionalList(User.class, userRepository.getAll(), new User());

		return true;
	}



	@Override
	public void close() {
		this.entityRepository.close();
		this.imageRepository.close();
		this.cityRepository.close();
		this.stateRepository.close();
		this.userRepository.close();
		this.entityuserRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;

        _item.UpdateUserId = DeliveryApp.getUser().Id;

        Date current = new Date();
        _item.UpdateDate = current;

		if(_itemId != null &&  _itemId.length > 0){
			succes=entityRepository.update(this._item);
		}else{
            _item.CreateDate = current;
			succes=entityRepository.create(this._item) > 0;

			if(succes) {
				//link to current user
				User user = DeliveryApp.getUser();
				if (user != null) {
					UserEntities rel = new UserEntities();
					rel.EntityId = _item.Id;
					rel.UserId = user.Id;
					entityuserRepository.create(rel);
				}
			}
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}