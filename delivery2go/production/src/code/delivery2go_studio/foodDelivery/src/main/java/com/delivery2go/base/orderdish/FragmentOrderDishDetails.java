package com.delivery2go.base.orderdish;

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
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.orderdish.ViewModelOrderDishDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.OrderDish})
public class FragmentOrderDishDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDish}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.OrderDish}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_orderdish_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityOrderDishEdit.class).putExtra(ActivityOrderDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishDetails.ID)), FragmentOrderDishEdit.ORDERDISH_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentOrderDishDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityOrderDishList.class).putExtra(ActivityOrderDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishDetails.ID)));
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
		return new ViewModelOrderDishDetails(this, RepositoryManager.getInstance().getIOrderDishRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelOrderDishDetails.ORDER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.order.ActivityOrderDetails.class).putExtra(com.delivery2go.base.order.ActivityOrderDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishDetails.DISH:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.dish.ActivityDishDetails.class).putExtra(com.delivery2go.base.dish.ActivityDishDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishDetails.SIZE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.dishsizeprice.ActivityDishSizePriceDetails.class).putExtra(com.delivery2go.base.dishsizeprice.ActivityDishSizePriceDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishDetails.DRESSING:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.addons.ActivityAddonsDetails.class).putExtra(com.delivery2go.base.addons.ActivityAddonsDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDishDetails.UPDATEUSER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.user.ActivityUserDetails.class).putExtra(com.delivery2go.base.user.ActivityUserDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentOrderDishEdit.ORDERDISH_EDITED){
			load();
			getActivity().setResult(FragmentOrderDishEdit.ORDERDISH_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentOrderDishEdit.ORDERDISH_EDITED);
		super.onDeleted(data);
	}
}
