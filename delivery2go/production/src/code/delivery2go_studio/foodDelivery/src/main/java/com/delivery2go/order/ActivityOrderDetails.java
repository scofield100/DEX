package com.delivery2go.order;

import android.app.Activity;
import android.os.Bundle;

import com.delivery2go.OrderStateEnum;
import com.delivery2go.R;

/**
 * Created by ansel on 28/08/2016.
 */
public class ActivityOrderDetails extends Activity implements OnOrderChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gen_activity_frame);

        int orderId =getIntent().getIntExtra(com.delivery2go.base.order.ActivityOrderDetails.ID, 0);

        if(savedInstanceState == null){
            FragmentOrderDetails fragment = new FragmentOrderDetails();
            Bundle args = new Bundle();
            args.putInt(com.delivery2go.base.order.ActivityOrderDetails.ID, orderId);
            fragment.setArguments(args);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onOrderChanged() {
        setResult(RESULT_OK);
    }

    @Override
    public void onOrderStateChange(int state) {
        if(state == OrderStateEnum.Cancelled || state == OrderStateEnum.Submited){
            setResult(RESULT_OK);
            finish();
        }
    }
}
