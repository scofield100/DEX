package com.delivery2go.base.entity;

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
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.entity.ViewModelEntityEdit;
import com.delivery2go.R;

public class FragmentEntityEdit extends BindableEditFragment {

	public static final int ENTITY_EDITED=25;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_entity_fragment_edit, container, false);
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
		.put("IntegerConverterNullable", new com.enterlib.converters.IntegerToStringConverter(true))
		.put("IntegerConverter", new com.enterlib.converters.IntegerToStringConverter(false))
		.put("ImageComparer", new com.delivery2go.base.image.ImageComparer())
		.put("CityComparer", new com.delivery2go.base.city.CityComparer())
		.put("StateComparer", new com.delivery2go.base.state.StateComparer())
		.put("DoubleConverterNullable", new com.enterlib.converters.DoubleToStringConverter(true))
		.put("UserComparer", new com.delivery2go.base.user.UserComparer())
		.put("ImageConverter", new ThumbailDrawableConverter())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ENTITY");
		IEntityRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ENTITY");
		}

		if(factoryCls!=null){
			try {
				repository=(IEntityRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIEntityRepository();
		}
		return new ViewModelEntityEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(ENTITY_EDITED);
		getActivity().finish();
	}

}
