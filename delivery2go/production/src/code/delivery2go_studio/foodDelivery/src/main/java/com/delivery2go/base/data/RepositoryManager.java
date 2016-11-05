package com.delivery2go.base.data;

import com.delivery2go.base.data.web.WebRepositoryManager;
import com.delivery2go.base.data.sqlite.DbContext;
import com.delivery2go.base.data.repository.IEntityCategoryRepository;
import com.delivery2go.base.data.repository.IStateRepository;
import com.delivery2go.base.data.repository.IEntityImagesRepository;
import com.delivery2go.base.data.repository.IEntityCategoriesRepository;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.repository.IOrderStateRepository;
import com.delivery2go.base.data.repository.IOrderTypeRepository;
import com.delivery2go.base.data.repository.IDishSizeRepository;
import com.delivery2go.base.data.repository.IRollRepository;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.data.repository.IOrderRepository;
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

import com.enterlib.data.EntityMapContext;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.enterlib.data.IClosable;
import com.enterlib.data.IEntityContext;


public class RepositoryManager implements IClosable {
    private static RepositoryManager singleton;
    private boolean isClosed;
    protected Context context;
    protected SharedPreferences preferences;


    public static final String DATABASE = "delivery.db3";
    public static final String ONLINE_MODE = "ONLINE_MODE";
    public static boolean ONLINE_MODE_ENABLE = true;

    static boolean deployed;


