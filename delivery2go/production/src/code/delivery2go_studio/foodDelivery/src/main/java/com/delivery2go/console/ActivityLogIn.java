package com.delivery2go.console;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.Access;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.User;
import com.enterlib.DialogUtil;
import com.enterlib.app.UIUtils;
import com.enterlib.fields.Form;
import com.enterlib.mvvm.Command;
import com.enterlib.threading.AsyncManager;
import com.enterlib.threading.IWorkPost;

/**
 * Created by ansel on 09/09/2016.
 */
public class ActivityLogIn extends Activity {
    static final String DEBUG_TAG = ActivityLogIn.class.getSimpleName();

    Form form;
    String username;
    String password;
    boolean remember_me=true;
    private ProgressDialog dialog;

    public String getUsername(){
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword(){
        return password;
    }

    public boolean getRememberMe(){
        return remember_me;
    }

    public void setRememberMe(boolean value){
        this.remember_me = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.console_login_fragment);

        form = Form.build(null, getWindow().getDecorView(), this);

        User user = DeliveryApp.getUser();
        if(user!=null && DeliveryApp.getInstance().getPreferenceBoolean(DeliveryApp.REMEMBER_USER)){
            username = user.Username;
            password = user.Password;
            remember_me = true;
        }

        form.updateTargets();
    }

    public Command LogIn = new Command() {

        @Override
        public void invoke(Object invocator, Object args) {
            if(!form.validate())
                return;

            form.updateSource();

            dialog = DialogUtil.getProgressDialog(ActivityLogIn.this, R.string.loading);
            dialog.show();

            AsyncManager.postAsync(new IWorkPost() {
                @Override
                public boolean runWork() throws Exception {
                    IUserRepository userRep = RepositoryManager.getInstance().getIUserRepository();
                    User user = userRep.getFirst(String.format("Username = '%s' and Password = '%s'",username, password));
                    if(user==null){
                        UIUtils.showMessage(ActivityLogIn.this, "wrong username or password");
                    }else if(!user.IsActive){
                        DialogUtil.showAlertDialog(ActivityLogIn.this,
                                "User not Active",
                                "Your user is not active yet. It will be active once that your account had been validated", null);
                    }
                    else{
                        DeliveryApp.setUser(user);
                        DeliveryApp.getInstance().savePreference(DeliveryApp.REMEMBER_USER, remember_me);
                        Access.LoadAccess(userRep, user.Id);

                        startActivity(new Intent(getApplicationContext(), ActivityDashboard.class));
                    }
                    userRep.close();

                    return true;
                }

                @Override
                public void onWorkFinish(Exception workException) {
                    dialog.dismiss();
                    if(workException!=null){
                        Log.e("ActivityLogIn", workException.getLocalizedMessage(), workException);
                        DialogUtil.showErrorDialog(ActivityLogIn.this, "An error has occured");
                        return;
                    }

                }
            });

        }
    };

    public Command Register = new Command() {

        @Override
        public void invoke(Object invocator, Object args) {
            startActivity(new Intent(getApplicationContext(), ActivityRegisterUser.class));
        }
    };
}
