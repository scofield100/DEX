package com.delivery2go.base.dishcategories;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishCategories;
import com.delivery2go.base.data.repository.IDishCategoriesRepository;
import com.delivery2go.base.dishcategories.ViewModelDishCategoriesEdit;
import com.delivery2go.R;

public class FragmentDishCategoriesEdit extends BindableEditFragment {

	public static final int DISHCATEGORIES_EDITED=5;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityDishCategoriesDetails.ID);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_edit, menu);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dishcategories_fragment_edit, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.save:
				save();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DishComparer", new com.delivery2go.base.dish.DishComparer())
		.put("DishCategoryComparer", new com.delivery2go.base.dishcategory.DishCategoryComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHCATEGORIES");
		IDishCategoriesRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHCATEGORIES");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishCategoriesRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishCategoriesRepository();
		}
		return new ViewModelDishCategoriesEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(DISHCATEGORIES_EDITED);
		getActivity().finish();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras !=null && extras.containsKey(ActivityDishCategoriesDetails.ID)){
			getForm().getFieldByBinding("DishId").setEnabled(false);
			getForm().getFieldByBinding("CategoryId").setEnabled(false);
		}
	}

}
