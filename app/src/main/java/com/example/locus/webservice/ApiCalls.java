package com.example.locus.webservice;

import com.example.locus.model.response.LocationResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {
//    http://10.105.16.249:8080/routes?data=77.6637267,12.9091603;77.6753565,12.9248383
    @GET("/routes")
    Call<List<LocationResponseData>> getWorkOrders(@Query("data") String query);
}
