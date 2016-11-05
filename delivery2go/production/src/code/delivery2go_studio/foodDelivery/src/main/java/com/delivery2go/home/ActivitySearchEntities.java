package com.delivery2go.home;

import com.delivery2go.R;
import com.enterlib.mvvm.ActivityView;
import com.enterlib.widgets.ProgressLayout;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySearchEntities extends ActivityView implements ActionBar.TabListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	ViewModelSearchEntity viewModel;
	private TextView statusView;
	
	

	public ViewModelSearchEntity getViewModel() {
		return viewModel;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity_search_entity);

		
		Intent intent = getIntent();
		String adress = null;
		Double lng = null;
		Double lat = null;
		if(intent.hasExtra(ViewModelHome.ADRESS))
			adress = intent.getStringExtra(ViewModelHome.ADRESS);
		if(intent.hasExtra(ViewModelHome.LATITUDE))
			lat=intent.getDoubleExtra(ViewModelHome.LATITUDE, 0);
		if(intent.hasExtra(ViewModelHome.LONGITUDE))
			lng=intent.getDoubleExtra(ViewModelHome.LONGITUDE, 0);

		if(lng == null && lat == null){
			Toast.makeText(this, "Coordinates not found, using testing values", Toast.LENGTH_SHORT).show();
		}

		statusView = (TextView) findViewById(R.id.status);
		ProgressLayout progress = (ProgressLayout)findViewById(R.id.container);
		setProgressLayout(progress);
		
		viewModel = new ViewModelSearchEntity(this, adress, lat, lng);		
		setViewModel(viewModel);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);				
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionBarColor)));
		
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()					
					.setText(mSectionsPagerAdapter.getPageTitle(i))					
					.setTabListener(this));
		}		
		
	}
	

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if(position == 0){
				return new FragmentEntitySearchList();
			}
			else if(position == 1){
				return new FragmentDishWhatsGoodList();
			}			
			return null;

		}

		@Override
		public int getCount() {
			return 2;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			if(position == 0){
				return getString(R.string.restaurants);
			}
			else if(position == 1){
				return getString(R.string.what_s_good);
			}			
			return null;

		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
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
	public boolean onNavigateUp() {
		finish();
		return true;
	}
	
}
