package com.delivery2go.base.clientdishfavorites;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.ClientDishFavorites;
import com.delivery2go.base.data.repository.IClientDishFavoritesRepository;
import com.delivery2go.base.clientdishfavorites.ViewModelClientDishFavoritesDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.ClientDishFavorites})
public class FragmentClientDishFavoritesDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.ClientDishFavorites}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.ClientDishFavorites}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_clientdishfavorites_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityClientDishFavoritesEdit.class).putExtra(ActivityClientDishFavoritesDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityClientDishFavoritesDetails.ID)), FragmentClientDishFavoritesEdit.CLIENTDISHFAVORITES_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentClientDishFavoritesDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityClientDishFavoritesList.class).putExtra(ActivityClientDishFavoritesDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityClientDishFavoritesDetails.ID)));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelClientDishFavoritesDetails(this, RepositoryManager.getInstance().getIClientDishFavoritesRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityClientDishFavoritesDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelClientDishFavoritesDetails.CLIENT:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.client.ActivityClientDetails.class).putExtra(com.delivery2go.base.client.ActivityClientDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelClientDishFavoritesDetails.DISH:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.dish.ActivityDishDetails.class).putExtra(com.delivery2go.base.dish.ActivityDishDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentClientDishFavoritesEdit.CLIENTDISHFAVORITES_EDITED){
			load();
			getActivity().setResult(FragmentClientDishFavoritesEdit.CLIENTDISHFAVORITES_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentClientDishFavoritesEdit.CLIENTDISHFAVORITES_EDITED);
		super.onDeleted(data);
	}
}
