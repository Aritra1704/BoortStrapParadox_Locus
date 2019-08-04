package com.example.locus;

import android.app.Application;

import com.example.locus.webservice.ApiCalls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocusApplication extends Application {
    public static Retrofit retrofit;
    private static ApiCalls apiCalls;
    private OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
    }
    private void initHTTPClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);//add None for prod env
        client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).addInterceptor(interceptor).build();
    }


    private void initRetrofit() {
        initHTTPClient();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.105.16.249:8080")                //BaseURL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCalls = retrofit.create(ApiCalls.class);
    }

    public static ApiCalls getApiService() {
        return apiCalls;
    }
}
