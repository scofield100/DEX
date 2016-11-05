package com.delivery2go.base.dashboard;

import com.delivery2go.R;

import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Rolls;
import com.delivery2go.base.data.RepositoryManager;
import android.app.Activity;
import android.os.Bundle;


@AccessAnnotation(allows={Rolls.Administrator})
public class ActivityDashboard extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);

		RepositoryManager.create(this);
		
		setContentView(R.layout.gen_activity_frame);
		
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentDashboard())
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
