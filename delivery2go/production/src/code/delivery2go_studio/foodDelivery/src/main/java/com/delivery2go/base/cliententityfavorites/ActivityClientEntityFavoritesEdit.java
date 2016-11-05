package com.delivery2go.base.cliententityfavorites;

import com.delivery2go.R;
import android.app.Activity;
import android.os.Bundle;
import com.delivery2go.base.data.RepositoryManager;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class ActivityClientEntityFavoritesEdit extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RepositoryManager.create(this);
		setContentView(R.layout.gen_activity_frame);
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentClientEntityFavoritesEdit())
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
