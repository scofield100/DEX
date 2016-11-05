package com.delivery2go.base.entityreview;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IEntityReviewRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.EntityReview;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;

public class ViewModelEntityReviewEdit extends EditViewModel implements IClosable {

	protected IEntityReviewRepository entityReviewRepository;

	int entityId;
	protected EntityReview _item;

	public EntityReview getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}


	protected IClientRepository clientRepository;
	protected ArrayList<Client> clients;
	public List<Client> getClients(){ return clients; }

	protected IEntityRepository entityRepository;
	protected ArrayList<Entity> entities;
	public List<Entity> getEntities(){ return entities; }

	public ViewModelEntityReviewEdit(BindableEditFragment view, IEntityReviewRepository entityReviewRepository){
		super(view);

		this.entityReviewRepository=entityReviewRepository;

		this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		this.entityRepository=RepositoryManager.getInstance().getIEntityRepository();

		entityId = view.getActivity().getIntent().getIntExtra(ActivityEntityDetails.ID, 0);
	}

	@Override
	protected boolean loadAsync() throws Exception{
		Client client = DeliveryApp.getServerClient();
		_item = entityReviewRepository.getFirst(String.format("ClientId=%d and EntityId=%d", client.Id, entityId));
		if(_item == null){
			this._item = entityReviewRepository.getInstance();
			_item.ClientId = client.Id;
			_item.EntityId= entityId;
		}

		return true;
	}



	@Override
	public void close() {
		this.entityReviewRepository.close();
		this.clientRepository.close();
		this.entityRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
		_item.UpdateDate = new Date();

		Entity entity = entityRepository.get(entityId);

		if(_item.Id > 0){
			succes=entityReviewRepository.update(this._item);
		}else{

			entity.ReviewCount++;
			succes=entityReviewRepository.create(this._item) > 0;
		}

		if(!succes)throw new InvalidOperationException("Error");

		computeAvgRating(entity);

		return true;
	}

	private void computeAvgRating(Entity entity){
		int rating = _item.Rating;
		int count = 1;
		for(EntityReview review : entity.getEntityReviews()){
			if(review.Id != _item.Id) {
				rating += review.Rating;
				count++;
			}
		}
		rating /=count;
		entity.Ranking = rating;
		entityRepository.update(entity);

		entity.getEntityReviews().close();
	}


}