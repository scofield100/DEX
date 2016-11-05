package com.delivery2go.base.dishimages;

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

@AccessAnnotation(allows={Rolls.Administrator, Rolls.DishImages})
public class ActivityDishImagesDetails extends Activity{

public static final String ID ="DishImages";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RepositoryManager.create(this);
		setContentView(R.layout.gen_activity_frame);
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentDishImagesDetails())
			.commit();
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onNavigateUp() {
		finish();
		return true;
	}

}
