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
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.dishreview.ViewModelDishReviewList;
import com.delivery2go.base.dishreview.FragmentDishReviewList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentDishDishReviewList extends FragmentDishReviewList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityDishDetails.ID);
			return new WrapperRepDishReview_DishId(RepositoryManager.getInstance().getIDishReviewRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityDishDetails.ID);
			return new WrapperRepDishReview_DishId(RepositoryManager.getInstance().getIDishReviewRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_comments, menu);
//	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishReview}, Access.DELETE)){
//		menu.findItem(R.id.delete).setEnabled(false);
//		menu.findItem(R.id.delete).setVisible(false);
//	}else{
//		onInitDelete(menu);
//	}

		onInitSearch(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.dishreview.ActivityDishReviewList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_DISHREVIEW", AttachFactory.class)
					.putExtra(ActivityDishDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID))
					, com.delivery2go.base.dishreview.FragmentDishReviewEdit.DISHREVIEW_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.dishreview.ActivityDishReviewEdit.class)
					.putExtra("REPOSITORY_DISHREVIEW", Factory.class)
					.putExtra(ActivityDishDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"DishId"} )
					, com.delivery2go.base.dishreview.FragmentDishReviewEdit.DISHREVIEW_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID);

		return new ViewModelDishReviewList(this, new WrapperRepDishReview_DishId(RepositoryManager.getInstance().getIDishReviewRepository(), ids[0]));
	}

}
