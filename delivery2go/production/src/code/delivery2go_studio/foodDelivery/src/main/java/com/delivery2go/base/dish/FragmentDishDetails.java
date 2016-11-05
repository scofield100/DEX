package com.delivery2go.base.dish;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.dish.ViewModelDishDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Dish})
public class FragmentDishDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dish_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishEdit.class)
						.putExtra(ActivityDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID))
						.putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID))
						, FragmentDishEdit.DISH_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentDishDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityDishList.class).putExtra(ActivityDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID)));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("CurrencyConverter",  new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		.put("RankingConverter", new RantingConverter(getActivity()))
        .put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.adapter_image),(int)getResources().getDimension(R.dimen.adapter_image)))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelDishDetails(this, RepositoryManager.getInstance().getIDishRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelDishDetails.IMAGE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.image.ActivityImageDetails.class).putExtra(com.delivery2go.base.image.ActivityImageDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelDishDetails.ENTITY:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.entity.ActivityEntityDetails.class).putExtra(com.delivery2go.base.entity.ActivityEntityDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelDishDetails.MENU:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.entitymenu.ActivityEntityMenuDetails.class).putExtra(com.delivery2go.base.entitymenu.ActivityEntityMenuDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelDishDetails.UPDATEUSER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.user.ActivityUserDetails.class).putExtra(com.delivery2go.base.user.ActivityUserDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishEdit.DISH_EDITED){
			load();
			getActivity().setResult(FragmentDishEdit.DISH_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishEdit.DISH_EDITED);
		super.onDeleted(data);
	}
}
