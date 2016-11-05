package com.delivery2go.base.entitymenu;

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
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityMenu;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelEntityMenuEdit extends EditViewModel implements IClosable {

	private int entityId;
	protected IEntityMenuRepository entityMenuRepository;

	protected EntityMenu _item;
	protected int[] _itemId;
	public EntityMenu getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelEntityMenuEdit(BindableEditFragment view, IEntityMenuRepository entityMenuRepository, int[] id){
		super(view);

		this.entityMenuRepository=entityMenuRepository;
		this._itemId=id;

		int[] entityIds = (int[]) view.getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);
		if(entityIds!=null){
			this.entityId = entityIds[0];
		}

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = entityMenuRepository.get(_itemId);
		}
		else{
			this._item = entityMenuRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
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
			succes=entityMenuRepository.update(this._item);
		}else{
			_item.CreateDate = _item.UpdateDate;
			succes=entityMenuRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}