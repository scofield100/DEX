package com.delivery2go.base.client;

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
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.order.ViewModelOrderList;
import com.delivery2go.base.order.FragmentOrderList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentClientOrderList extends FragmentOrderList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityClientDetails.ID);
			return new WrapperRepOrder_ClientId(RepositoryManager.getInstance().getIOrderRepository(), ids[0]);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityClientDetails.ID);
			return new WrapperRepOrder_ClientId(RepositoryManager.getInstance().getIOrderRepository(), ids[0], FilterOp.NOT_EQUALS);
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list_dependence, menu);
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.DELETE)){
		menu.findItem(R.id.delete).setEnabled(false);
		menu.findItem(R.id.delete).setVisible(false);
	}else{
		onInitDelete(menu);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.CREATE)){
		menu.findItem(R.id.create).setEnabled(false);
		menu.findItem(R.id.create).setVisible(false);
	}
	if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.WRITE)){
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), com.delivery2go.base.order.ActivityOrderList.class)
					.putExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE,true)
					.putExtra("REPOSITORY_ORDER", FragmentClientOrderList.AttachFactory.class)
					.putExtra(ActivityClientDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityClientDetails.ID))
					, com.delivery2go.base.order.FragmentOrderEdit.ORDER_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), com.delivery2go.base.order.ActivityOrderEdit.class)
					.putExtra("REPOSITORY_ORDER", FragmentClientOrderList.Factory.class)
					.putExtra(ActivityClientDetails.ID, (int[])getActivity().getIntent().getSerializableExtra(ActivityClientDetails.ID))
					.putExtra(BindableEditFragment.READ_ONLY_FIELDS, new String[] {"ClientId"} )
					, com.delivery2go.base.order.FragmentOrderEdit.ORDER_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityClientDetails.ID);

		return new ViewModelOrderList(this, new WrapperRepOrder_ClientId(RepositoryManager.getInstance().getIOrderRepository(), ids[0]), false);
	}

}
