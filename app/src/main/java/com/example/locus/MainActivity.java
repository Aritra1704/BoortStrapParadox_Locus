package com.example.locus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arpaul.utilitieslib.NetworkUtility;
import com.example.locus.model.response.LocationResponseData;
import com.example.locus.webservice.ApiCalls;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.lat_e_txt)
    TextInputEditText latETxt;
    @BindView(R.id.long_e_txt)
    TextInputEditText longETxt;
    @BindView(R.id.lat_e_txt_dest)
    TextInputEditText latETxtDest;
    @BindView(R.id.long_e_txt_dest)
    TextInputEditText longETxtDest;
    @BindView(R.id.fetch_btn)
    Button fetchBtn;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    ApiCalls apiCalls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiCalls = LocusApplication.getApiService();
        latETxt.setText("12.9091603");
        longETxt.setText("77.6637267");
        latETxtDest.setText("12.9248383");
        longETxtDest.setText("77.6753565");

    }

    //    http://10.105.16.249:8080/routes?data=77.6637267,12.9091603;77.6753565,12.9248383



    public void onFetchClick(View view){



        if (isEmpty(latETxt))
            latETxt.setError("Please enter source latitude");
        else if (isEmpty(longETxt))
            longETxt.setError("Please enter source longitude");
        else if (isEmpty(latETxtDest))
            latETxtDest.setError("Please enter destinaton latitude");
        else if (isEmpty(longETxtDest))
            longETxtDest.setError("Please enter destination longitude");
        else if(!NetworkUtility.isConnectionAvailable(MainActivity.this))
            Toast.makeText(this,"Please connect to the network",Toast.LENGTH_LONG).show();
        else {
            Double src_latitude = Double.parseDouble(latETxt.getText().toString());
            Double src_longitude = Double.parseDouble(longETxt.getText().toString());
            Double dest_latitude = Double.parseDouble(latETxtDest.getText().toString());
            Double dest_longitude = Double.parseDouble(longETxtDest.getText().toString());
            String query = src_longitude+","+src_latitude+";"+dest_longitude+","+dest_latitude;

            apiCalls.getWorkOrders(query).enqueue(dataCallback);
            pbLoading.setVisibility(View.VISIBLE);
        }
    }
    Callback<List<LocationResponseData>> dataCallback = new Callback<List<LocationResponseData>>() {
        @Override
        public void onResponse(Call<List<LocationResponseData>> call, Response<List<LocationResponseData>> response) {
            if(response.isSuccessful()) {
                Gson gson = new Gson();
                String data = gson.toJson(response.body().get(0).getResponseData());
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("DATA",data);
                startActivity(intent);
                pbLoading.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onFailure(Call<List<LocationResponseData>> call, Throwable t) {

        }
    };
    public boolean isEmpty(EditText editText) {
        int value = editText.getText().toString().length();
        if (value > 0)
            return false;
        else
            return true;

    }

}
