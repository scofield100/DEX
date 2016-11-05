package com.delivery2go.home;

import android.app.Activity;
import android.os.Bundle;

import com.delivery2go.R;

/**
 * Created by ansel on 30/09/2016.
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}
