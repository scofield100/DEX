package com.delivery2go.base.entitymenu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.DataViewModel;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.enterlib.data.IRepositoryFactory;
import com.enterlib.filtering.FilterOp;
import android.view.MenuItem;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.dish.ViewModelDishList;
import com.delivery2go.base.dish.FragmentDishList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentEntityMenuDishList extends FragmentDishList{

	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID);
            int[] entityId =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);

			return new WrapperRepDish_MenuId(RepositoryManager.getInstance().getIDishRepository(), ids[0] , entityId[0], FilterOp.EQUALS);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {

			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID);
            int[] entityId =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return new WrapperRepDish_MenuId(RepositoryManager.getInstance().getIDishRepository(), ids[0], entityId[0], FilterOp.NOT_EQUALS);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list_dependence, menu);
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.DELETE)){
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}else{
		onInitDelete(menu);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.CREATE)){
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.WRITE)){
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.dish.ActivityDishList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_DISH", FragmentEntityMenuDishList.AttachFactory.class)
					.putExtra(ActivityEntityMenuDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID))
                    .putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					, com.delivery2go.base.dish.FragmentDishEdit.DISH_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.dish.ActivityDishEdit.class)
					.putExtra("REPOSITORY_DISH", FragmentEntityMenuDishList.Factory.class)
					.putExtra(ActivityEntityMenuDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID))
                    .putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[]{"MenuId"})
					, com.delivery2go.base.dish.FragmentDishEdit.DISH_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID);
        int[] entityId =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelDishList(this, new WrapperRepDish_MenuId(RepositoryManager.getInstance().getIDishRepository(), ids[0], entityId[0], FilterOp.EQUALS));
	}

}
