package com.delivery2go.base.dishreview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.DishReview;
import com.delivery2go.base.data.repository.IDishReviewRepository;
import com.delivery2go.base.dishreview.ViewModelDishReviewEdit;
import com.delivery2go.R;

public class FragmentDishReviewEdit extends BindableEditFragment implements View.OnClickListener {

	public static final int DISHREVIEW_EDITED=28;

	public int[] getIds(){
		return (int[])getActivity().getIntent().getSerializableExtra(ActivityDishReviewDetails.ID);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.model_edit, menu);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dishreview_fragment_edit, container, false);
		Button cancel = (Button) view.findViewById(R.id.btnCancel);
		Button send = (Button) view.findViewById(R.id.btnSend);
		cancel.setOnClickListener(this);
        send.setOnClickListener(this);
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
		.put("IntegerConverter", new com.enterlib.converters.IntegerToStringConverter(false))
		.put("ClientComparer", new com.delivery2go.base.client.ClientComparer())
		.put("DishComparer", new com.delivery2go.base.dish.DishComparer())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHREVIEW");
		IDishReviewRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHREVIEW");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishReviewRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishReviewRepository();
		}
		return new ViewModelDishReviewEdit(this, repository);
	}

	@Override
	protected void onFinishActivity() {
		getActivity().setResult(DISHREVIEW_EDITED);
		getActivity().finish();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnCancel){
			getActivity().setResult(Activity.RESULT_CANCELED);
			getActivity().finish();
		}else if(v.getId() == R.id.btnSend){
			save();
		}
	}
}
