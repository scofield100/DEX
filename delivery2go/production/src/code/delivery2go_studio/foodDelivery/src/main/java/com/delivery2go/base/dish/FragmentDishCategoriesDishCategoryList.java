package com.delivery2go.base.dish;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.DataViewModel;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.enterlib.data.IRepositoryFactory;
import com.enterlib.filtering.FilterOp;
import android.view.MenuItem;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.dishcategory.ViewModelDishCategoryList;
import com.delivery2go.base.dishcategory.FragmentDishCategoryList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentDishCategoriesDishCategoryList extends FragmentDishCategoryList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityDishDetails.ID);
			return RepositoryManager.getInstance().getDishCategories_DishCategoryRepository(ids[0], false);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityDishDetails.ID);
			return RepositoryManager.getInstance().getDishCategories_DishCategoryRepository(ids[0], true);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list_dependence, menu);
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategories}, Access.DELETE)){
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}else{
		onInitDelete(menu);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategory}, Access.CREATE)){
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategories}, Access.CREATE)){
		menu.findItem(R.id.attach).setEnabled(false);
		menu.findItem(R.id.attach).setVisible(false);
	}
		onInitSearch(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.dishcategory.ActivityDishCategoryList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_DISHCATEGORY", FragmentDishCategoriesDishCategoryList.AttachFactory.class)
					.putExtra(ActivityDishDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID))
					, com.delivery2go.base.dishcategory.FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.dishcategory.ActivityDishCategoryEdit.class)
					.putExtra("REPOSITORY_DISHCATEGORY", FragmentDishCategoriesDishCategoryList.Factory.class)
					.putExtra(ActivityDishDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID)), com.delivery2go.base.dishcategory.FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID);

		return new ViewModelDishCategoryList(this, RepositoryManager.getInstance().getDishCategories_DishCategoryRepository(ids[0], false));
	}

}
