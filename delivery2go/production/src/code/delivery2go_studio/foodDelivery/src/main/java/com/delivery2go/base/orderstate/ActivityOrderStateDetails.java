package com.delivery2go.base.orderstate;

import com.delivery2go.R;
import android.app.Activity;
import android.os.Bundle;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.FragmentPage;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.ArrayList;

@AccessAnnotation(allows={Rolls.Administrator, Rolls.OrderState})
public class ActivityOrderStateDetails extends Activity implements ActionBar.TabListener {

	public static final String ID ="OrderState";
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gen_activity_page);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
			.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		getActionBar().setDisplayHomeAsUpEnabled(true);
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {			               
			        actionBar.addTab(actionBar.newTab()
					        .setText(mSectionsPagerAdapter.getPageTitle(i))
					        .setTabListener(this));
		      }
	}


        public class SectionsPagerAdapter extends FragmentPagerAdapter {

                ArrayList<FragmentPage>pages = new ArrayList<FragmentPage>();

		        public SectionsPagerAdapter(FragmentManager fm) {
			        super(fm);

                    addPages(new Class[]{
                              FragmentOrderStateDetails.class
							,FragmentOrderStateOrderList.class

                       },new String[]{
                                getString(R.string.orderstate)
							,getString(R.string.orders)

                       });
		        }

                void addPages(Class<?>[]fragments, String[]labels){
                    
                    for (int i = 0; i < fragments.length; i++) {
                        if(Access.hasAccess(fragments[i], Access.READ))
                            pages.add(new FragmentPage(getApplicationContext(), fragments[i], labels[i], null, null));
                    }
                }

		        @Override
		        public Fragment getItem(int position) {
			        return pages.get(position).newInstance();
		        }

		        @Override
		        public int getCount() {
			        return pages.size();
		        }

		        @Override
		        public CharSequence getPageTitle(int position) {
			       return pages.get(position).getLabel();
		        }

                public android.graphics.drawable.Drawable getIcon(int position){
                    return pages.get(position).getIcon();
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
