package com.delivery2go.entity;

import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.models.OrderDish;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;

import java.util.ArrayList;

/**
* Created by ansel on 16/08/2016.
*/
public class ViewModelEntityHome extends DataViewModel {

    private final IOrderRepository orderRep;
    private final IOrderDishRepository orderDishRep;
    private IDishCategoryRepository categoryRepo;
    private IEntityRepository entityRep;
    private ArrayList<DishCategory> categories;
    private Entity entity;
    private int entityId;
    private Order currentOrder;
    private ArrayList<OrderDish> currentOrderDishes;

    public ViewModelEntityHome(IDataView view, int entityId) {
        super(view);

        categoryRepo = RepositoryManager.getInstance().getIDishCategoryRepository();
        entityRep  = RepositoryManager.getInstance().getIEntityRepository();
        orderRep = RepositoryManager.getInstance().getIOrderRepository();
        orderDishRep = RepositoryManager.getInstance().getIOrderDishRepository();
        this.entityId = entityId;
    }

    public ArrayList<DishCategory> getCategories() {
        return categories;
    }



    public Entity getEntity() {
        return entity;
    }

    @Override
    protected boolean loadAsync() throws Exception {
        categories =categoryRepo.getAll();
        entity = entityRep.get(entityId);

        //TODO Read Current Order for the entity end the client and order items
        Client client = DeliveryApp.getServerClient();
        currentOrder = orderRep.getFirst(String.format("EntityId=%d and  ClientId=%d and OrderStateId=%d", entityId, client.Id, OrderStateEnum.Open));
        if(currentOrder == null){
            currentOrderDishes = new ArrayList<OrderDish>();
        }else {
            currentOrderDishes = orderDishRep.getAll(String.format("OrderId = %d",currentOrder.OrderId), -1, -1);
        }
        return true;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public ArrayList<OrderDish> getCurrentOrderDishes() {
        return currentOrderDishes;
    }
}
