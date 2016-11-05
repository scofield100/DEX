package com.delivery2go.entity;

import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.R;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.DishCategory;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.delivery2go.base.entity.WrapperRepDish_EntityId;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.order.ActivityOrderDetails;
import com.delivery2go.order.FragmentOrderDetails;
import com.delivery2go.order.OnOrderChangeListener;
import com.enterlib.app.UIUtils;
import com.enterlib.data.IEntityCursor;
import com.enterlib.data.IRepository;
import com.enterlib.data.IRepositoryFactory;
import com.enterlib.data.QueryHelper;
import com.enterlib.data.WrapperRepository;
import com.enterlib.mvvm.ActivityView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

public class ActivityEntityWelcome extends ActivityView
        implements ActionBar.TabListener ,FragmentEntityHomeMenu.NavigationDrawerCallbacks,
        OnOrderChangeListener{

	int entityId;
	private ViewPager mViewPager;

    private ViewModelEntityHome viewModel;
	private ActionBar actionBar;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private FragmentEntityHomeMenu mNavigationDrawerFragment;
	private CharSequence mTitle;
	int selectedPosition = -1;
	private FrameLayout mContainer;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private int currentTabPosition;

    @Override
    public ViewModelEntityHome getViewModel() {
        return viewModel;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RepositoryManager.create(this);
		
		setContentView(R.layout.entity_activity);

		actionBar = getActionBar();				

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		mContainer = (FrameLayout) findViewById(R.id.container);

		int[]ids = (int[]) getIntent().getSerializableExtra(ActivityEntityDetails.ID);
		entityId = ids[0];
		
		viewModel = new ViewModelEntityHome(this, entityId);
        setViewModel(viewModel);

		mNavigationDrawerFragment = (FragmentEntityHomeMenu) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));		

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		if( mContainer == null){			
			return;
		}			
		
		FragmentManager fragmentManager = getFragmentManager();
		switch (position) {		
		case 0:
		{
            mViewPager.setAdapter(null);
			//Welcome
            mContainer.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			
			actionBar.removeAllTabs();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			fragmentManager .beginTransaction().replace(R.id.container, new FragmentEntityWelcome()).commit();
			break;	
		}					
		case 1:
		{
			//Offerts	
			
			Fragment fragment = fragmentManager.findFragmentById(R.id.container);
			if(fragment!=null){
				fragmentManager.beginTransaction().remove(fragment).commit();
			}

			mContainer.setVisibility(View.GONE);
			mViewPager.setVisibility(View.VISIBLE);
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.removeAllTabs();			
			mViewPager.setAdapter(mSectionsPagerAdapter);							
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {			               
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			}
			actionBar.setSelectedNavigationItem(currentTabPosition);

			break;
		}
		case 2:
		{
            mViewPager.setAdapter(null);
			//Favorites
			mContainer.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			
			actionBar.removeAllTabs();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			
			FragmentWelcomeDishList fragment =new FragmentWelcomeDishList();
			Bundle args = new Bundle();			
			args.putSerializable("REPOSITORY_DISH", DishFavoritesFactory.class);
			fragment.setArguments(args);
			
			fragmentManager .beginTransaction().replace(R.id.container, fragment).commit();		
			break;			
		}
		case 3:
            mViewPager.setAdapter(null);
			//Contacts
			mContainer.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			
			actionBar.removeAllTabs();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			fragmentManager .beginTransaction().replace(R.id.container, new FragmentEntityDetails()).commit();			
			break;
        case 4:
            mViewPager.setAdapter(null);
           //Location
            mContainer.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);

            actionBar.removeAllTabs();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            fragmentManager .beginTransaction().replace(R.id.container, new FragmentMap()).commit();
            break;
        case 5:
            Order order = viewModel.getCurrentOrder();
            if(order == null){
                UIUtils.showMessage(this, getString(R.string.msg_no_open_order));
                return;
            }

            mViewPager.setAdapter(null);
            //Location
            mContainer.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);

            actionBar.removeAllTabs();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);


            FragmentOrderDetails fragment = new FragmentOrderDetails();
            Bundle args = new Bundle();
            args.putInt(ActivityOrderDetails.ID, order.OrderId);
            fragment.setArguments(args);
            fragmentManager .beginTransaction().replace(R.id.container, fragment).commit();
            break;
		case 6:
			finish();
			break;
		default:
            mViewPager.setAdapter(null);
			mContainer.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			
			actionBar.removeAllTabs();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);				
			
