package com.delivery2go.base.dishcategory;

import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.enterlib.StringUtils;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.fields.ListField;
import com.enterlib.databinding.BindingResources;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.SpinnerFilterCondition;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.filtering.DateFilterCondition;
import com.enterlib.filtering.TimeFilterCondition;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.widgets.ProgressLayout;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.data.repository.IDishCategoryRepository;
import com.delivery2go.base.dishcategory.ViewModelDishCategoryList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.DishCategory})
public class FragmentDishCategoryList extends ActionBarFilterableFragment {
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list, menu);
		if(getActivity().getIntent().getBooleanExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE, false)){
			menu.findItem(R.id.delete).setTitle(R.string.select);
			menu.findItem(R.id.delete).setIcon(R.drawable.ic_action_list_2);

			menu.findItem(R.id.create).setEnabled(false);
			menu.findItem(R.id.create).setVisible(false);

			onInitDelete(menu);
		} else {

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategory}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.DishCategory}, Access.CREATE)){
				menu.findItem(R.id.create).setEnabled(false);
				menu.findItem(R.id.create).setVisible(false);
			}
		}
		onInitSearch(menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSearchItemId(R.id.action_search);
		setDeleteItemId(R.id.delete);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dishcategory_fragment_list, container, false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishCategoryList.class)
					.putExtra(ActivityDishCategoryDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishCategoryDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityDishCategoryEdit.class), FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISHCATEGORY");
		IDishCategoryRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISHCATEGORY");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishCategoryRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishCategoryRepository();
		}
		return new ViewModelDishCategoryList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new StringFilterCondition<DishCategory>("Code", getActivity().getString(R.string.code)){
				protected boolean eval(String prefix, DishCategory item){
					return StringUtils.startsWordWith(prefix, item.Code);
				}
			}
			,new StringFilterCondition<DishCategory>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, DishCategory item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}
			/*,new SpinnerFilterCondition<DishCategory,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, DishCategory item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelDishCategoryList viewModel = ViewModelDishCategoryListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
			,new DateFilterCondition<DishCategory>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Date date, DishCategory item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) >= 0);
				}
			}
			,new DateFilterCondition<DishCategory>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, DishCategory item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) <= 0);
				}
			}
			,new StringFilterCondition<DishCategory>("UpdateUserName", getActivity().getString(R.string.updateusername)){
				protected boolean eval(String prefix, DishCategory item){
					return StringUtils.startsWordWith(prefix, item.UpdateUserName);
				}
			}

		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityDishCategoryDetails.class);
			i.putExtra(ActivityDishCategoryDetails.ID, new int[]{((DishCategory)data).Id});
			startActivityForResult(i,FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishCategoryEdit.DISHCATEGORY_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishCategoryEdit.DISHCATEGORY_EDITED);
		load();
	}

}
