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
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.entitymenu.ViewModelEntityMenuList;
import com.delivery2go.base.entitymenu.FragmentEntityMenuList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentEntityEntityMenuList extends FragmentEntityMenuList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return new WrapperRepEntityMenu_EntityId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return new WrapperRepEntityMenu_EntityId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
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
					.putExtra("REPOSITORY_ENTITYMENU", FragmentEntityEntityMenuList.AttachFactory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					, com.delivery2go.base.entitymenu.FragmentEntityMenuEdit.ENTITYMENU_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.entitymenu.ActivityEntityMenuEdit.class)
					.putExtra("REPOSITORY_ENTITYMENU", FragmentEntityEntityMenuList.Factory.class)
					.putExtra(ActivityEntityDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"EntityId"} )
					, com.delivery2go.base.entitymenu.FragmentEntityMenuEdit.ENTITYMENU_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelEntityMenuList(this, new WrapperRepEntityMenu_EntityId(RepositoryManager.getInstance().getIEntityMenuRepository(), ids[0]));
	}

}
