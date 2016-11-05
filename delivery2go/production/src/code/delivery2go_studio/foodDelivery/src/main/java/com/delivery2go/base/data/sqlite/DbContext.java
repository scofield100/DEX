package com.delivery2go.base.data.sqlite;

import android.content.Context;

import com.enterlib.data.EntityMap;
import com.enterlib.data.EntityMapContext;
import com.enterlib.data.IEntityMapInitializer;
import com.enterlib.data.IStopWordRepository;
import com.enterlib.data.generics.IFactory;
import com.delivery2go.base.models.*;

public class DbContext extends EntityMapContext {
	public DbContext(Context context, String database, IStopWordRepository stopWordRep) {
		super(context, database, stopWordRep);
		init();
	}

	private void init() {
		//TODO Performs initialization here
		//registerInitializer(EntityCategory.class, new EntityCategoryFactory());
		//registerInitializer(State.class, new StateFactory());
		//registerInitializer(EntityImages.class, new EntityImagesFactory());
		//registerInitializer(EntityCategories.class, new EntityCategoriesFactory());
		//registerInitializer(DishCategories.class, new DishCategoriesFactory());
		//registerInitializer(DishImages.class, new DishImagesFactory());
		//registerInitializer(City.class, new CityFactory());
		//registerInitializer(ClientDishFavorites.class, new ClientDishFavoritesFactory());
		//registerInitializer(ClientEntityFavorites.class, new ClientEntityFavoritesFactory());
		//registerInitializer(Client.class, new ClientFactory());
		//registerInitializer(OrderState.class, new OrderStateFactory());
		//registerInitializer(OrderType.class, new OrderTypeFactory());
		//registerInitializer(DishSize.class, new DishSizeFactory());
		//registerInitializer(Roll.class, new RollFactory());
		//registerInitializer(DishCategory.class, new DishCategoryFactory());
		//registerInitializer(DishSizePrice.class, new DishSizePriceFactory());
		//registerInitializer(Image.class, new ImageFactory());
		//registerInitializer(Order.class, new OrderFactory());
		//registerInitializer(OrderDish.class, new OrderDishFactory());
		//registerInitializer(OrderDishAddons.class, new OrderDishAddonsFactory());
		//registerInitializer(Permission.class, new PermissionFactory());
		//registerInitializer(User.class, new UserFactory());
		//registerInitializer(UserEntities.class, new UserEntitiesFactory());
		//registerInitializer(Entity.class, new EntityFactory());
		//registerInitializer(Addons.class, new AddonsFactory());
		//registerInitializer(EntityMenu.class, new EntityMenuFactory());
		//registerInitializer(EntityReview.class, new EntityReviewFactory());
		//registerInitializer(DishReview.class, new DishReviewFactory());
		//registerInitializer(Dish.class, new DishFactory());
		//registerInitializer(DeliveryAgent.class, new DeliveryAgentFactory());
		//registerInitializer(DeliveryTarif.class, new DeliveryTarifFactory());
	}

}

