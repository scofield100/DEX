package com.delivery2go.base.entity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.DataViewModel;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.enterlib.data.IRepositoryFactory;
import com.enterlib.filtering.FilterOp;
import android.view.MenuItem;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.entityreview.ViewModelEntityReviewList;
import com.delivery2go.base.entityreview.FragmentEntityReviewList;
import com.delivery2go.R;


public class FragmentEntityEntityReviewList extends FragmentEntityReviewList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return new WrapperRepEntityReview_EntityId(RepositoryManager.getInstance().getIEntityReviewRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return new WrapperRepEntityReview_EntityId(RepositoryManager.getInstance().getIEntityReviewRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_comments, menu);
//		if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityReview}, Access.DELETE)){
//			menu.findItem(R.id.delete).setEnabled(false);
//			menu.findItem(R.id.delete).setVisible(false);
//		}else{
//			onInitDelete(menu);
//		}

		onInitSearch(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.entityreview.ActivityEntityReviewList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_ENTITYREVIEW", AttachFactory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					, com.delivery2go.base.entityreview.FragmentEntityReviewEdit.ENTITYREVIEW_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.entityreview.ActivityEntityReviewEdit.class)
					.putExtra("REPOSITORY_ENTITYREVIEW", Factory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"EntityId"} )
					, com.delivery2go.base.entityreview.FragmentEntityReviewEdit.ENTITYREVIEW_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelEntityReviewList(this, new WrapperRepEntityReview_EntityId(RepositoryManager.getInstance().getIEntityReviewRepository(), ids[0]));
	}

}
