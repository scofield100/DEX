package com.delivery2go;

import java.util.Date;
import java.util.UUID;

import com.delivery2go.base.Access;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.web.WebRepositoryManager;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.User;
import com.enterlib.serialization.JSonSerializer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeliveryApp extends Application {

	public  static final float MILLE_METERS = 1609.344f;
	public static final double MAX_DISTANCE =  10.0 ;

	public static final boolean AUTOCOMPLETE_OFFLINE = false;

    public static int DELIVERY_ACOUNT_ID = 1;

	private static final String APP_USER = "APP_USER";
	private static final String APP_CONSOLE_USER = "APP_CONSOLE_USER";
	private static final String DEBUG_TAG = "DeliveryApp";
    public static final String REMEMBER_USER = "REMEMBER_USER";



	private static DeliveryApp instance;
	
	private Client client;
	private User user;
	private SharedPreferences mPreferences;

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mPreferences = getPreferences();
		
		RepositoryManager.create(getApplicationContext());		
		instance = this;
		
		_getClient();
		if(client == null){
			//Generate new client
			client = new Client();
			
			// FOR TEST
			//client.Id =1;
			
			client.CreateDate = new Date();
			client.UpdateDate = new Date();
			client.IsRegistered = false;
			client.Guid = UUID.randomUUID().toString();
			client.Name = client.Guid;
			
			TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if(phoneManager != null){
				client.DeviceId = phoneManager.getDeviceId();
				client.Phone = phoneManager.getLine1Number();
			}
			
			//store in the app preferences
			_setClient(client);
		}

		Access.restoreAccess();
	}

    public static void restart(){
        RepositoryManager.getInstance().close();
        instance.client = null;
        instance.user = null;
    }

	public IImageProvider createImageProvider(){
		RepositoryManager repositoryManager = RepositoryManager.getInstance();
		if(repositoryManager instanceof WebRepositoryManager){
			WebRepositoryManager webRepositoryManager = (WebRepositoryManager) repositoryManager;
			return new WebImageProvider(webRepositoryManager.getBaseUrl(), webRepositoryManager.getSessionId());
		}else{
			return new LocalImageProvider(this);
		}
	}

    public static DeliveryApp getInstance(){
        return instance;
    }


	public static Client getClient(){
		if(instance == null)
			return null;
		return instance._getClient();
	}

	public static  User getUser(){
		if(instance == null)
			return  null;
		return instance._getUser();
	}

	public  static  void setUser(User user){
		if(instance!=null){
			instance._setUser(user);
		}
	}

    /**This method must be called from another Thread*/
    public static Client getServerClient(){
        Client client = getClient();

        if(client.Id == 0){
			IClientRepository clientRep= RepositoryManager.getInstance().getIClientRepository();
			client.UpdateDate = new Date();
			client.CreateDate = client.UpdateDate;
            clientRep.create(client);
            setClient(client);
			clientRep.close();
        }

        if(client.getEntityContext() == null){
            client.setEntityContext(RepositoryManager.getInstance().getEntityContext());
        }

        return client;
    }
	
	public static void setClient(Client client){
		if(instance == null)
			return;
		instance._setClient(client);
	}
	
	private Client _getClient(){	
		mPreferences = getPreferences();
		if(client == null && mPreferences.contains(APP_USER)){			
			client = getPreferenceObject(APP_USER, Client.class);							
		}
		return client;
	}

	
	public void _setClient(Client value){
		if(value!=null){			
			savePreferenceObject(APP_USER, value);			
		}
		else
			removePreference(APP_USER);
		
		client = value;
	}

	private User _getUser(){
		mPreferences = getPreferences();
		if(user == null && mPreferences.contains(APP_CONSOLE_USER)){
			user = getPreferenceObject(APP_CONSOLE_USER, User.class);
		}
		return user;
	}

	private void _setUser(User user){
		if(user != null){
			savePreferenceObject(APP_CONSOLE_USER, user);
		}else{
			removePreference(APP_CONSOLE_USER);
		}
		this.user = user;
	}
	
	private SharedPreferences getPreferences(){
		if(mPreferences == null){
			mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		}
		return mPreferences;
	}

	
	public boolean savePreference(String key, String value){
		boolean ok = false;
		if (key != null && !key.equals("")){
			try{
				Editor editor = getPreferences().edit();
				editor.putString(key, value);
				editor.commit();
				ok = true;
			}catch(Exception exc){
				Log.d(DEBUG_TAG, exc.getMessage(), exc);
			}
		}
		return ok;
	}
	
	public boolean savePreference(String key, Boolean value){
		boolean ok = false;
		if (key != null && !key.equals("")){
			try{
				Editor editor = getPreferences().edit();
				editor.putBoolean(key, value);
				editor.commit();
				ok = true;
			}catch(Exception exc){
				Log.d(DEBUG_TAG, exc.getMessage(), exc);
			}
		}
		return ok;
	}
	
	public boolean savePreference(String key, int value){
		boolean ok = false;
		if (key != null && !key.equals("")){
			try{
				Editor editor = getPreferences().edit();
				editor.putInt(key, value);
				editor.commit();
				ok = true;
			}catch(Exception exc){
				Log.d(DEBUG_TAG, exc.getMessage(), exc);
			}
		}
		return ok;
	}
	
	public boolean removePreference(String key){
		boolean ok = false;
		if (key != null && !key.equals("")){
			try{
				Editor editor = getPreferences().edit();
				editor.remove(key);
				editor.commit();
				ok = true;
			}catch(Exception exc){
				Log.d(DEBUG_TAG, exc.getMessage(), exc);
			}
		}
		return ok;
	}
	
	public boolean savePreferenceObject(String key, Object value){		
		String json = JSonSerializer.serializeObject(value);
		return savePreference(key, json);
	}
	
	public String getPreferenceString(String key){		
		return getPreferenceString(key, "");
	}
	
	public boolean getPreferenceBoolean(String key){
		return getPreferenceBoolean(key, false);
	}
	
	public int getPreferenceInt(String key){
		return getPreferenceInt(key, 0);
	}
	
	public String getPreferenceString(String key,String defaultValue){
		return getPreferences().getString(key, defaultValue);
	}
	
	public boolean getPreferenceBoolean(String key, boolean defaultValue){
		return getPreferences().getBoolean(key, defaultValue);
	}
	
	public int getPreferenceInt(String key, int defaultValue){
		return getPreferences().getInt(key, defaultValue);
	}
	
	public <T> T getPreferenceObject(String key, Class<T>type){
		String json = getPreferenceString(key);
		return JSonSerializer.deserializeObject(type, json);
	}
	
	public boolean containsPreference(String key){
		return getPreferences().contains(key);
	}
		
 
}
