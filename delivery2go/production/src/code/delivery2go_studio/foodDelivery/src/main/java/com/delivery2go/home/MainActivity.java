package com.delivery2go.home;

import com.delivery2go.DeliveryApp;
import com.delivery2go.FragmentNavMenu;
import com.delivery2go.NavMenuItem;
import com.delivery2go.R;
import com.delivery2go.R.id;
import com.delivery2go.R.layout;
import com.delivery2go.R.menu;
import com.delivery2go.SettingsActivity;
import com.delivery2go.base.dashboard.ActivityDashboard;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Client;
import com.delivery2go.console.ActivityLogIn;
import com.delivery2go.order.FragmentOrderList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements
        FragmentNavMenu.NavigationDrawerCallbacks,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private static final String TAG = "MainActivity";
    private static final int SETTINGS = 45789;
    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private FragmentNavMenu mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private GoogleApiClient mGoogleApiClient;
    private Geocoder geocoder;
    private LatLng currentLocation;
    private Location currentFix;
    private LocationManager locationManager;


    public Geocoder getGeocoder() {
        return geocoder;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RepositoryManager.create(this);

        setContentView(R.layout.activity_drawer);

//		if(savedInstanceState == null){
//			getFragmentManager().beginTransaction()
//			.add(R.id.content, new FragmentHome())
//			.commit();
//		}

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationDrawerFragment = (FragmentNavMenu) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        geocoder = new Geocoder(this);
        buildGoogleApiClient();

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        currentLocation = null;
        currentFix = null;
        if(locationManager!=null){
            //get the cached location until a new location arrives
            Location networkLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(networkLoc == null && gpsLoc != null){
                currentLocation = new LatLng(gpsLoc.getLatitude(), gpsLoc.getLongitude());
                currentFix = gpsLoc;
            }else if(gpsLoc == null && networkLoc!=null) {
                currentLocation = new LatLng(networkLoc.getLatitude(), networkLoc.getLongitude());
                currentFix = networkLoc;
            }else if(networkLoc!=null && gpsLoc!=null) {
                //get the best location
                if(isBetterLocation(networkLoc, gpsLoc)){
                    currentLocation = new LatLng(networkLoc.getLatitude(), networkLoc.getLongitude());
                    currentFix = networkLoc;
                }else{
                    currentLocation = new LatLng(gpsLoc.getLatitude(), gpsLoc.getLongitude());
                    currentFix = gpsLoc;
                }
            }
        }

        if(currentLocation!=null){
            Client client = DeliveryApp.getClient();
            client.Lat = currentLocation.latitude;
            client.Lng = currentLocation.longitude;
            DeliveryApp.setClient(client);
        }

    }

    @Override
    protected void onStart() {
        //request location updates for network providers
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        //request location updates for gps providers
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        super.onStart();
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(this);

        super.onStop();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                //.addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        if(mGoogleApiClient!=null)
            mGoogleApiClient.disconnect();

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.admin:
                startActivity(new Intent(getApplicationContext(), ActivityLogIn.class));
                break;
            case R.id.settings:
                startActivityForResult(new Intent(getApplicationContext(), SettingsActivity.class), SETTINGS);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//	@Override
//	public boolean onNavigateUp() {
//		finish();
//		return true;
//	}

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
            case 0:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new FragmentHome()).commit();
                break;
            case 1:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new FragmentLogin()).commit();
                break;
            case 2:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new FragmentOrderList()).commit();
                break;

            default:
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public NavMenuItem[] getMenuItems() {
        return new NavMenuItem[]{
                new NavMenuItem(this, R.drawable.ic_action_home_yellow, R.string.home), //0
                new NavMenuItem(this, R.drawable.ic_action_user, R.string.login), //1
                new NavMenuItem(this, R.drawable.ic_action_cart_yellow, R.string.order_status), //2
                //new NavMenuItem(this, R.drawable.ic_action_vip, R.string.vip_lounge) //3
        };
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GoogleApiClient connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "GoogleApiClient suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "GoogleApiClient failed");
    }



    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    //
    @Override
    public void onLocationChanged(Location location) {
        if(isBetterLocation(location, currentFix)){
            currentFix = location;
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            Client client = DeliveryApp.getClient();
            client.Lat = currentLocation.latitude;
            client.Lng = currentLocation.longitude;
            DeliveryApp.setClient(client);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "Location Provider status changed: "+provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "Location Provider enabled: "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "Location Provider disabled: "+provider);
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SETTINGS){
            finish();
            DeliveryApp.restart();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
