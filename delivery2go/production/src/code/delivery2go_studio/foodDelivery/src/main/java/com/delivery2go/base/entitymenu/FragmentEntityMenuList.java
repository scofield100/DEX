package com.delivery2go.base.entitymenu;

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
import com.delivery2go.base.models.EntityMenu;
import com.delivery2go.base.data.repository.IEntityMenuRepository;
import com.delivery2go.base.entitymenu.ViewModelEntityMenuList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.EntityMenu})
public class FragmentEntityMenuList extends ActionBarFilterableFragment {
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

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.EntityMenu}, Access.CREATE)){
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
		View view = inflater.inflate(R.layout.gen_entitymenu_fragment_list, container, false);
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
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityEntityMenuList.class)
					.putExtra(ActivityEntityMenuDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityMenuDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentEntityMenuEdit.ENTITYMENU_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityEntityMenuEdit.class), FragmentEntityMenuEdit.ENTITYMENU_EDITED);
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
		return new ViewModelEntityMenuList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new StringFilterCondition<EntityMenu>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, EntityMenu item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}
			/*,new SpinnerFilterCondition<EntityMenu,Entity>(getActivity(),"EntityId", getActivity().getString(R.string.entityid)){
				protected boolean eval(Entity value, EntityMenu item){
					return value.Id==0 || value.Id==item.EntityId;
				}
				public void update(){
					ViewModelEntityMenuList viewModel = ViewModelEntityMenuListgetViewModel();
					setItems(viewModel.getEntitiesOptional());
				}
			}*/
			,new SwitchCondition<EntityMenu>(getActivity(),"IsSpecial", getActivity().getString(R.string.isspecial), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, EntityMenu item){
					return value == null || item.IsSpecial == value;
				}
			}
			,new SwitchCondition<EntityMenu>(getActivity(),"IsAvailable", getActivity().getString(R.string.isavailable), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, EntityMenu item){
					return value == null || item.IsAvailable == value;
				}
			}
			/*,new SpinnerFilterCondition<EntityMenu,Image>(getActivity(),"ImageId", getActivity().getString(R.string.imageid)){
				protected boolean eval(Image value, EntityMenu item){
					return value.Id==0 || value.Id==item.ImageId;
				}
				public void update(){
					ViewModelEntityMenuList viewModel = ViewModelEntityMenuListgetViewModel();
					setItems(viewModel.getImagesOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<EntityMenu,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, EntityMenu item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelEntityMenuList viewModel = ViewModelEntityMenuListgetViewModel();
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
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityMenuDetails.class);
			i.putExtra(ActivityEntityMenuDetails.ID, new int[]{((EntityMenu)data).Id});
			i.putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID));
			startActivityForResult(i,FragmentEntityMenuEdit.ENTITYMENU_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentEntityMenuEdit.ENTITYMENU_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentEntityMenuEdit.ENTITYMENU_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentEntityMenuEdit.ENTITYMENU_EDITED);
		load();
	}

}
