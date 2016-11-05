package com.delivery2go.base.dishsizeprice;

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
import com.delivery2go.base.models.DishSizePrice;
import com.delivery2go.base.data.repository.IDishSizePriceRepository;
import com.delivery2go.base.dishsizeprice.ViewModelDishSizePriceEdit;
import com.delivery2go.R;

public class FragmentDishSizePriceEdit extends BindableEditFragment {

	public static final int DISHSIZEPRICE_EDITED=18;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityDishSizePriceDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_dishsizeprice_fragment_edit, container, false);
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
		.put("DishSizeComparer", new com.delivery2go.base.dishsize.DishSizeComparer())
		.put("DishComparer", new com.delivery2go.base.dish.DishComparer())
		.put("DoubleConverter", new com.enterlib.converters.DoubleToStringConverter(false))
		.put("IntegerConverterNullable", new com.enterlib.converters.IntegerToStringConverter(true))
		.put("UserComparer", new com.delivery2go.base.user.UserComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHSIZEPRICE");
		IDishSizePriceRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHSIZEPRICE");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishSizePriceRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishSizePriceRepository();
		}
		return new ViewModelDishSizePriceEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(DISHSIZEPRICE_EDITED);
		getActivity().finish();
	}

}
