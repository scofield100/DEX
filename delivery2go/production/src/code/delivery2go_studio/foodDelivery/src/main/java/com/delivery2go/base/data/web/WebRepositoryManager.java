package com.delivery2go.base.data.web;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.enterlib.conetivity.WebClientContext;
import com.enterlib.data.IEntityContext;
import com.delivery2go.base.models.*;
import com.delivery2go.base.data.RepositoryManager;

import com.delivery2go.base.data.repository.IEntityCategoryRepository;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.data.repository.IEntityImagesRepository;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.data.repository.IUserEntitiesRepository;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.data.repository.IEntityReviewRepository;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.data.repository.IDeliveryAgentRepository;
import com.delivery2go.base.data.repository.IDeliveryTarifRepository;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.data.repository.IClientRepository;

public class WebRepositoryManager extends RepositoryManager {


	public static final String DEFAULT_BASE_URL="http://10.0.2.2:1816/api/";
	public static final String BASE_URL = "BASE_URL";


	private WebClientContext webContext;

	public WebRepositoryManager(Context context) {
		super(context);
	}


	public String getSessionId(){
		return "D566E76F60C87C42B61DA1C4B8D984E7";
	}

	public String getBaseUrl(){
		return preferences.getString(BASE_URL, DEFAULT_BASE_URL);
	}

	@Override
	public IEntityContext getEntityContext(){
		return getClientContext();
	}

	private WebClientContext getClientContext(){
		return webContext != null ? webContext : (webContext = new WebContext(context, getBaseUrl(), getSessionId()));
	}

	@Override
	public void close() {
		if(!isClosed()){
			if(webContext!=null){
				webContext.close();
				webContext = null;
			}
		}
		super.close();
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityCategoryRepository getIEntityCategoryRepository(){
		return new EntityCategoryWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IStateRepository getIStateRepository(){
		return new StateWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityImagesRepository getIEntityImagesRepository(){
		return new EntityImagesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IImageRepository getEntityImages_ImageRepository(int entity, boolean distint){
		return new EntityImagesImageWebRepository(getClientContext(), getBaseUrl(), getSessionId(), entity, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityRepository getEntityImages_EntityRepository(int image, boolean distint){
		return new EntityImagesEntityWebRepository(getClientContext(), getBaseUrl(), getSessionId(),image, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityCategoriesRepository getIEntityCategoriesRepository(){
		return new EntityCategoriesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityCategoryRepository getEntityCategories_EntityCategoryRepository(int entity, boolean distint){
		return new EntityCategoriesEntityCategoryWebRepository(getClientContext(), getBaseUrl(), getSessionId(), entity, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityRepository getEntityCategories_EntityRepository(int category, boolean distint){
		return new EntityCategoriesEntityWebRepository(getClientContext(), getBaseUrl(), getSessionId(),category, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IDishCategoriesRepository getIDishCategoriesRepository(){
		return new DishCategoriesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishCategoryRepository getDishCategories_DishCategoryRepository(int dish, boolean distint){
		return new DishCategoriesDishCategoryWebRepository(getClientContext(), getBaseUrl(), getSessionId(), dish, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IDishRepository getDishCategories_DishRepository(int category, boolean distint){
		return new DishCategoriesDishWebRepository(getClientContext(), getBaseUrl(), getSessionId(),category, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IDishImagesRepository getIDishImagesRepository(){
		return new DishImagesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IImageRepository getDishImages_ImageRepository(int dish, boolean distint){
		return new DishImagesImageWebRepository(getClientContext(), getBaseUrl(), getSessionId(), dish, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IDishRepository getDishImages_DishRepository(int image, boolean distint){
		return new DishImagesDishWebRepository(getClientContext(), getBaseUrl(), getSessionId(),image, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.ICityRepository getICityRepository(){
		return new CityWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IClientDishFavoritesRepository getIClientDishFavoritesRepository(){
		return new ClientDishFavoritesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishRepository getClientDishFavorites_DishRepository(int client, boolean distint){
		return new ClientDishFavoritesDishWebRepository(getClientContext(), getBaseUrl(), getSessionId(), client, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IClientRepository getClientDishFavorites_ClientRepository(int dish, boolean distint){
		return new ClientDishFavoritesClientWebRepository(getClientContext(), getBaseUrl(), getSessionId(),dish, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IClientEntityFavoritesRepository getIClientEntityFavoritesRepository(){
		return new ClientEntityFavoritesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityRepository getClientEntityFavorites_EntityRepository(int client, boolean distint){
		return new ClientEntityFavoritesEntityWebRepository(getClientContext(), getBaseUrl(), getSessionId(), client, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IClientRepository getClientEntityFavorites_ClientRepository(int entity, boolean distint){
		return new ClientEntityFavoritesClientWebRepository(getClientContext(), getBaseUrl(), getSessionId(),entity, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IOrderStateRepository getIOrderStateRepository(){
		return new OrderStateWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IOrderTypeRepository getIOrderTypeRepository(){
		return new OrderTypeWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishSizeRepository getIDishSizeRepository(){
		return new DishSizeWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IRollRepository getIRollRepository(){
		return new RollWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishCategoryRepository getIDishCategoryRepository(){
		return new DishCategoryWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishSizePriceRepository getIDishSizePriceRepository(){
		return new DishSizePriceWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IImageRepository getIImageRepository(){
		return new ImageWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IOrderDishRepository getIOrderDishRepository(){
		return new OrderDishWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IOrderDishAddonsRepository getIOrderDishAddonsRepository(){
		return new OrderDishAddonsWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IPermissionRepository getIPermissionRepository(){
		return new PermissionWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IUserRepository getIUserRepository(){
		return new UserWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IUserEntitiesRepository getIUserEntitiesRepository(){
		return new UserEntitiesWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityRepository getUserEntities_EntityRepository(int user, boolean distint){
		return new UserEntitiesEntityWebRepository(getClientContext(), getBaseUrl(), getSessionId(), user, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IUserRepository getUserEntities_UserRepository(int entity, boolean distint){
		return new UserEntitiesUserWebRepository(getClientContext(), getBaseUrl(), getSessionId(),entity, distint);
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityRepository getIEntityRepository(){
		return new EntityWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IAddonsRepository getIAddonsRepository(){
		return new AddonsWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityMenuRepository getIEntityMenuRepository(){
		return new EntityMenuWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IEntityReviewRepository getIEntityReviewRepository(){
		return new EntityReviewWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishReviewRepository getIDishReviewRepository(){
		return new DishReviewWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDishRepository getIDishRepository(){
		return new DishWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDeliveryAgentRepository getIDeliveryAgentRepository(){
		return new DeliveryAgentWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IDeliveryTarifRepository getIDeliveryTarifRepository(){
		return new DeliveryTarifWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IOrderRepository getIOrderRepository(){
		return new OrderWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

	@Override
	public com.delivery2go.base.data.repository.IClientRepository getIClientRepository(){
		return new ClientWebRepository(getClientContext(), getBaseUrl(), getSessionId());
	}

}