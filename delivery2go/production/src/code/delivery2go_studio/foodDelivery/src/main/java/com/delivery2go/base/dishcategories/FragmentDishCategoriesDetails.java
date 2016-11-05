package com.delivery2go.base.dishcategories;

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
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.dishcategories.ViewModelDishCategoriesDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.DishCategories})
public class FragmentDishCategoriesDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategories}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategories}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dishcategories_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishCategoriesEdit.class).putExtra(ActivityDishCategoriesDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishCategoriesDetails.ID)), FragmentDishCategoriesEdit.DISHCATEGORIES_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentDishCategoriesDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityDishCategoriesList.class).putExtra(ActivityDishCategoriesDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishCategoriesDetails.ID)));
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
		return new ViewModelDishCategoriesDetails(this, RepositoryManager.getInstance().getIDishCategoriesRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityDishCategoriesDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelDishCategoriesDetails.DISH:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.dish.ActivityDishDetails.class).putExtra(com.delivery2go.base.dish.ActivityDishDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelDishCategoriesDetails.CATEGORY:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.dishcategory.ActivityDishCategoryDetails.class).putExtra(com.delivery2go.base.dishcategory.ActivityDishCategoryDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishCategoriesEdit.DISHCATEGORIES_EDITED){
			load();
			getActivity().setResult(FragmentDishCategoriesEdit.DISHCATEGORIES_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishCategoriesEdit.DISHCATEGORIES_EDITED);
		super.onDeleted(data);
	}
}
