package com.example.locus.model.response;

public class LocationResponse {
    double latitute,longitude;

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "latitute=" + latitute +
                ", longitude=" + longitude +
                '}';
    }
}
