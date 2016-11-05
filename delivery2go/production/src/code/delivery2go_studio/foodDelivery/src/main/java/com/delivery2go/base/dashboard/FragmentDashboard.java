package com.delivery2go.base.dashboard;

import com.delivery2go.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.enterlib.StringUtils;
import com.enterlib.fields.Field;
import com.enterlib.fields.ListField;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.SelectionCommand;

public class FragmentDashboard extends ActionBarFilterableFragment {
	
	public static class Module{
		public String Name;	
		public Class<? extends Activity> ActivityClass;
		
		public Module(String name, Class<? extends Activity> ActivityClass) {
			this.Name = name;
			this.ActivityClass = ActivityClass;
		}
		
		public void startActivity(Context context){
			context.startActivity(new Intent(context.getApplicationContext(), ActivityClass));
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSearchItemId(R.id.action_search);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.gen_fragment_dashboard, container, false);
	}	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_dashboard, menu);
		onInitSearch(menu);
	}
	
	@Override
	protected SearchViewFilterController createFilterController(
			Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
				,new StringFilterCondition<Module>("Name", getActivity().getString(R.string.name)){
					@Override
					protected boolean eval(String prefix, Module item){
						return StringUtils.startsWordWith(prefix, item.Name.toLowerCase());
					}
				});
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new DashboarViewModel(getActivity(), this, new Module[]{
				new Module(getString(R.string.entitycategory), com.delivery2go.base.entitycategory.ActivityEntityCategoryList.class)
				,new Module(getString(R.string.state), com.delivery2go.base.state.ActivityStateList.class)
				,new Module(getString(R.string.city), com.delivery2go.base.city.ActivityCityList.class)
				,new Module(getString(R.string.client), com.delivery2go.base.client.ActivityClientList.class)
				,new Module(getString(R.string.orderstate), com.delivery2go.base.orderstate.ActivityOrderStateList.class)
				,new Module(getString(R.string.ordertype), com.delivery2go.base.ordertype.ActivityOrderTypeList.class)
				,new Module(getString(R.string.dishsize), com.delivery2go.base.dishsize.ActivityDishSizeList.class)
				,new Module(getString(R.string.dishcategory), com.delivery2go.base.dishcategory.ActivityDishCategoryList.class)
				,new Module(getString(R.string.dishsizeprice), com.delivery2go.base.dishsizeprice.ActivityDishSizePriceList.class)
			
		});
	}
	
	static class DashboarViewModel extends DataViewModel{
		private Module[]items;
		private Activity activity;
		
		public Module[]getItems(){
			return items;
		}
		
		public DashboarViewModel(Activity activity, IDataView view, Module[] items) {
			super(view);
			this.items = items;
			this.activity =activity;
		}
		
		@Override
		protected boolean loadAsync() throws Exception {
			return true;
		}
		
		public SelectionCommand Selection = new SelectionCommand() {
			
			@Override
			public void invoke(Field field, AdapterView<?> adapterView, View itemView,
					int position, long id) {
				
				Module module = (Module) adapterView.getItemAtPosition(position);
				module.startActivity(activity);
			}
		};
		
	}
}
