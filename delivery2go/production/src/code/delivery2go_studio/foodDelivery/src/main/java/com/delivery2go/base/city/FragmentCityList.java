package com.delivery2go.base.city;

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
import com.delivery2go.base.models.City;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.city.ViewModelCityList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.City})
public class FragmentCityList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.City}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.City}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_city_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityCityList.class)
					.putExtra(ActivityCityDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityCityDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentCityEdit.CITY_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityCityEdit.class), FragmentCityEdit.CITY_EDITED);
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
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_CITY");
		ICityRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_CITY");
		}

		if(factoryCls!=null){
			try {
				repository=(ICityRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getICityRepository();
		}
		return new ViewModelCityList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new StringFilterCondition<City>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, City item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}
			/*,new SpinnerFilterCondition<City,State>(getActivity(),"StateId", getActivity().getString(R.string.stateid)){
				protected boolean eval(State value, City item){
					return value.Id==0 || value.Id==item.StateId;
				}
				public void update(){
					ViewModelCityList viewModel = ViewModelCityListgetViewModel();
					setItems(viewModel.getStatesOptional());
				}
			}*/
			,new StringFilterCondition<City>("StateName", getActivity().getString(R.string.statename)){
				protected boolean eval(String prefix, City item){
					return StringUtils.startsWordWith(prefix, item.StateName);
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
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityCityDetails.class);
			i.putExtra(ActivityCityDetails.ID, new int[]{((City)data).Id});
			startActivityForResult(i,FragmentCityEdit.CITY_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentCityEdit.CITY_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentCityEdit.CITY_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentCityEdit.CITY_EDITED);
		load();
	}

}
