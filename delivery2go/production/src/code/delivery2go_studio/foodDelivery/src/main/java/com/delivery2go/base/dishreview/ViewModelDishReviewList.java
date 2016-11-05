package com.delivery2go.base.dishreview;

import java.util.List;
import java.util.ArrayList;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.mvvm.IDataView;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishReview;

import com.delivery2go.base.models.Client;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;

public class ViewModelDishReviewList extends EntityCursorViewModel<DishReview>{

	protected IDishReviewRepository dishReviewRepository;


	//protected IClientRepository clientRepository;
	//protected ArrayList<Client> clientsOptional;
	//public List<Client> getClientsOptional(){ return clientsOptional; }

	//protected IDishRepository dishRepository;
	//protected ArrayList<Dish> dishsOptional;
	//public List<Dish> getDishsOptional(){ return dishsOptional; }

	public ViewModelDishReviewList (IDataView view, IDishReviewRepository dishReviewRepository) {
		super(view);
		
		this.dishReviewRepository = dishReviewRepository;   

		//this.clientRepository=RepositoryManager.getInstance().getIClientRepository();
		//this.dishRepository=RepositoryManager.getInstance().getIDishRepository();
    
	}
	
	@Override
	protected boolean loadAsync() throws Exception {               

		//this.clientsOptional=BaseModel.asOptionalList(Client.class, clientRepository.getAll(), new Client());
		//this.dishsOptional=BaseModel.asOptionalList(Dish.class, dishRepository.getAll(), new Dish());


		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<DishReview> createCursor()
			throws InvalidOperationException {		
		return this.dishReviewRepository.getCursor();			
    }

	@Override
	public void close() {
		super.close();

		this.dishReviewRepository.close();
		//this.clientRepository.close();
		//this.dishRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(dishReviewRepository instanceof IAttachRepository<?>){
			IAttachRepository<DishReview>attachedRep = (IAttachRepository<DishReview>) dishReviewRepository;
			if(!attachedRep.attach((DishReview) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			DishReview obj = (DishReview) items.get(i);
			dishReviewRepository.delete(obj);
		}
		return true;
	}



}