package com.delivery2go.entity;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.*;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.EntityMenu;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.data.IEntityCursor;
import com.enterlib.fields.AdapterProvider;
import com.enterlib.fields.Field;
import com.enterlib.fields.ItemsField;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IAsyncLoadOperation;
import com.enterlib.mvvm.SelectionCommand;

public class ViewModelEntityWelcome extends DataViewModel implements IClosable{
    private final ViewModelEntityHome parentViewModel;

    private final class DishCategoryViewModel extends DishCategory {
		public DishCategory InnerCategory;
		
		public Command OnSelect = new Command() {						
			@Override
			public void invoke(Object invocator, Object args) {
				if(selectedCategory!=DishCategoryViewModel.this){
					selectedCategory = DishCategoryViewModel.this;					
					if(categoriesAdapter!=null){
						categoriesAdapter.notifyDataSetChanged();
					}
					loadSelectedCategory();
				}
			}				
		};

		public int isSelected(){
			return this == selectedCategory?View.VISIBLE:View.INVISIBLE;
		}
	}

	IEntityCursor<Dish>promotions;
	IEntityCursor<Dish>recomendation;
	ArrayList<EntityMenu> menus;
	ArrayList<DishCategoryViewModel>categories;

	IDishRepository dishRepository;
	int entityId;				
	
	FragmentEntityWelcome fragment;
	private IEntityMenuRepository menuRepository;
	private IDishCategoryRepository categoryRep;
	private DishCategoryViewModel selectedCategory;		
	private EntityMenu selectedMenu;
	private BaseAdapter categoriesAdapter;
	public AdapterProvider CategoryAdapterProvider;
	Entity entity;
	private IEntityRepository entityRep;
	
	public ViewModelEntityWelcome(FragmentEntityWelcome view, IDishRepository dishRepository, int entityId, ViewModelEntityHome activityViewModel) {
		super(view);

		this.dishRepository = dishRepository;
		this.menuRepository = RepositoryManager.getInstance().getIEntityMenuRepository();
		this.categoryRep = RepositoryManager.getInstance().getIDishCategoryRepository();
		this.entityRep = RepositoryManager.getInstance().getIEntityRepository();
		this.parentViewModel = activityViewModel;
        
		this.entityId = entityId;
		this.fragment = view;
		
		CategoryAdapterProvider = new AdapterProvider(view.getActivity(), R.layout.entity_entitycategory_adapter){				
			@Override
			public void onItemsSet(ItemsField listField) {
				super.onItemsSet(listField);
				categoriesAdapter = (BaseAdapter) listField.getAdapter();
			}
		};
	}

	public IEntityCursor<Dish> getPromotions() {
		return promotions;
	}


	public IEntityCursor<Dish> getRecomendations() {
		return recomendation;
	}

	public Date getOpeningTime(){
		return entity.OpeningTime;
	}
	
	public Date getCloseTime(){
		return entity.CloseTime;
	}

	public ArrayList<EntityMenu> getMenus() {
		return menus;
	}
	
	public ArrayList<DishCategoryViewModel> getCategories(){
		return categories;
	}
	
	public EntityMenu getSelectedMenu(){
		return selectedMenu;
	}
	
	public void setSelectedMenu(EntityMenu value){
		selectedMenu = value;
		onPropertyChange("SelectedMenu");
	}

	@Override
	protected boolean loadAsync() throws Exception {
        if(!parentViewModel.isLoaded())
            return false;

		if(promotions!=null)
			promotions.close();
		if(recomendation != null)
			recomendation.close();

		entity = parentViewModel.getEntity();
		EntityMenu whatsgood = new EntityMenu();
		whatsgood.Name ="What's Good";
		whatsgood.ImageId = 0;
		menus =BaseModel.asOptionalList(EntityMenu.class, menuRepository.getAll(String.format("EntityId = %d", entityId), -1, -1),whatsgood);		
		
		categories = new ArrayList<DishCategoryViewModel>();
		DishCategory allCategory = new DishCategory();
		allCategory.Name = "All";
		for (DishCategory c : BaseModel.asOptionalList(DishCategory.class, parentViewModel.getCategories(), allCategory)) {
						
			DishCategoryViewModel cat= new DishCategoryViewModel();				
			cat.Code = c.Code;
			cat.Name = c.Name;
			cat.Id = c.Id;
			cat.InnerCategory = c;
			categories.add(cat);
		}						
			
		if(selectedMenu == null)
			selectedMenu = menus.get(0);
		
		if(selectedCategory == null)
			selectedCategory = categories.get(0);
		
		if(selectedCategory.Id == 0){
			recomendation = dishRepository.getCursor(getFilter(), "Ranking DESC");
		}else{
			recomendation = selectedCategory.InnerCategory.getDishs()
					.where(getFilter())
					.orderBy("Ranking DESC");
		}
				
		return true;
					
	}

