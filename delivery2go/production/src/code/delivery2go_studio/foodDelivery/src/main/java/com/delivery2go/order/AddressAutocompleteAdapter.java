package com.delivery2go.order;

/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.delivery2go.AutoCompleteResult;
import com.delivery2go.DeliveryApp;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Entity;
import com.enterlib.StringUtils;
import com.enterlib.data.QueryHelper;
import com.enterlib.exceptions.ConnectionFailException;
import com.enterlib.exceptions.ServerOperationException;
import com.enterlib.googleservices.geocode.GeocodeInfo;
import com.enterlib.googleservices.geocode.ResultInfo;
import com.enterlib.serialization.JSonSerializer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Adapter that handles Autocomplete requests from the Places Geo Data API.
 * {@link AutocompletePrediction} results from the API are frozen and stored directly in this
 * adapter. (See {@link AutocompletePrediction#freeze()}.)
 * <p>
 * Note that this adapter requires a valid {@link GoogleApiClient}.
 * The API client must be maintained in the encapsulating Activity, including all lifecycle and
 * connection states. The API client must be connected with the {@link Places#GEO_DATA_API} API.
 */
public class AddressAutocompleteAdapter
        extends ArrayAdapter<AutoCompleteResult> implements Filterable {



    private static final String TAG = "Autocomplete";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    /**
     * Current results returned by this adapter.
     */
    private ArrayList<AutoCompleteResult> mResultList;



    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private AutocompleteFilter mPlaceFilter;


    public AddressAutocompleteAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1);
    }

    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public AutoCompleteResult getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        AutoCompleteResult item = getItem(position);
        int splitIndex = item.getDescription().indexOf(',');
        TextView textView1 = (TextView) row.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) row.findViewById(android.R.id.text2);
        if(splitIndex > 0){
            textView1.setText(item.getDescription().substring(0,splitIndex));
            textView2.setText(item.getDescription().substring(splitIndex+1));
        }
        else {
            textView1.setText(item.getDescription());
            textView2.setText("");
        }

        return row;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                // We need a separate list to store the results, since
                // this is run asynchronously.
                ArrayList<AutoCompleteResult> filterData = new ArrayList<>();

                // Skip the autocomplete query if no constraints are given.
                if (constraint != null && constraint.length() > 0) {
                    // Query the autocomplete API for the (constraint) search string.
                    filterData = getAutocomplete(constraint);
                }

                results.values = filterData;
                if (filterData != null) {
                    results.count = filterData.size();
                } else {
                    results.count = 0;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    mResultList = (ArrayList<AutoCompleteResult>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getDescription();
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }


    private ArrayList<AutoCompleteResult> getAutocomplete(CharSequence constraint) {
        ArrayList<AutoCompleteResult> results = new ArrayList<>();
        String constraintStr = constraint.toString();

        try{

            GeocodeInfo info;
            if(!DeliveryApp.AUTOCOMPLETE_OFFLINE) {
                info = com.enterlib.googleservices.GoogleApiClient.getGeocodeInfo(constraintStr);
            }
            else {
                InputStream is = getContext().getAssets().open("resonse.json.txt");
                String json = StringUtils.readAllText(is);
                JSonSerializer serializer = new JSonSerializer();
                info = (GeocodeInfo)serializer.deserialize(GeocodeInfo.class, json);
            }

            if (info != null && info.status != null && info.status.equals("OK")) {
                if (info.results != null) {
                    for (int i = 0; i < info.results.length; i++) {
                        ResultInfo p = info.results[i];
                        results.add(new AutoCompleteResult(p.formatted_address, AutoCompleteResult.TYPE_ADDRESS, new LatLng(p.geometry.location.lat, p.geometry.location.lng)));
                    }
                }
            }

        }catch (ConnectionFailException e){
            Log.d(getClass().getSimpleName(),e.getMessage(), e);
        }catch (ServerOperationException e){
            Log.d(getClass().getSimpleName(), e.getMessage(), e);
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), e.getMessage(), e);
        }

        return results;
    }


}

