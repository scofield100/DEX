package com.delivery2go.base.dish;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;

import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelDishEdit extends EditViewModel implements IClosable {

    private int entityId;
    protected IDishRepository dishRepository;

	protected Dish _item;
	protected int[] _itemId;
	public Dish getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IImageRepository imageRepository;
	protected ArrayList<Image> imagesOptional;
	public List<Image> getImagesOptional(){ return imagesOptional; }


	protected IEntityMenuRepository entityMenuRepository;
	protected ArrayList<EntityMenu> entityMenusOptional;
	public List<EntityMenu> getEntityMenusOptional(){ return entityMenusOptional; }


	public ViewModelDishEdit(BindableEditFragment view, IDishRepository dishRepository, int[] id){
		super(view);

		this.dishRepository=dishRepository;
		this._itemId=id;
		this.imageRepository=RepositoryManager.getInstance().getIImageRepository();
		this.entityMenuRepository=RepositoryManager.getInstance().getIEntityMenuRepository();

        int[] entityIds = (int[]) view.getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);
        if(entityIds!=null){
            this.entityId = entityIds[0];
        }
	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = dishRepository.get(_itemId);

			ArrayList<Image> images = _item.getImages().toList();
			this.imagesOptional=BaseModel.asOptionalList(Image.class, images, null);
		}
		else{
			this._item = dishRepository.getInstance();
            this.imagesOptional = new ArrayList<>();
		}

		this.entityMenusOptional=BaseModel.asOptionalList(EntityMenu.class, entityMenuRepository.getAll(String.format("EntityId=%d", entityId), -1, -1), new EntityMenu());

		return true;
	}



	@Override
	public void close() {
		this.dishRepository.close();
		this.imageRepository.close();
		this.entityMenuRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
        _item.UpdateDate = new Date();

        if(entityId > 0)
            _item.EntityId = entityId;

        if(DeliveryApp.getUser()!=null)
            _item.UpdateUserId = DeliveryApp.getUser().Id;

		if(_itemId != null &&  _itemId.length > 0){
			succes=dishRepository.update(this._item);
		}else{
            _item.CreateDate = _item.UpdateDate;
			succes=dishRepository.create(this._item) > 0;
		}

		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}