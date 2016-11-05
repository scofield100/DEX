package com.delivery2go;

import com.delivery2go.base.models.Entity;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ansel on 09/09/2016.
 */
public class AutoCompleteResult {
    public static final int TYPE_ADDRESS = 1;
    public static final int TYPE_ENTITY = 2;

    private String description;

    private String placeId;

    private int entityId;

    private int resultType;

    private LatLng latLng;

    public AutoCompleteResult(Entity e) {
        resultType = TYPE_ENTITY;
        entityId = e.Id;
        description =String.format("%s, %s, %s",e.Name, e.Adress, e.CityName);
    }

    public AutoCompleteResult(AutocompletePrediction prediction) {
        resultType = TYPE_ADDRESS;
        description = prediction.getDescription();
        placeId = prediction.getPlaceId();
    }

    public AutoCompleteResult(String description, int resultType, LatLng latLng) {
        this.description = description;
        this.resultType = resultType;
        this.latLng = latLng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return description;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}

