package com.delivery2go.console;

import com.delivery2go.R;
import android.app.Activity;
import android.os.Bundle;

import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.delivery2go.base.client.FragmentClientDetails;
import com.delivery2go.base.data.RepositoryManager;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;

public class ActivityClientDetails extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RepositoryManager.create(this);
		setContentView(R.layout.gen_activity_frame);
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentClientDetails())
			.commit();
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onNavigateUp() {
		finish();
		return true;
	}

	public static class FragmentClientDetails extends com.delivery2go.base.client.FragmentClientDetails{
		public FragmentClientDetails(){

		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.menu_client_details, menu);
		}

	}

}
