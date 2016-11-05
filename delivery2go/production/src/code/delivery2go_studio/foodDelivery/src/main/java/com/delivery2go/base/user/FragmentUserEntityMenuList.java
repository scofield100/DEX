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
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.entitymenu.ViewModelEntityMenuList;
import com.delivery2go.base.entitymenu.FragmentEntityMenuList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentUserEntityMenuList extends FragmentEntityMenuList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityUserDetails.ID);
			return new WrapperRepEntityMenu_UpdateUserId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityUserDetails.ID);
			return new WrapperRepEntityMenu_UpdateUserId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list_dependence, menu);
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.DELETE)){
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}else{
		onInitDelete(menu);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.CREATE)){
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.WRITE)){
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.entitymenu.ActivityEntityMenuList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_ENTITYMENU", FragmentUserEntityMenuList.AttachFactory.class)
					.putExtra(ActivityUserDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID))
					, com.delivery2go.base.entitymenu.FragmentEntityMenuEdit.ENTITYMENU_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.entitymenu.ActivityEntityMenuEdit.class)
					.putExtra("REPOSITORY_ENTITYMENU", FragmentUserEntityMenuList.Factory.class)
					.putExtra(ActivityUserDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"UpdateUserId"} )
					, com.delivery2go.base.entitymenu.FragmentEntityMenuEdit.ENTITYMENU_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityUserDetails.ID);

		return new ViewModelEntityMenuList(this, new WrapperRepEntityMenu_UpdateUserId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0]));
	}

}
