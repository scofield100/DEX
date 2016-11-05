package com.delivery2go.console;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.User;
import com.delivery2go.base.user.ViewModelUserEdit;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.ActivityView;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IEditView;

/**
 * Created by ansel on 09/09/2016.
 */
public class ActivityRegisterUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RepositoryManager.create(this);

        setContentView(R.layout.gen_activity_frame);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.content, new FragmentRegisterUser())
                    .commit();
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }


    public static class FragmentRegisterUser extends com.delivery2go.base.user.FragmentUserEdit{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.console_register_user, container, false);
            return view;
        }

        @Override
        protected DataViewModel createViewModel(Bundle savedInstanceState) {
            return new ViewModelUserEdit(this, RepositoryManager.getInstance().getIUserRepository(), getIds());
        }
    }


}
