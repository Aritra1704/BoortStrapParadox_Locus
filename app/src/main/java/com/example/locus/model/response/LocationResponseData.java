package com.example.locus.model.response;

import java.util.ArrayList;
import java.util.List;

public class LocationResponseData {
    List<LocationResponse> listLocations;

    public List<LocationResponse> getResponseData() {
        return listLocations;
    }

    public void setResponseData(List<LocationResponse> responseData) {
        this.listLocations = responseData;
    }

    @Override
    public String toString() {
        return "LocationResponseData{" +
                "responseData=" + listLocations +
                '}';
    }
}
