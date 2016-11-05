package com.delivery2go.base.orderdishaddons;

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
import com.delivery2go.base.models.OrderDishAddons;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.orderdishaddons.ViewModelOrderDishAddonsDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.OrderDishAddons})
public class FragmentOrderDishAddonsDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDishAddons}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDishAddons}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_orderdishaddons_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityOrderDishAddonsEdit.class).putExtra(ActivityOrderDishAddonsDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishAddonsDetails.ID)), FragmentOrderDishAddonsEdit.ORDERDISHADDONS_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentOrderDishAddonsDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityOrderDishAddonsList.class).putExtra(ActivityOrderDishAddonsDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishAddonsDetails.ID)));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelOrderDishAddonsDetails(this, RepositoryManager.getInstance().getIOrderDishAddonsRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishAddonsDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelOrderDishAddonsDetails.ORDERDISH:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.orderdish.ActivityOrderDishDetails.class).putExtra(com.delivery2go.base.orderdish.ActivityOrderDishDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishAddonsDetails.ADDON:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.addons.ActivityAddonsDetails.class).putExtra(com.delivery2go.base.addons.ActivityAddonsDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishAddonsDetails.UPDATEUSER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.user.ActivityUserDetails.class).putExtra(com.delivery2go.base.user.ActivityUserDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentOrderDishAddonsEdit.ORDERDISHADDONS_EDITED){
			load();
			getActivity().setResult(FragmentOrderDishAddonsEdit.ORDERDISHADDONS_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentOrderDishAddonsEdit.ORDERDISHADDONS_EDITED);
		super.onDeleted(data);
	}
}