    public static RepositoryManager getInstance(){
        return singleton;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Context getContext(){
        return context;
    }

    public static RepositoryManager create(Context context){
        if(singleton == null || singleton.isClosed){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            if(pref.getBoolean(ONLINE_MODE, ONLINE_MODE_ENABLE)){
                singleton = new WebRepositoryManager(context);
            }else{
                singleton = new RepositoryManager(context);
            }
        }
        return singleton;
    }

    public RepositoryManager(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isOnline(){
        return preferences.getBoolean(ONLINE_MODE, ONLINE_MODE_ENABLE);
    }

    @Override
    public void close(){
        if(!isClosed){

            isClosed = true;
        }
    }

    public IEntityContext getEntityContext(){
        return getMapContext();
    }

    public EntityMapContext getMapContext(){
        if(!deployed){
            deployed = EntityMapContext.deploy(context, DATABASE, 4);
        }
        return new DbContext(context, DATABASE, null);
    }

    public IEntityCategoryRepository getIEntityCategoryRepository(){
        return new com.delivery2go.base.data.sqlite.EntityCategoryDbRepository(getMapContext());
    }

    public IStateRepository getIStateRepository(){
        return new com.delivery2go.base.data.sqlite.StateDbRepository(getMapContext());
    }

    public IEntityImagesRepository getIEntityImagesRepository(){
        return new com.delivery2go.base.data.sqlite.EntityImagesDbRepository(getMapContext());
    }

    public IImageRepository getEntityImages_ImageRepository(int entity, boolean distint){
        return new com.delivery2go.base.data.sqlite.EntityImages_ImageDbRepository(getMapContext(),entity, distint);
    }

    public IEntityRepository getEntityImages_EntityRepository(int image, boolean distint){
        return new com.delivery2go.base.data.sqlite.EntityImages_EntityDbRepository(getMapContext(),image, distint);
    }

    public IEntityCategoriesRepository getIEntityCategoriesRepository(){
        return new com.delivery2go.base.data.sqlite.EntityCategoriesDbRepository(getMapContext());
    }

    public IEntityCategoryRepository getEntityCategories_EntityCategoryRepository(int entity, boolean distint){
        return new com.delivery2go.base.data.sqlite.EntityCategories_EntityCategoryDbRepository(getMapContext(),entity, distint);
    }

    public IEntityRepository getEntityCategories_EntityRepository(int category, boolean distint){
        return new com.delivery2go.base.data.sqlite.EntityCategories_EntityDbRepository(getMapContext(),category, distint);
    }

    public IDishCategoriesRepository getIDishCategoriesRepository(){
        return new com.delivery2go.base.data.sqlite.DishCategoriesDbRepository(getMapContext());
    }

    public IDishCategoryRepository getDishCategories_DishCategoryRepository(int dish, boolean distint){
        return new com.delivery2go.base.data.sqlite.DishCategories_DishCategoryDbRepository(getMapContext(),dish, distint);
    }

    public IDishRepository getDishCategories_DishRepository(int category, boolean distint){
        return new com.delivery2go.base.data.sqlite.DishCategories_DishDbRepository(getMapContext(),category, distint);
    }

    public IDishImagesRepository getIDishImagesRepository(){
        return new com.delivery2go.base.data.sqlite.DishImagesDbRepository(getMapContext());
    }

    public IImageRepository getDishImages_ImageRepository(int dish, boolean distint){
        return new com.delivery2go.base.data.sqlite.DishImages_ImageDbRepository(getMapContext(),dish, distint);
    }

    public IDishRepository getDishImages_DishRepository(int image, boolean distint){
        return new com.delivery2go.base.data.sqlite.DishImages_DishDbRepository(getMapContext(),image, distint);
    }

    public ICityRepository getICityRepository(){
        return new com.delivery2go.base.data.sqlite.CityDbRepository(getMapContext());
    }

    public IClientDishFavoritesRepository getIClientDishFavoritesRepository(){
        return new com.delivery2go.base.data.sqlite.ClientDishFavoritesDbRepository(getMapContext());
    }

    public IDishRepository getClientDishFavorites_DishRepository(int client, boolean distint){
        return new com.delivery2go.base.data.sqlite.ClientDishFavorites_DishDbRepository(getMapContext(),client, distint);
    }

    public IClientRepository getClientDishFavorites_ClientRepository(int dish, boolean distint){
        return new com.delivery2go.base.data.sqlite.ClientDishFavorites_ClientDbRepository(getMapContext(),dish, distint);
    }

    public IClientEntityFavoritesRepository getIClientEntityFavoritesRepository(){
        return new com.delivery2go.base.data.sqlite.ClientEntityFavoritesDbRepository(getMapContext());
    }

    public IEntityRepository getClientEntityFavorites_EntityRepository(int client, boolean distint){
        return new com.delivery2go.base.data.sqlite.ClientEntityFavorites_EntityDbRepository(getMapContext(),client, distint);
    }

    public IClientRepository getClientEntityFavorites_ClientRepository(int entity, boolean distint){
        return new com.delivery2go.base.data.sqlite.ClientEntityFavorites_ClientDbRepository(getMapContext(),entity, distint);
    }

    public IClientRepository getIClientRepository(){
        return new com.delivery2go.base.data.sqlite.ClientDbRepository(getMapContext());
    }

    public IOrderStateRepository getIOrderStateRepository(){
        return new com.delivery2go.base.data.sqlite.OrderStateDbRepository(getMapContext());
    }

    public IOrderTypeRepository getIOrderTypeRepository(){
        return new com.delivery2go.base.data.sqlite.OrderTypeDbRepository(getMapContext());
    }

    public IDishSizeRepository getIDishSizeRepository(){
        return new com.delivery2go.base.data.sqlite.DishSizeDbRepository(getMapContext());
    }

    public IRollRepository getIRollRepository(){
        return new com.delivery2go.base.data.sqlite.RollDbRepository(getMapContext());
    }

    public IDishCategoryRepository getIDishCategoryRepository(){
        return new com.delivery2go.base.data.sqlite.DishCategoryDbRepository(getMapContext());
    }

    public IDishSizePriceRepository getIDishSizePriceRepository(){
        return new com.delivery2go.base.data.sqlite.DishSizePriceDbRepository(getMapContext());
    }

    public IImageRepository getIImageRepository(){
        return new com.delivery2go.base.data.sqlite.ImageDbRepository(getMapContext());
    }

    public IOrderRepository getIOrderRepository(){
        return new com.delivery2go.base.data.sqlite.OrderDbRepository(getMapContext());
    }

    public IOrderDishRepository getIOrderDishRepository(){
        return new com.delivery2go.base.data.sqlite.OrderDishDbRepository(getMapContext());
    }

    public IOrderDishAddonsRepository getIOrderDishAddonsRepository(){
        return new com.delivery2go.base.data.sqlite.OrderDishAddonsDbRepository(getMapContext());
    }

    public IPermissionRepository getIPermissionRepository(){
        return new com.delivery2go.base.data.sqlite.PermissionDbRepository(getMapContext());
    }

    public IUserRepository getIUserRepository(){
        return new com.delivery2go.base.data.sqlite.UserDbRepository(getMapContext());
    }

    public IUserEntitiesRepository getIUserEntitiesRepository(){
        return new com.delivery2go.base.data.sqlite.UserEntitiesDbRepository(getMapContext());
    }

    public IEntityRepository getUserEntities_EntityRepository(int user, boolean distint){
        return new com.delivery2go.base.data.sqlite.UserEntities_EntityDbRepository(getMapContext(),user, distint);
    }

    public IUserRepository getUserEntities_UserRepository(int entity, boolean distint){
        return new com.delivery2go.base.data.sqlite.UserEntities_UserDbRepository(getMapContext(),entity, distint);
    }

    public IEntityRepository getIEntityRepository(){
        return new com.delivery2go.base.data.sqlite.EntityDbRepository(getMapContext());
    }

    public IAddonsRepository getIAddonsRepository(){
        return new com.delivery2go.base.data.sqlite.AddonsDbRepository(getMapContext());
    }

    public IEntityMenuRepository getIEntityMenuRepository(){
        return new com.delivery2go.base.data.sqlite.EntityMenuDbRepository(getMapContext());
    }

    public IEntityReviewRepository getIEntityReviewRepository(){
        return new com.delivery2go.base.data.sqlite.EntityReviewDbRepository(getMapContext());
    }

    public IDishReviewRepository getIDishReviewRepository(){
        return new com.delivery2go.base.data.sqlite.DishReviewDbRepository(getMapContext());
    }

    public IDishRepository getIDishRepository(){
        return new com.delivery2go.base.data.sqlite.DishDbRepository(getMapContext());
    }

    public IDeliveryAgentRepository getIDeliveryAgentRepository(){
        return new com.delivery2go.base.data.sqlite.DeliveryAgentDbRepository(getMapContext());
    }

    public IDeliveryTarifRepository getIDeliveryTarifRepository(){
        return new com.delivery2go.base.data.sqlite.DeliveryTarifDbRepository(getMapContext());
    }



}
