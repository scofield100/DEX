package com.delivery2go.base.dishreview;

import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.models.DishReview;

public class ViewModelDishReviewDetails extends DataViewModel implements IClosable {

	public static final int CLIENT = 1;
	public static final int DISH = 2;

	protected IDishReviewRepository dishReviewRepository;
	protected DishReview _item;
	int[] _itemId;
	public DishReview getItem(){ return this._item;}


	public Command NavigateToClient= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(CLIENT, null, _item.ClientId);
			}
		}
	};

	public Command NavigateToDish= new Command(){
		@Override
		public void invoke(Object invocator, Object args){
			if(getNavigator()!=null){
				getNavigator().navigateTo(DISH, null, _item.DishId);
			}
		}
	};

	public ViewModelDishReviewDetails(IDataView view, IDishReviewRepository dishReviewRepository, int[] id){
		super(view);

		this.dishReviewRepository=dishReviewRepository;
		this._itemId=id;

	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = dishReviewRepository.get(_itemId);
		return true;
	}



	@Override
	public void close() {
		this.dishReviewRepository.close();
	}
	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=dishReviewRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}



}