	@Override
	public void close() {
		if(promotions!=null)
			promotions.close();
		if(recomendation != null)
			recomendation.close();

		if(dishRepository !=null){
			dishRepository.close();
		}

	}

	public SelectionCommand OnPromotionItemClick = new SelectionCommand() {

		@Override
		public void invoke(Field field, AdapterView<?> adapterView, View itemView,
				int position, long id) {

			Dish item = (Dish) adapterView.getItemAtPosition(position);
			Intent i =new  Intent(fragment.getActivity().getApplicationContext(), ActivityOrderDish.class);
			i.putExtra(ActivityDishDetails.ID, new int[]{item.Id});
			fragment.startActivityForResult(i, FragmentDishEdit.DISH_EDITED);
		}
	};	

	public Command OnItemCLick = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			Dish item = (Dish) args;

			Intent i =new  Intent(fragment.getActivity().getApplicationContext(), ActivityOrderDish.class);
			i.putExtra(ActivityDishDetails.ID, new int[]{item.Id});
			fragment.startActivityForResult(i, FragmentDishEdit.DISH_EDITED);


		}
	};
	
	public SelectionCommand OnMenuClick = new SelectionCommand() {
		
		@Override
		public void invoke(Field field, AdapterView<?> adapterView, View itemView,
				int position, long id) {
			
			final EntityMenu menu = (EntityMenu) adapterView.getItemAtPosition(position);
			setSelectedMenu(menu);
			loadSelectedMenu();				
		}

		private void loadSelectedMenu() {
			doLoadOperationAsync(new IAsyncLoadOperation() {
				
				@Override
				public void onDataLoaded() {
					onPropertyChange("Recomendations");	
					selectedCategory = categories.get(0);
					
					fragment.closeMenuPanel();
					
					if(categoriesAdapter!=null)
						categoriesAdapter.notifyDataSetChanged();
				}
				
				@Override
				public boolean loadAsync() throws Exception {
					String filter;
					if(selectedMenu.Id == 0){
						filter = String.format("InWhatsGood = true and EntityId=%d", entityId); 
					}else{
						filter = String.format("MenuId = %d and EntityId=%d", selectedMenu.Id,entityId);
					}
					recomendation = dishRepository.getCursor(filter, "Ranking DESC");
					return true;
				}
			});
		}
	};
	
	public String getFilter(){
		String filter = String.format("IsAvailable = true and (AvailableCount = null or AvailableCount > 0) and EntityId = %d", entityId);
		if(selectedMenu == null || selectedMenu.Id == 0){
			filter += " and InWhatsGood = true";
		}
		else {
			filter+= String.format(" and MenuId = %d", selectedMenu.Id);
		}
		return filter;
	}
		
	
	private void loadSelectedCategory() {
		doLoadOperationAsync(new  IAsyncLoadOperation() {
			
			@Override
			public void onDataLoaded() {
				onPropertyChange("Recomendations");					
			}
			
			@Override
			public boolean loadAsync() throws Exception {
				if(selectedCategory.Id == 0){
					recomendation = dishRepository.getCursor(getFilter(), "Ranking DESC");
				}else{
					recomendation = selectedCategory.InnerCategory.getDishs()
							.where(getFilter())
							.orderBy("Ranking DESC")
							.reload();
				}
				return true;
			}
		});
		
	}
	
}