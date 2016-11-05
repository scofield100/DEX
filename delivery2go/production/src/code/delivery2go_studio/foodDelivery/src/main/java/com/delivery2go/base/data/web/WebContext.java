package com.delivery2go.base.data.web;
import android.content.Context;

import com.enterlib.conetivity.WebClientContext;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.*;


public class WebContext extends WebClientContext {

	public WebContext(Context context, String baseUrl, String sessionId) {
		super(context, baseUrl, sessionId);
		init();
	}

	private void init() {
		//TODO Performs initialization here
		//registerInitializer(EntityCategory.class, new com.delivery2go.base.data.repository.EntityCategoryFactory());
		//registerInitializer(State.class, new com.delivery2go.base.data.repository.StateFactory());
		//registerInitializer(EntityImages.class, new com.delivery2go.base.data.repository.EntityImagesFactory());
		//registerInitializer(EntityCategories.class, new com.delivery2go.base.data.repository.EntityCategoriesFactory());
		//registerInitializer(DishCategories.class, new com.delivery2go.base.data.repository.DishCategoriesFactory());
		//registerInitializer(DishImages.class, new com.delivery2go.base.data.repository.DishImagesFactory());
		//registerInitializer(City.class, new com.delivery2go.base.data.repository.CityFactory());
		//registerInitializer(ClientDishFavorites.class, new com.delivery2go.base.data.repository.ClientDishFavoritesFactory());
		//registerInitializer(ClientEntityFavorites.class, new com.delivery2go.base.data.repository.ClientEntityFavoritesFactory());
		//registerInitializer(OrderState.class, new com.delivery2go.base.data.repository.OrderStateFactory());
		//registerInitializer(OrderType.class, new com.delivery2go.base.data.repository.OrderTypeFactory());
		//registerInitializer(DishSize.class, new com.delivery2go.base.data.repository.DishSizeFactory());
		//registerInitializer(Roll.class, new com.delivery2go.base.data.repository.RollFactory());
		//registerInitializer(DishCategory.class, new com.delivery2go.base.data.repository.DishCategoryFactory());
		//registerInitializer(DishSizePrice.class, new com.delivery2go.base.data.repository.DishSizePriceFactory());
		//registerInitializer(Image.class, new com.delivery2go.base.data.repository.ImageFactory());
		//registerInitializer(OrderDish.class, new com.delivery2go.base.data.repository.OrderDishFactory());
		//registerInitializer(OrderDishAddons.class, new com.delivery2go.base.data.repository.OrderDishAddonsFactory());
		//registerInitializer(Permission.class, new com.delivery2go.base.data.repository.PermissionFactory());
		//registerInitializer(User.class, new com.delivery2go.base.data.repository.UserFactory());
		//registerInitializer(UserEntities.class, new com.delivery2go.base.data.repository.UserEntitiesFactory());
		//registerInitializer(Entity.class, new com.delivery2go.base.data.repository.EntityFactory());
		//registerInitializer(Addons.class, new com.delivery2go.base.data.repository.AddonsFactory());
		//registerInitializer(EntityMenu.class, new com.delivery2go.base.data.repository.EntityMenuFactory());
		//registerInitializer(EntityReview.class, new com.delivery2go.base.data.repository.EntityReviewFactory());
		//registerInitializer(DishReview.class, new com.delivery2go.base.data.repository.DishReviewFactory());
		//registerInitializer(Dish.class, new com.delivery2go.base.data.repository.DishFactory());
		//registerInitializer(DeliveryAgent.class, new com.delivery2go.base.data.repository.DeliveryAgentFactory());
		//registerInitializer(DeliveryTarif.class, new com.delivery2go.base.data.repository.DeliveryTarifFactory());
		//registerInitializer(Order.class, new com.delivery2go.base.data.repository.OrderFactory());
		//registerInitializer(Client.class, new com.delivery2go.base.data.repository.ClientFactory());
	}

}
