package com.delivery2go.base.addons;

import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.base.entity.ActivityEntityDetails;
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
import com.delivery2go.base.models.Addons;
import com.delivery2go.base.data.repository.IAddonsRepository;
import com.delivery2go.base.addons.ViewModelAddonsList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Addons})
public class FragmentAddonsList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Addons}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Addons}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_addons_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityAddonsList.class)
					.putExtra(ActivityAddonsDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityAddonsDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentAddonsEdit.ADDONS_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityAddonsEdit.class), FragmentAddonsEdit.ADDONS_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("CurrencyConverter",  new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_ADDONS");
		IAddonsRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_ADDONS");
		}

		if(factoryCls!=null){
			try {
				repository=(IAddonsRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIAddonsRepository();
		}
		return new ViewModelAddonsList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new StringFilterCondition<Addons>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, Addons item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}
			/*,new SpinnerFilterCondition<Addons,Entity>(getActivity(),"EntityId", getActivity().getString(R.string.entityid)){
				protected boolean eval(Entity value, Addons item){
					return value.Id==0 || value.Id==item.EntityId;
				}
				public void update(){
					ViewModelAddonsList viewModel = ViewModelAddonsListgetViewModel();
					setItems(viewModel.getEntitiesOptional());
				}
			}*/
			,new DoubleFilterCondition<Addons>(getActivity(),"Price", getActivity().getString(R.string.price_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, Addons item){
					return value == null || item.Price >= value;
				}
			}
			,new DoubleFilterCondition<Addons>(getActivity(),"Price", getActivity().getString(R.string.price_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, Addons item){
					return value == null || item.Price <= value;
				}
			}
			,new SwitchCondition<Addons>(getActivity(),"IsAvailable", getActivity().getString(R.string.isavailable), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Addons item){
					return value == null || item.IsAvailable == value;
				}
			}
			,new SwitchCondition<Addons>(getActivity(),"IsDressing", getActivity().getString(R.string.isdressing), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Addons item){
					return value == null || item.IsDressing == value;
				}
			}
			,new IntegerFilterCondition<Addons>(getActivity(),"AvailableCount", getActivity().getString(R.string.availablecount_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, Addons item){
					return value == null || (item.AvailableCount != null && item.AvailableCount >= value);
				}
			}
			,new IntegerFilterCondition<Addons>(getActivity(),"AvailableCount", getActivity().getString(R.string.availablecount_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, Addons item){
					return value == null || (item.AvailableCount != null && item.AvailableCount <= value);
				}
			}
			/*,new SpinnerFilterCondition<Addons,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, Addons item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelAddonsList viewModel = ViewModelAddonsListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityAddonsDetails.class);
			i.putExtra(ActivityAddonsDetails.ID, new int[]{((Addons)data).Id});
			i.putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID));
			startActivityForResult(i,FragmentAddonsEdit.ADDONS_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentAddonsEdit.ADDONS_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentAddonsEdit.ADDONS_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentAddonsEdit.ADDONS_EDITED);
		load();
	}

}
