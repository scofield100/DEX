package com.delivery2go.base.user;

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
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.addons.ViewModelAddonsList;
import com.delivery2go.base.addons.FragmentAddonsList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentUserAddonsList extends FragmentAddonsList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityUserDetails.ID);
			return new WrapperRepAddons_UpdateUserId(RepositoryManager.getInstance().getIAddonsRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityUserDetails.ID);
			return new WrapperRepAddons_UpdateUserId(RepositoryManager.getInstance().getIAddonsRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list_dependence, menu);
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Addons}, Access.DELETE)){
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}else{
		onInitDelete(menu);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Addons}, Access.CREATE)){
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Addons}, Access.WRITE)){
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.addons.ActivityAddonsList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_ADDONS", FragmentUserAddonsList.AttachFactory.class)
					.putExtra(ActivityUserDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID))
					, com.delivery2go.base.addons.FragmentAddonsEdit.ADDONS_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.addons.ActivityAddonsEdit.class)
					.putExtra("REPOSITORY_ADDONS", FragmentUserAddonsList.Factory.class)
					.putExtra(ActivityUserDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"UpdateUserId"} )
					, com.delivery2go.base.addons.FragmentAddonsEdit.ADDONS_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID);

		return new ViewModelAddonsList(this, new WrapperRepAddons_UpdateUserId(RepositoryManager.getInstance().getIAddonsRepository(), ids[0]));
	}

}
