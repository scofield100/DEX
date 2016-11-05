package com.delivery2go.base.dishimages;

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
import com.delivery2go.base.models.DishImages;
import com.delivery2go.base.data.repository.IDishImagesRepository;
import com.delivery2go.base.dishimages.ViewModelDishImagesEdit;
import com.delivery2go.R;

public class FragmentDishImagesEdit extends BindableEditFragment {

	public static final int DISHIMAGES_EDITED=6;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityDishImagesDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_dishimages_fragment_edit, container, false);
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
		.put("ImageComparer", new com.delivery2go.base.image.ImageComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHIMAGES");
		IDishImagesRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHIMAGES");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishImagesRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishImagesRepository();
		}
		return new ViewModelDishImagesEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(DISHIMAGES_EDITED);
		getActivity().finish();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras !=null && extras.containsKey(ActivityDishImagesDetails.ID)){
			getForm().getFieldByBinding("DishId").setEnabled(false);
			getForm().getFieldByBinding("ImageId").setEnabled(false);
		}
	}

}
