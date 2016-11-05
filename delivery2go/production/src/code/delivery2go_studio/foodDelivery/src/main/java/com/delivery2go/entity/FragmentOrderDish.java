package com.delivery2go.entity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.OrderTypeEnum;
import com.delivery2go.R;
import com.delivery2go.RantingConverter;
import com.delivery2go.base.data.repository.*;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.dishreview.ActivityDishReviewEdit;
import com.delivery2go.base.dishreview.FragmentDishReviewEdit;
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.models.DishSize;
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.FragmentDishDetails;
import com.delivery2go.base.dish.ViewModelDishDetails;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.models.OrderDishAddons;
import com.delivery2go.base.orderdish.ActivityOrderDishDetails;
import com.enterlib.DateUtils;
import com.enterlib.data.BaseModel;
import com.enterlib.data.EntityTracker;
import com.enterlib.data.IEntityCollection;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IModelStateObserver;
import com.enterlib.data.IdNameValue;
import com.enterlib.data.ModelStateManager;
import com.enterlib.databinding.BindingResources;
import com.enterlib.exceptions.BusinessException;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IAsyncLoadOperation;
import com.enterlib.mvvm.IDataView;

public class FragmentOrderDish extends FragmentDishDetails {

    private View commentsPanel;
    private View contentPanel;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.entity_fragment_order_dish, container, false);
		commentsPanel = view.findViewById(R.id.commentsPanel);
        contentPanel = view.findViewById(R.id.contentPanel);

		ImageView btnClose = (ImageView) view.findViewById(R.id.btnClose);
		ImageView btnShare= (ImageView) view.findViewById(R.id.btnShare);
		
		btnClose.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
			}
		});
		
		btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnAddCart = (Button) view.findViewById(R.id.footerPanel);
		btnAddCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                ViewModelOrderDish viewModel = (ViewModelOrderDish) getViewModel();
				viewModel.addToCart();
			}
		});

        ImageView btnComments = (ImageView)view.findViewById(R.id.btnReviews);
        btnComments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commentsPanel.getVisibility() == View.GONE){
                    Animation enter = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_enter);
                    commentsPanel.startAnimation(enter);
                    commentsPanel.setVisibility(View.VISIBLE);
                }else{
                    Animation exit = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_exit);
                    commentsPanel.startAnimation(exit);
                    exit.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            commentsPanel.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        ImageView btnLike = (ImageView) view.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ActivityDishReviewEdit.class);

                int[]dishid = (int[]) getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID);
                i.putExtra(ActivityDishDetails.ID, dishid[0]);
                startActivityForResult(i, FragmentDishReviewEdit.DISHREVIEW_EDITED);
            }
        });
		
		return view;
	}
	
	@Override
	protected BindingResources getBindingResources() {
		return super.getBindingResources()
                .put("DishSizeComparer", new com.delivery2go.base.dishsizeprice.DishSizePriceComparer())
                .put("AddonsComparer", new com.delivery2go.base.addons.AddonsComparer())
                ;
	}
	
	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelOrderDish(this, RepositoryManager.getInstance().getIDishRepository(), 
				(int[]) getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID));
	}

    @Override
    public void navigateTo(int requestCode, Bundle extras, Object data) {
        switch (requestCode){
            case ViewModelOrderDish.ADD_TO_CART_COMPLETED:
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FragmentDishReviewEdit.DISHREVIEW_EDITED && resultCode == FragmentDishReviewEdit.DISHREVIEW_EDITED){
            load();
            getActivity().setResult(FragmentDishEdit.DISH_EDITED);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ViewModelOrderDish extends ViewModelDishDetails{

        private static final int ADD_TO_CART_COMPLETED = 488794;

        private final IOrderRepository orderRep;
        private final IOrderDishRepository orderDishRep;
        private final IDishSizePriceRepository sizeRep;
        private final IClientDishFavoritesRepository favRep;
        private ArrayList<Addons> extras = new  ArrayList<Addons>();
		private Integer quantity = 1;		
		
		private EntityTracker<Addons> tracker;
		private IAddonsRepository addonsRep;
        private ClientDishFavorites fav;

        private int orderDishId;
        private OrderDish orderDish;
        private Client client;

        public ViewModelOrderDish(IDataView view,
				IDishRepository dishRepository, int[] id) {
			super(view, dishRepository, id);	
			

			tracker = new EntityTracker<Addons>(Addons.class);
			addonsRep = RepositoryManager.getInstance().getIAddonsRepository();
            orderRep = RepositoryManager.getInstance().getIOrderRepository();
            orderDishRep = RepositoryManager.getInstance().getIOrderDishRepository();
            sizeRep = RepositoryManager.getInstance().getIDishSizePriceRepository();
            favRep = RepositoryManager.getInstance().getIClientDishFavoritesRepository();

            this.orderDishId = getActivity().getIntent().getIntExtra(ActivityOrderDishDetails.ID, 0);
		}

		public Integer SizeId;
		
		public ArrayList<DishSizePrice> Sizes;

		public Integer DressingId;
		
		public ArrayList<Addons> Dressings;
			
		public IEntityCursor<Addons> AvailableExtras;
		
		public Integer getQuantity(){
			return quantity;
		}
		
		public void setQuantity(Integer value){
			quantity = value;
		}

		public ArrayList<Addons> getExtras(){
			return extras;
		}		
		
		public IModelStateObserver getStateObserver(){
			return tracker;
		}

        public Integer getAvailableCount(){
            if(_item == null)
                return  null;
            return  _item.AvailableCount;
        }

        public String getOperationText(){
            if(orderDishId > 0){
                return getString(R.string.update_order);
            }
            return getString(R.string.add_to_cart);
        }
		
		@Override
		protected boolean loadAsync() throws Exception {	
			super.loadAsync();

			//availableExtras = addonsRep.getAll(String.format("IsAvailable = true and EntityId=%d", getItem().EntityId), -1, -1);
			AvailableExtras = addonsRep.getCursor(String.format("IsAvailable = true and (AvailableCount = null or AvailableCount > 0) and EntityId=%d", _item.EntityId));
			Dressings = BaseModel.asOptionalList(Addons.class, addonsRep.getAll(String.format("IsAvailable = true and (AvailableCount = null or AvailableCount > 0) and IsDressing = true and EntityId=%d", _item.EntityId), -1, -1), null);
			Sizes = BaseModel.asOptionalList(DishSizePrice.class, sizeRep.getAll(String.format("DishId = %d and (AvailableCount = null or AvailableCount > 0)", _item.Id), -1, -1), null);

            if(orderDishId > 0){
                orderDish = orderDishRep.get(orderDishId);
                SizeId = orderDish.SizeId;
                quantity = orderDish.Quantity;
                DressingId = orderDish.DressingId;

                IEntityCollection<OrderDishAddons> dishAddons = orderDish.getOrderDishAddonses();
                extras = new ArrayList<>(dishAddons.getCount());
                for (OrderDishAddons item : dishAddons ){
                    Addons addon = new Addons();
                    addon.Id = item.AddonId;
                    addon.Name = item.AddonName;
                    addon.Price = item.AddonPrice;
                    extras.add(addon);
                }

                dishAddons.close();
            }

            Client client = DeliveryApp.getServerClient();

            if(_item!=null){
                fav = favRep.getFirst(String.format("ClientId=%d && DishId=%d", client.Id, _item.Id));
                _item.getDishReviews().load();
            }

            tracker.reset(extras);

			return true;
		}

        public void addToCart() {
            if(!updateSources())
                return;
            doLoadOperationAsync(new IAsyncLoadOperation() {

                @Override
                public boolean loadAsync() throws Exception {
                    //validate quantity
                    if(quantity == null|| quantity <= 0)
                        throw new ValidationException("Quantity", "Must be greater than 0");

                    //reload the dish
                    _item = dishRepository.get(_item.Id);
                    Entity entity = _item.getEntity();
                    Date currentDate = new Date();

                    boolean isOpened = false;
                    Calendar currentCalendar = Calendar.getInstance();
                    Calendar opening = Calendar.getInstance();
                    Calendar closed = Calendar.getInstance();

                    if(entity.OpeningTime != null){
                        opening.setTime(DateUtils.getDateTime(currentDate, entity.OpeningTime));
                    }else {
                        opening.set(Calendar.HOUR_OF_DAY, 0);
                        opening.set(Calendar.MINUTE, 0);
                        opening.set(Calendar.SECOND, 0);
                    }

                    if(entity.CloseTime != null){
                        closed.setTime(DateUtils.getDateTime(currentDate, entity.CloseTime));
                    }else {
                        closed.set(Calendar.HOUR_OF_DAY, 0);
                        closed.set(Calendar.MINUTE, 0);
                        closed.set(Calendar.SECOND, 0);
                    }

                    if(opening.compareTo(closed) >= 0){
                        if(currentCalendar.compareTo(opening)>=0)
                            closed.add(Calendar.DAY_OF_MONTH, 1);
                        else
                            opening.add(Calendar.DAY_OF_MONTH, -1);
                    }

                    if(currentCalendar.compareTo(opening) < 0 || currentCalendar.compareTo(closed) > 0){
                        throw new BusinessException("Sorry the restaurant is not receiving orders at this time");
                    }

                    if(!_item.IsAvailable || (_item.AvailableCount!=null && _item.AvailableCount <= 0)){
                        throw  new BusinessException("Sorry the dish is not available at this time");
                    }

                    if(_item.AvailableCount != null){
                        //the dish has stocks limits. If AvaliableCount is null is it asummed the dish has
                        //unlimeted stocks

                        int count = Math.max(0, _item.AvailableCount - quantity);
                        if(count == 0){
                            throw new ValidationException("Quantity", "Sorry we cannot dispatch the quantity");
                        }
                    }


                    //get the open order for this client to the dish restaurant.
                    // If there are any order open then create one in the open state and
                    //attach an OrderDish with its corresponding OrderDishAddons
                    client = DeliveryApp.getServerClient();

                    Dish dish = getItem();
                    Order openOrder = orderRep.getFirst(String.format("EntityId=%d and ClientId=%d and OrderStateId=%d", dish.EntityId, client.Id , OrderStateEnum.Open));
                    if(openOrder == null){
                        //create a new order
                        openOrder = RepositoryManager.getInstance().getEntityContext().getInstance(Order.class);
                        openOrder.ClientId = client.Id;
                        openOrder.Code = UUID.randomUUID().toString();
                        openOrder.CreateDate = currentDate;
                        openOrder.EntityId= dish.EntityId;
                        openOrder.OrderStateId = OrderStateEnum.Open;
                        openOrder.OrderTypeId = OrderTypeEnum.Delivery;
                        openOrder.UpdateDate = currentDate;
                        openOrder.Phone = client.Mobile;

                        if(entity.DeliveryTimeMax!=null)
                            openOrder.DeliveryTimeMinutes = (int)(double)entity.DeliveryTimeMax;

                        orderRep.create(openOrder);
                    }

                    if(orderDishId == 0) {
                       orderDish = RepositoryManager.getInstance().getEntityContext().getInstance(OrderDish.class);
                    }

                    orderDish.DishId = dish.Id;
                    orderDish.OrderId = openOrder.OrderId;
                    orderDish.SizeId =SizeId;
                    orderDish.DressingId = DressingId;
                    orderDish.Quantity =quantity;
                    orderDish.UpdateDate =currentDate;
                    orderDish.DishPrice = dish.Price;

                    orderDish.SubTotal = dish.Price * quantity;

                    //compute subtotal
                    if(DressingId!=null){
                        Addons dressing = addonsRep.get(DressingId);
                        orderDish.DressingPrice = dressing.Price;
                        orderDish.SubTotal += dressing.Price * quantity;
                    }else{
                        orderDish.DressingPrice  = null;
                    }

                    if(SizeId !=null){
                        DishSizePrice sizePrice = sizeRep.get(SizeId);
                        orderDish.SubTotal += sizePrice.ExtraPrice * quantity;
                    }

                    if(orderDishId == 0) {
                        //creates the orderDish
                        openOrder.getOrderDishs().add(orderDish);
                    }

                    IOrderDishAddonsRepository dishAddonsRep = RepositoryManager.getInstance().getIOrderDishAddonsRepository();
                    for (int i = 0; i < tracker.getCount(); i++) {
                        EntityTracker.EntityState<Addons> state = tracker.getEntityState(i);
                        Addons addon = state.Entity;
                        if(state.State == EntityTracker.ADDED){
                            OrderDishAddons rel = new OrderDishAddons();
                            rel.AddonId = addon.Id;
                            rel.AddonPrice = addon.Price;
                            rel.UpdateDate = currentDate;
                            rel.OrderDishId = orderDish.Id;

                            dishAddonsRep.create(rel);
                            orderDish.SubTotal+=addon.Price * quantity;

                        }else if(state.State == EntityTracker.REMOVED){
                            OrderDishAddons rel = new OrderDishAddons();
                            rel.AddonId = addon.Id;
                            rel.OrderDishId = orderDish.Id;
                            dishAddonsRep.delete(rel);
                        }else if(state.State == EntityTracker.UNCHANGED) {
                            orderDish.SubTotal+=addon.Price * quantity;
                        }
                    }

                    orderDishRep.update(orderDish);

                    updateOrder(openOrder);

                    return true;
                }

                @Override
                public void onDataLoaded() {
                    getNavigator().navigateTo(ADD_TO_CART_COMPLETED, null, null);
                }
            });
        }


        private void updateOrder(Order openOrder){
            Dish dish = getItem();
            Entity entity = dish.getEntity();
            if(entity.DeliveryPrice!=null)
                openOrder.DeliveryCharge = entity.DeliveryPrice;
            else
                openOrder.DeliveryCharge = 0;

            double totalAmount = 0;
            for (OrderDish item : openOrder.getOrderDishs()){
                totalAmount+=item.SubTotal;
            }
            openOrder.getOrderDishs().close();

            openOrder.TaxAmount = 0;
            openOrder.TotalAmount = totalAmount;

            orderRep.update(openOrder);
        }

        public Drawable getFavoriteImage(){
            if(fav != null)
                return getResources().getDrawable(R.drawable.ic_action_star_10);
            return getResources().getDrawable(R.drawable.ic_action_star_disable);
        }

        public Command UpdateFavorite = new Command() {
            @Override
            public void invoke(Object invocator, Object args) {
             doLoadOperationAsync(new IAsyncLoadOperation() {
                 @Override
                 public boolean loadAsync() throws Exception {
                     if(fav == null ){
                         Client client = DeliveryApp.getClient();
                         fav = new ClientDishFavorites();
                         fav.ClientId = client.Id;
                         fav.DishId = _item.Id;
                         favRep.create(fav);
                     }else if(favRep.delete(fav)){
                         fav = null;
                     }

                     return true;
                 }

                 @Override
                 public void onDataLoaded() {
                     onPropertyChange("FavoriteImage");
                     getActivity().setResult(Activity.RESULT_OK);
                 }
             });
            }
        };
    }

}
