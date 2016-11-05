package com.delivery2go.base.orderdishaddons;

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
import com.delivery2go.base.models.OrderDishAddons;
import com.delivery2go.base.data.repository.IOrderDishAddonsRepository;
import com.delivery2go.base.orderdishaddons.ViewModelOrderDishAddonsEdit;
import com.delivery2go.R;

public class FragmentOrderDishAddonsEdit extends BindableEditFragment {

	public static final int ORDERDISHADDONS_EDITED=23;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDishAddonsDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_orderdishaddons_fragment_edit, container, false);
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
		.put("OrderDishComparer", new com.delivery2go.base.orderdish.OrderDishComparer())
		.put("AddonsComparer", new com.delivery2go.base.addons.AddonsComparer())
		.put("DoubleConverter", new com.enterlib.converters.DoubleToStringConverter(false))
		.put("UserComparer", new com.delivery2go.base.user.UserComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ORDERDISHADDONS");
		IOrderDishAddonsRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ORDERDISHADDONS");
		}

		if(factoryCls!=null){
			try {
				repository=(IOrderDishAddonsRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIOrderDishAddonsRepository();
		}
		return new ViewModelOrderDishAddonsEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(ORDERDISHADDONS_EDITED);
		getActivity().finish();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras !=null && extras.containsKey(ActivityOrderDishAddonsDetails.ID)){
			getForm().getFieldByBinding("OrderDishId").setEnabled(false);
			getForm().getFieldByBinding("AddonId").setEnabled(false);
		}
	}

}
