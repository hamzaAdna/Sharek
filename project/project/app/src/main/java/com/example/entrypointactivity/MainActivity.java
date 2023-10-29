package com.example.entrypointactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.entrypointactivity.datasource.remote.ApiClient;
import com.example.entrypointactivity.datasource.remote.UserApiService;
import com.example.entrypointactivity.model.user.ResponseUser;

import dagger.hilt.android.HiltAndroidApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltAndroidApp
public class MainActivity extends Application {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ApiClient.getClient().create(UserApiService.class).getusers().enqueue(new Callback<ResponseUser>() {
//            @Override
//            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
//
//                Toast.makeText(getBaseContext(), ""+"onResponse: "+response.body(), Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "onResponse: "+response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseUser> call, Throwable t) {
//                Toast.makeText(getBaseContext(), ""+"failuer", Toast.LENGTH_SHORT).show();
//               // Log.d("TAG", "onResponse: "+response.body());
//            }
//        });
//
//    }
}