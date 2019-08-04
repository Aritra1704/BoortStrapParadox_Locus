package com.example.locus;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.arpaul.utilitieslib.ColorUtils;
import com.example.locus.model.response.LocationResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<LocationResponse> responseData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Gson gson = new Gson();
        responseData = gson.fromJson(getIntent().getStringExtra("DATA"),new TypeToken<List<LocationResponse>>(){}.getType());
        Log.e("SIZE",responseData.size()+"");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        List<LatLng> lists = new ArrayList<>();
        for(LocationResponse rs: responseData){
            lists.add(new LatLng(rs.getLatitute(),rs.getLongitude()));
        }
        Log.e("SIZE",lists.size()+"");

        drawPolyLineOnMap(lists);
    }

    // Draw polyline on map
    public void drawPolyLineOnMap(List<LatLng> list) {
        if(list != null && list.size() > 0) {
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(ColorUtils.getColor(this, R.color.colorGreen));
            polyOptions.width(15);
            polyOptions.addAll(list);

            mMap.clear();
            mMap.addPolyline(polyOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(0).latitude, list.get(0).longitude), 4));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(list.size()/2).latitude, list.get(list.size()/2).longitude), 14.0f));
        }
    }
}
