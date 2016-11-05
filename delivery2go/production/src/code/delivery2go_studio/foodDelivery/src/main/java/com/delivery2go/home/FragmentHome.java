package com.delivery2go.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.delivery2go.AutoCompleteResult;
import com.delivery2go.PlaceAutocompleteAdapter;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.delivery2go.base.entity.FragmentEntityEdit;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.Entity;
import com.delivery2go.entity.ActivityEntityWelcome;
import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.ThumbailDrawableConverter;
import com.enterlib.DialogUtil;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.databinding.BindingResources;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

public class FragmentHome extends BindableFragment
{
	private static final String TAG = "FragmentHome";

	private MainActivity activity;
	private PlaceAutocompleteAdapter mAdapter;
	private AutoCompleteTextView adressEntry;
   // private LatLng placeCoord;
    AutoCompleteResult selectedResult;

	public Geocoder getGeocoder() {
		return activity.getGeocoder();
	}

//    public LatLng getPlaceCoord() {
//        return placeCoord;
//    }
//
//    public void setPlaceCoord(LatLng placeCoord) {
//        this.placeCoord = placeCoord;
//    }

    // Global constants

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;



    
    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    @Override
	public void onActivityResult( int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {
            case ViewModelHome.EDIT:
            	load();
            	break;
        }
     }


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		
		Button button = (Button) view.findViewById(R.id.btnSearch);
		button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
                ViewModelHome viewModel = (ViewModelHome) getViewModel();
                adressEntry.setError(null);

                if(selectedResult!=null){
                    if(selectedResult.getResultType() == AutoCompleteResult.TYPE_ENTITY){
                        Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityWelcome.class);
                        i.putExtra(ActivityEntityDetails.ID, new int[]{selectedResult.getEntityId()});
                        startActivityForResult(i,FragmentEntityEdit.ENTITY_EDITED);
                    }else if(selectedResult.getLatLng()!=null){
                            final Intent i = new Intent(activity.getApplicationContext(), ActivitySearchEntities.class);
                            i.putExtra(ViewModelHome.LATITUDE, selectedResult.getLatLng().latitude);
                            i.putExtra(ViewModelHome.LONGITUDE, selectedResult.getLatLng().longitude);
                            startActivityForResult(i, ViewModelHome.EDIT);
                        }
                    else if(!StringUtils.isNullOrWhitespace(selectedResult.getDescription())) {
                        showProgressDialog("Finding address coordinates...");
                        viewModel.findAddressLocation(selectedResult.getDescription());
                    }
                }else{
                    String address = adressEntry.getText().toString();
                    if(StringUtils.isNullOrWhitespace(address)) {
                        adressEntry.setError(getString(R.string.required));
                        return;
                    }
                    showProgressDialog("Finding address coordinates...");
                    viewModel.findAddressLocation(address);
                }

//                if(placeCoord!=null){
//                    final Intent i = new Intent(activity.getApplicationContext(), ActivitySearchEntities.class);
//                    i.putExtra(ViewModelHome.LATITUDE, placeCoord.latitude);
//                    i.putExtra(ViewModelHome.LONGITUDE, placeCoord.longitude);
//                    startActivityForResult(i, ViewModelHome.EDIT);
//                }else{
//                    String address =adressEntry.getText().toString();
//                    if(!StringUtils.isNullOrWhitespace(address)) {
//                        adressEntry.setError(getString(R.string.required));
//                        return;
//                    }
//                    showProgressDialog("Finding address coordinates...");
//                    viewModel.findAddressLocation(address);
//                }

			}
		});

		adressEntry = (AutoCompleteTextView) view.findViewById(R.id.editText1);
        final ImageButton imageBtnClear = (ImageButton) view.findViewById(R.id.imageBtnClear);
        imageBtnClear.setVisibility(View.GONE);
        imageBtnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adressEntry.setText(null);
            }
        });

        adressEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                selectedResult = null;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedResult = null;
                String value = adressEntry.getText().toString();
                if(!StringUtils.isNullOrWhitespace(value)){
                    imageBtnClear.setVisibility(View.VISIBLE);
                }else{
                    imageBtnClear.setVisibility(View.GONE);
                }
            }
        });


        ImageButton btnLocation = (ImageButton)view.findViewById(R.id.imageButton1);
        btnLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng location = activity.getCurrentLocation();
                if(location == null) {
                    Toast.makeText(activity, "Location unknown", Toast.LENGTH_SHORT).show();
                    return;
                }

                final Intent i = new Intent(activity.getApplicationContext(), ActivitySearchEntities.class);
                i.putExtra(ViewModelHome.LATITUDE, location.latitude);
                i.putExtra(ViewModelHome.LONGITUDE, location.longitude);
                startActivityForResult(i, ViewModelHome.EDIT);

            }
        });

		return view;
	}

    public void onAddressLocationResolve(LatLng location) {
        dismisProgressDialog();

        if(location!=null) {
            Intent i = new Intent(activity.getApplicationContext(), ActivitySearchEntities.class);
            i.putExtra(ViewModelHome.LATITUDE, location.latitude);
            i.putExtra(ViewModelHome.LONGITUDE, location.longitude);
            startActivityForResult(i, ViewModelHome.EDIT);
        }
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Register a listener that receives callbacks when a suggestion has been selected
		adressEntry.setOnItemClickListener(mAutocompleteClickListener);

		mAdapter = new PlaceAutocompleteAdapter(activity, activity.getGoogleApiClient(), null, null);
		adressEntry.setAdapter(mAdapter);

	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.activity = null;
    }
	
	@Override
	protected BindingResources getBindingResources() {	
		return super.getBindingResources()
				.put("ImageConverter", new ThumbailDrawableConverter());
	}
	
	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {			
		return new ViewModelHome(this);
	}

    @Override
    public void onStartViewModel() {
        getViewModel().onStarted();
        load();
    }

    @Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
		case EntityCursorViewModel.ENTITY_DETAILS:
			Entity entity = (Entity)data;
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityWelcome.class);
			i.putExtra(ActivityEntityDetails.ID, new int[]{entity.Id});
			startActivityForResult(i,ViewModelHome.EDIT);		
			break;

		default:
			break;
		}
	}


	/**
	 * Listener that handles selections from suggestions from the AutoCompleteTextView that
	 * displays Place suggestions.
	 * Gets the place id of the selected item and issues a request to the Places Geo Data API
	 * to retrieve more details about the place.
	 *
	 * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
	 * String...)
	 */
	private AdapterView.OnItemClickListener mAutocompleteClickListener
			= new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
			selectedResult = mAdapter.getItem(position);
			final String placeId = selectedResult.getPlaceId();
			final CharSequence primaryText = selectedResult.getDescription();

			Log.i(TAG, "Autocomplete item selected: " + primaryText);

            if(placeId!=null) {
                /*
                 Issue a request to the Places Geo Data API to retrieve a Place object with additional
                 details about the place.
                  */
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(activity.getGoogleApiClient(), placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            }else if(selectedResult.getResultType() == AutoCompleteResult.TYPE_ENTITY){
                Intent i =new  Intent(getActivity().getApplicationContext(), ActivityEntityWelcome.class);
                i.putExtra(ActivityEntityDetails.ID, new int[]{selectedResult.getEntityId()});
                startActivityForResult(i,FragmentEntityEdit.ENTITY_EDITED);
            }
		}
	};

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }

            if(selectedResult!=null) {
                // Get the Place object from the buffer.
                Place place = places.get(0);
                selectedResult.setLatLng(place.getLatLng());

                Log.i(TAG, "Place details received: " + place.getName());
            }

            places.release();
        }
    };


}
