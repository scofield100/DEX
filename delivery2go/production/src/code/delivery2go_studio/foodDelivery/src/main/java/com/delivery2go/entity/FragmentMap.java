package com.delivery2go.entity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.delivery2go.R;
import com.delivery2go.base.models.Entity;
import com.enterlib.DialogUtil;
import com.enterlib.app.UIUtils;
import com.enterlib.threading.AsyncManager;
import com.enterlib.threading.IWorkPost;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by hp on 8/16/2016.
 */
public class FragmentMap extends MapFragment implements OnMapReadyCallback, Observer {

    public static final String LATITUDE = "LAT";
    public static final String LONGITUDE ="LNG";
    public static  final String ADDRESS ="ADDRESS";

    private GoogleMap map;
    private ActivityEntityWelcome activity;
    private Geocoder geocoder;
    private List<Address> adresses;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (ActivityEntityWelcome) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        doWhenMapIsReady();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        activity.getViewModel().addObserver(this);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        activity.getViewModel().deleteObserver(this);

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        doWhenMapIsReady();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(map!=null){
            map.setMyLocationEnabled(false);
        }
    }

    private void doWhenMapIsReady() {
        if(map !=null && isResumed()){
            map.setMyLocationEnabled(true);
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                }
            });

            update(activity.getViewModel(), null);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(!activity.getViewModel().isLoaded() || map == null)
            return;

        final Entity entity = activity.getViewModel().getEntity();
        if(entity == null)
            return;

        map.clear();

        if(entity.Lat!=null && entity.Lng!=null){
            // Add a marker
            LatLng location = new LatLng(entity.Lat, entity.Lng);
            MarkerOptions markerOpt = new MarkerOptions()
                    .draggable(false)
                    .flat(false)
                    .position(location)
                    .title(entity.Name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.
                            HUE_AZURE));
            map.addMarker(markerOpt);

            //move the camera to the location
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(location)
                    .zoom(12)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }else{
            //find location by address
            if(geocoder == null) {
                geocoder = new Geocoder(activity);
            }

            AsyncManager.postAsync(new IWorkPost() {
                @Override
                public boolean runWork() throws Exception {

                    try {
                        if(Geocoder.isPresent()){
                            //get the latitud and longitud from the adress
                            adresses = geocoder.getFromLocationName(entity.getFullAdress(), 1);
                        }
                    }catch (Exception e){
                        adresses = null;
                    }
                    return true;
                }

                @Override
                public void onWorkFinish(Exception workException) {
                    if(workException!=null){
                        DialogUtil.showErrorDialog(activity, workException.getMessage());
                        Log.e(getClass().getSimpleName(), workException.getMessage(), workException);
                        return;                        
                    }
                    
                    if(adresses == null || adresses.size() == 0){
                        UIUtils.showMessage(activity, activity.getString(R.string.error_map_unknown_location));
                        return;
                    }

                    Address addr = adresses.get(0);
                    LatLng location = new LatLng(addr.getLatitude(),addr.getLongitude());
                    MarkerOptions markerOpt = new MarkerOptions()
                            .draggable(false)
                            .flat(false)
                            .position(location)
                            .title(entity.Name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.
                                    HUE_AZURE));
                    map.addMarker(markerOpt);

                    //move the camera to the location
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(location)
                            .zoom(12)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });
        }
    }
}
