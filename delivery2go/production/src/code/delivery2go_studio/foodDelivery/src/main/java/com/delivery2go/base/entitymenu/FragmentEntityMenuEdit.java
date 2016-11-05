package com.delivery2go.base.entitymenu;

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
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.entitymenu.ViewModelEntityMenuEdit;
import com.delivery2go.R;

public class FragmentEntityMenuEdit extends BindableEditFragment {

	public static final int ENTITYMENU_EDITED=19;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID);
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
		View view = inflater.inflate(R.layout.gen_entitymenu_fragment_edit, container, false);
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
		.put("EntityComparer", new com.delivery2go.base.entity.EntityComparer())
		.put("ImageComparer", new com.delivery2go.base.image.ImageComparer())
		.put("UserComparer", new com.delivery2go.base.user.UserComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ENTITYMENU");
		IEntityMenuRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ENTITYMENU");
		}

		if(factoryCls!=null){
			try {
				repository=(IEntityMenuRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIEntityMenuRepository();
		}
		return new ViewModelEntityMenuEdit(this, repository, getIds());
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(ENTITYMENU_EDITED);
		getActivity().finish();
	}

}
