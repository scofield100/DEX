package com.delivery2go.entity;

import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import android.app.Activity;
import android.os.Bundle;

public class ActivityOrderDish extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RepositoryManager.create(this);
		
		setContentView(R.layout.gen_activity_frame);
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentOrderDish())
			.commit();
		}

        setResult(RESULT_CANCELED);
	}
	
}
