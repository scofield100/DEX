package com.delivery2go.base.dishreview;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.EditFragmentView;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishReview;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class ViewModelDishReviewEdit extends EditViewModel implements IClosable {

	private final int dishId;
	protected IDishReviewRepository dishReviewRepository;

	protected DishReview _item;
	public DishReview getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IClientRepository clientRepository;
	protected ArrayList<Client> clients;
	public List<Client> getClients(){ return clients; }

	protected IDishRepository dishRepository;
	protected ArrayList<Dish> dishs;
	public List<Dish> getDishs(){ return dishs; }

	public ViewModelDishReviewEdit(BindableEditFragment view, IDishReviewRepository dishReviewRepository){
		super(view);

		this.dishReviewRepository=dishReviewRepository;
		this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.dishRepository=RepositoryManager.getInstance().getIDishRepository();

		dishId = view.getActivity().getIntent().getIntExtra(ActivityDishDetails.ID, 0);
	}

	@Override
	protected boolean loadAsync() throws Exception{
		Client client = DeliveryApp.getServerClient();
		_item = dishReviewRepository.getFirst(String.format("ClientId=%d and DishId=%d", client.Id, dishId));
		if(_item == null){
			this._item = dishReviewRepository.getInstance();
			_item.ClientId = client.Id;
			_item.DishId= dishId;
		}

		return true;
	}



	@Override
	public void close() {
		this.dishReviewRepository.close();
		this.clientRepository.close();
		this.dishRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		_item.UpdateDate = new Date();

        Dish dish = dishRepository.get(dishId);

		if(_item.Id > 0){
			succes=dishReviewRepository.update(this._item);
		}else{
			if(dish.ReviewCount == null){
				dish.ReviewCount = 0;
			}

            dish.ReviewCount++;
			succes=dishReviewRepository.create(this._item) > 0;
		}

		if(!succes)throw new InvalidOperationException("Error");

        computeAvgRating(dish);

		return true;
	}

    private void computeAvgRating(Dish dish){
        int rating = _item.Rating;
        int count = 1;
        for(DishReview review : dish.getDishReviews()){
            if(review.Id != _item.Id) {
               rating += review.Rating;
                count++;
            }
        }
        rating /=count;
        dish.Ranking = rating;
        dishRepository.update(dish);

        dish.getDishReviews().close();
    }


}