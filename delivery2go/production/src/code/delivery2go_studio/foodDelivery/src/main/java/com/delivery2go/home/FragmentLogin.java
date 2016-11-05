package com.delivery2go.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.client.FragmentClientEdit;
import com.delivery2go.console.ActivityClientDetails;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.user.ActivityUserDetails;
import com.enterlib.app.UIUtils;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.threading.IWorkPost;

/**
 * Created by ansel on 28/08/2016.
 */
public class FragmentLogin extends BindableFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_login, container, false);
        View username = view.findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityClientDetails.class), FragmentClientEdit.CLIENT_EDITED);
            }
        });
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FragmentClientEdit.CLIENT_EDITED && resultCode == FragmentClientEdit.CLIENT_EDITED){
            load();
        }
    }

    @Override
    protected DataViewModel createViewModel(Bundle savedInstanceState) {
        return new ViewModelLogin(this);
    }

    private class ViewModelLogin extends DataViewModel {

        public static final String CLIENT_REMEMBER = "CLIENT_REMEMBER";
        public String Username;

        public String Password;

        public boolean RememberMe = true;

        IClientRepository clientRepository;
        private Client client;

        public String getCurrentClient(){
            return client.Name;
        }

        public ViewModelLogin(FragmentLogin fragmentLogin) {
            super(fragmentLogin);

            clientRepository = RepositoryManager.getInstance().getIClientRepository();
        }

        @Override
        protected boolean loadAsync() throws Exception {
            client = DeliveryApp.getServerClient();
            RememberMe = DeliveryApp.getInstance().getPreferenceBoolean(CLIENT_REMEMBER, true);
            if(RememberMe){
                Username = client.Username;
                Password = client.Password;
            }

            return true;
        }

        public Command LogIn = new Command() {
            @Override
            public void invoke(Object invocator, Object args) {
                if(validate()){
                    DeliveryApp.getInstance().savePreference(CLIENT_REMEMBER, RememberMe);

                   doAsyncWork(getString(R.string.login_in), new IWorkPost() {
                       @Override
                       public boolean runWork() throws Exception {
                           Client client = clientRepository.getFirst(String.format("Username='%s' and Password='%s'", Username, Password));
                           if(client == null) {
                               UIUtils.showMessage(getActivity(), "wrong username or password");
                           }
                           else {
                               DeliveryApp.setClient(client);
                               ViewModelLogin.this.client =client;
                           }
                           return true;
                       }

                       @Override
                       public void onWorkFinish(Exception workException) {
                            if(workException != null)
                                return;

                           onPropertyChange("CurrentClient");
                       }
                   });
                }
            }
        };
    }
}
