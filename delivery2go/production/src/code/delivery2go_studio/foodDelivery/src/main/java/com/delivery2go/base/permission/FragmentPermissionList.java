package com.delivery2go.base.permission;

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
import com.delivery2go.base.models.Permission;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.permission.ViewModelPermissionList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Permission})
public class FragmentPermissionList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Permission}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Permission}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_permission_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityPermissionList.class)
					.putExtra(ActivityPermissionDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityPermissionDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentPermissionEdit.PERMISSION_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityPermissionEdit.class), FragmentPermissionEdit.PERMISSION_EDITED);
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
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_PERMISSION");
		IPermissionRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_PERMISSION");
		}

		if(factoryCls!=null){
			try {
				repository=(IPermissionRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIPermissionRepository();
		}
		return new ViewModelPermissionList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			/*,new SpinnerFilterCondition<Permission,Roll>(getActivity(),"RollId", getActivity().getString(R.string.rollid)){
				protected boolean eval(Roll value, Permission item){
					return value.Id==0 || value.Id==item.RollId;
				}
				public void update(){
					ViewModelPermissionList viewModel = ViewModelPermissionListgetViewModel();
					setItems(viewModel.getRollsOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<Permission,User>(getActivity(),"UserId", getActivity().getString(R.string.userid)){
				protected boolean eval(User value, Permission item){
					return value.Id==0 || value.Id==item.UserId;
				}
				public void update(){
					ViewModelPermissionList viewModel = ViewModelPermissionListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
			,new SwitchCondition<Permission>(getActivity(),"CanRead", getActivity().getString(R.string.canread), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Permission item){
					return value == null || item.CanRead == value;
				}
			}
			,new SwitchCondition<Permission>(getActivity(),"CanWrite", getActivity().getString(R.string.canwrite), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Permission item){
					return value == null || item.CanWrite == value;
				}
			}
			,new SwitchCondition<Permission>(getActivity(),"CanCreate", getActivity().getString(R.string.cancreate), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Permission item){
					return value == null || item.CanCreate == value;
				}
			}
			,new SwitchCondition<Permission>(getActivity(),"CanDelete", getActivity().getString(R.string.candelete), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Permission item){
					return value == null || item.CanDelete == value;
				}
			}
			,new DateFilterCondition<Permission>(getActivity(),"CreateDate", getActivity().getString(R.string.createdate_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Date date, Permission item){
					return date == null || (item.CreateDate != null && item.CreateDate.compareTo(date) >= 0);
				}
			}
			,new DateFilterCondition<Permission>(getActivity(),"CreateDate", getActivity().getString(R.string.createdate_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, Permission item){
					return date == null || (item.CreateDate != null && item.CreateDate.compareTo(date) <= 0);
				}
			}
			,new DateFilterCondition<Permission>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Date date, Permission item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) >= 0);
				}
			}
			,new DateFilterCondition<Permission>(getActivity(),"UpdateDate", getActivity().getString(R.string.updatedate_to),FilterOp.LESS_EQUALS){
				protected boolean eval(Date date, Permission item){
					return date == null || (item.UpdateDate != null && item.UpdateDate.compareTo(date) <= 0);
				}
			}
			,new StringFilterCondition<Permission>("RollName", getActivity().getString(R.string.rollname)){
				protected boolean eval(String prefix, Permission item){
					return StringUtils.startsWordWith(prefix, item.RollName);
				}
			}
			,new StringFilterCondition<Permission>("UserName", getActivity().getString(R.string.username)){
				protected boolean eval(String prefix, Permission item){
					return StringUtils.startsWordWith(prefix, item.UserName);
				}
			}

		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Permission}, Access.READ)){
				return;
			}

			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityPermissionDetails.class);
			i.putExtra(ActivityPermissionDetails.ID, new int[]{((Permission)data).Id});
			startActivityForResult(i,FragmentPermissionEdit.PERMISSION_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentPermissionEdit.PERMISSION_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentPermissionEdit.PERMISSION_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentPermissionEdit.PERMISSION_EDITED);
		load();
	}

}
