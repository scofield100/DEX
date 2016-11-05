package com.delivery2go.console;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.Access;
import com.delivery2go.base.entity.ActivityEntityList;
import com.delivery2go.base.order.ActivityOrderList;
import com.delivery2go.base.user.ActivityCurrentUserDetails;
import com.delivery2go.base.user.ActivityUserDetails;
import com.delivery2go.base.user.ActivityUserList;
import com.enterlib.threading.AsyncManager;
import com.enterlib.threading.IAsyncInvocator;

public class ActivityDashboard extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.console_dashboard);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        if(!Access.hasAccess(ActivityEntityList.class, Access.READ)){
            findViewById(R.id.restaurants).setEnabled(false);

        }

        if(!Access.hasAccess(ActivityUserList.class, Access.READ)){
            findViewById(R.id.users).setEnabled(false);
        }

        if(!Access.hasAccess(com.delivery2go.base.dashboard.ActivityDashboard.class, Access.READ)){
            findViewById(R.id.nomenclators).setEnabled(false);
        }

        getActionBar().setSubtitle(R.string.administration_console);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    public void onClick(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_enter);
        switch (view.getId()) {
            case R.id.restaurants:
                animation.setAnimationListener(new ButtonAnimationListener(ActivityEntityList.class));
                break;
            case R.id.users:
                animation.setAnimationListener(new ButtonAnimationListener(ActivityUserList.class));
                break;
            case R.id.orders:
                animation.setAnimationListener(new ButtonAnimationListener(new Intent(getApplicationContext(), ActivityCurrentUserDetails.class)
                        .putExtra(ActivityUserDetails.ID, new int[]{DeliveryApp.getUser().Id})));
                break;
            case R.id.nomenclators:
                animation.setAnimationListener(new ButtonAnimationListener(com.delivery2go.base.dashboard.ActivityDashboard.class));
                break;
            default:
                break;
        }
        view.startAnimation(animation);
    }

    class ButtonAnimationListener implements AnimationListener{
        Class<? extends Activity>activityClass;
        android.content.Intent intent;

        public ButtonAnimationListener(Class<? extends Activity>activityClass) {
            this.activityClass = activityClass;

            intent =new Intent(getApplicationContext(), activityClass);
        }

        public ButtonAnimationListener(Intent intent){
            this.intent = intent;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            AsyncManager.InvokeAsync(new IAsyncInvocator() {

                @Override
                public void OnAsyncOperationException(Exception e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void OnAsyncOperationComplete() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void DoAsyncOperation() throws Exception {
                    try{
                        Thread.sleep(300);
                    }catch (InterruptedException e) {
                        // TODO: handle exception
                    }
                    startActivity(intent);
                }
            });

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//			AsyncManager.InvokeAsync(new IAsyncInvocator() {
//				
//				@Override
//				public void OnAsyncOperationException(Exception e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void OnAsyncOperationComplete() {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void DoAsyncOperation() throws Exception {
//					startActivity(new Intent(getApplicationContext(), activityClass));					
//				}
//			});
        }
    }

//	public class FragmentDashBoard extends FormFragment{		
//		
//		public FragmentDashBoard() {
//			// TODO Auto-generated constructor stub
//		}
//		
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			// TODO Auto-generated method stub
//			return super.onCreateView(inflater, container, savedInstanceState);
//		}
//	}
//		
//	public static class Module{
//		public String Name;	
//		public Drawable Image;
//		public Class<? extends Activity> ActivityClass;
//		
//		public Module(String name, Drawable image, Class<? extends Activity> ActivityClass) {
//			this.Name = name;
//			this.ActivityClass = ActivityClass;
//		}
//		
//		public void startActivity(Context context){
//			context.startActivity(new Intent(context.getApplicationContext(), ActivityClass));
//		}
//	}
}
