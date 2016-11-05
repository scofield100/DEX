package com.delivery2go.base.addons;

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
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Addons;

import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;

public class ViewModelAddonsEdit extends EditViewModel implements IClosable {

	private int entityId;
	protected IAddonsRepository addonsRepository;

	protected Addons _item;
	protected int[] _itemId;
	public Addons getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	public ViewModelAddonsEdit(BindableEditFragment view, IAddonsRepository addonsRepository, int[] id){
		super(view);

		this.addonsRepository=addonsRepository;
		this._itemId=id;

		int[] entityIds = (int[]) view.getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);
		if(entityIds!=null){
			this.entityId = entityIds[0];
		}

	}

	@Override
	protected boolean loadAsync() throws Exception{
		if(_itemId!=null && _itemId.length > 0){
			this._item = addonsRepository.get(_itemId);
		}
		else{
			this._item = addonsRepository.getInstance();
		}

		return true;
	}



	@Override
	public void close() {
		this.addonsRepository.close();
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
			succes=addonsRepository.update(this._item);
		}else{
            _item.CreateDate = _item.UpdateDate;
			succes=addonsRepository.create(this._item) > 0;
		}
		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}