//			fragmentManager
//			.beginTransaction()
//			.replace(R.id.container,
//					PlaceholderFragment.newInstance(position + 1)).commit();
			break;
		}
		
		selectedPosition = position;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
//		if(selectedPosition != 0){
//			if(actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS)
//				actionBar.removeAllTabs();
//			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		}
//		else{
//			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//			actionBar.removeAllTabs();	
//			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {			               
//				actionBar.addTab(actionBar.newTab()
//						.setText(mSectionsPagerAdapter.getPageTitle(i))
//						.setTabListener(this));
//			}
//		}
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
            if(selectedPosition == 0)
			    getMenuInflater().inflate(R.menu.drawer, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onDataLoaded() {						
		/*if(selectedPosition == -1){
			selectedPosition = 0;
		}*/
		
		//onNavigationDrawerItemSelected(selectedPosition);
		mTitle = viewModel.getEntity().Name;
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		currentTabPosition = tab.getPosition();
		mViewPager.setCurrentItem(currentTabPosition);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

    @Override
    public void onOrderChanged() {
        viewModel.load();
    }

	@Override
	public void onOrderStateChange(int state) {
		if(state == OrderStateEnum.Cancelled || state == OrderStateEnum.Submited){
			onNavigationDrawerItemSelected(0);
		}
	}

	class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {			
			if(position == 0){
				FragmentWelcomeDishList fragment =new FragmentWelcomeDishList();
				Bundle args = new Bundle();
				args.putInt("CATEGORY_ID", 0);
				args.putSerializable("REPOSITORY_DISH", DishRepositoryFactory.class);

				fragment.setArguments(args);
				return fragment;
			}
						
			ArrayList<DishCategory> categories = viewModel.getCategories();
			if(categories == null || categories.size() ==0)
				return null;

			DishCategory cat = categories.get(position - 1);
			FragmentWelcomeDishList fragment =new FragmentWelcomeDishList();
			Bundle args = new Bundle();
			args.putInt("CATEGORY_ID", cat.Id);
			args.putSerializable("REPOSITORY_DISH", DishRepositoryFactory.class);

			fragment.setArguments(args);
			return fragment;

		}

		@Override
		public int getCount() {
			ArrayList<DishCategory> categories = viewModel.getCategories();
			if(categories == null || categories.size() ==0)
				return 0;

			return categories.size() + 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			ArrayList<DishCategory> categories = viewModel.getCategories();
			if(categories == null || categories.size() ==0)
				return null;
			
			if(position == 0)
				return getString(R.string.all);
					
			return categories.get(position-1).Name;			
		}
	}

	public static class DishRepositoryFactory implements IRepositoryFactory{

		@Override
		public Object getInstance(Activity activity, Fragment fragment) {
			int catId = fragment.getArguments().getInt("CATEGORY_ID");
			int[] ids = (int[]) activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			IDishRepository repo;
			if(catId > 0){									
				repo = RepositoryManager.getInstance().getDishCategories_DishRepository(catId, false);				
			}else{
				repo = RepositoryManager.getInstance().getIDishRepository();
			}
			
			int entityId = ids[0];	
			return new WrapperRepDish_EntityId(repo, entityId);
		}
	}
	
	public static class DishFavoritesFactory implements IRepositoryFactory{

		@Override
		public Object getInstance(Activity activity, Fragment fragment) {
			
			int[] ids = (int[]) activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			int entityId = ids[0];
			Client client = DeliveryApp.getClient();

			IDishRepository repo = new FavoritesRepository(entityId,
					RepositoryManager.getInstance().getClientDishFavorites_DishRepository(client.Id, false));

			return repo;
		}
	}

	static class FavoritesRepository extends WrapperRepository<Dish> implements IDishRepository{
		int entityId;
		public FavoritesRepository(int entityId, IRepository<Dish> repository) {
			super(repository);

			this.entityId = entityId;
		}

		@Override
		public IEntityCursor<Dish> getCursor() {
			return super.getCursor(String.format("EntityId=%d", entityId));
		}

		@Override
		public IEntityCursor<Dish> getCursor(String condition) {
			return super.getCursor(QueryHelper.combine(condition,String.format("EntityId=%d", entityId)));
		}

		@Override
		public IEntityCursor<Dish> getCursor(String condition, String orderBy) {
			return super.getCursor(QueryHelper.combine(condition,String.format("EntityId=%d", entityId)), orderBy);
		}

		@Override
		public int getCount(String condition) {
			return super.getCount(QueryHelper.combine(condition,String.format("EntityId=%d", entityId)));
		}
	}
}
