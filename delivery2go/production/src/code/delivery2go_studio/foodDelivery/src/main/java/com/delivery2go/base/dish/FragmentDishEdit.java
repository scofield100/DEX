package com.delivery2go.base.dish;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.ThumbailDrawableConverter;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.dish.ViewModelDishEdit;
import com.delivery2go.R;

public class FragmentDishEdit extends BindableEditFragment {

	public static final int DISH_EDITED=16;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_dish_fragment_edit, container, false);
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
		.put("ImageComparer", new com.delivery2go.base.image.ImageComparer())
		.put("IntegerConverterNullable", new com.enterlib.converters.IntegerToStringConverter(true))
		.put("DoubleConverter", new com.enterlib.converters.DoubleToStringConverter(false))
		.put("EntityComparer", new com.delivery2go.base.entity.EntityComparer())
		.put("EntityMenuComparer", new com.delivery2go.base.entitymenu.EntityMenuComparer())
		.put("UserComparer", new com.delivery2go.base.user.UserComparer())
		.put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.spinner_image),(int)getResources().getDimension(R.dimen.spinner_image)))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISH");
		IDishRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISH");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishRepository();
		}
		return new ViewModelDishEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(DISH_EDITED);
		getActivity().finish();
	}

}
