package com.delivery2go.base.clientdishfavorites;

import com.delivery2go.R;
import android.app.Activity;
import android.os.Bundle;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;

@AccessAnnotation(allows={Rolls.Administrator, Rolls.ClientDishFavorites})
public class ActivityClientDishFavoritesList extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RepositoryManager.create(this);
		setContentView(R.layout.gen_activity_frame);
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
			.add(R.id.content, new FragmentClientDishFavoritesList())
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
