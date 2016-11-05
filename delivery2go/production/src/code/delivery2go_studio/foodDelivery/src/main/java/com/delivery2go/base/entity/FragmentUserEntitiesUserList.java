package com.delivery2go.base.entity;

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
import com.delivery2go.base.models.User;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.user.ViewModelUserList;
import com.delivery2go.base.user.FragmentUserList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentUserEntitiesUserList extends FragmentUserList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return RepositoryManager.getInstance().getUserEntities_UserRepository(ids[0], false);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return RepositoryManager.getInstance().getUserEntities_UserRepository(ids[0], true);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_entity_users, menu);
		if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.UserEntities}, Access.DELETE)){
			menu.findItem(R.id.delete).setEnabled(false);
			menu.findItem(R.id.delete).setVisible(false);
		}else{
			onInitDelete(menu);
		}

		if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.UserEntities}, Access.CREATE)){
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.user.ActivityUserList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_USER", FragmentUserEntitiesUserList.AttachFactory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					, com.delivery2go.base.user.FragmentUserEdit.USER_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.user.ActivityUserEdit.class)
					.putExtra("REPOSITORY_USER", FragmentUserEntitiesUserList.Factory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID)), com.delivery2go.base.user.FragmentUserEdit.USER_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelUserList(this, RepositoryManager.getInstance().getUserEntities_UserRepository(ids[0], false));
	}

}
