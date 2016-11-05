package com.delivery2go.base.entityreview;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityReviewRepository;
import com.delivery2go.base.models.EntityReview;

public class ViewModelEntityReviewDetails extends DataViewModel implements IClosable {

	public static final int CLIENT = 1;
	public static final int ENTITY = 2;

	protected IEntityReviewRepository entityReviewRepository;
	protected EntityReview _item;
	int[] _itemId;
	public EntityReview getItem(){ return this._item;}


	public Command NavigateToClient= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CLIENT, null, _item.ClientId);
			}
		}
	};

	public Command NavigateToEntity= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(ENTITY, null, _item.EntityId);
			}
		}
	};

	public ViewModelEntityReviewDetails(IDataView view, IEntityReviewRepository entityReviewRepository, int[] id){
		super(view);

		this.entityReviewRepository=entityReviewRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityReviewRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.entityReviewRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityReviewRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}