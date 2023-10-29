package com.example.entrypointactivity.ui.admin.address.create;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrypointactivity.datasource.remote.AddressApiService;
import com.example.entrypointactivity.datasource.remote.ApiClient;
import com.example.entrypointactivity.datasource.remote.PostApiService;
import com.example.entrypointactivity.datasource.remote.UserApiService;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.Address;
import com.example.entrypointactivity.model.address.ResponseAddress;
import com.example.entrypointactivity.model.user.ResponseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateAddressViewModel extends ViewModel {
    private UserApiService userApiService= ApiClient.UserServiceInstance();
    private PostApiService postApiService=ApiClient.PostServiceInstance();
    private AddressApiService addressApiService=ApiClient.AddressServiceInstance();


    private MutableLiveData<Status> statusCreateAddress=new MutableLiveData<>();
    //private MutableLiveData<ResponseUser> getUsersMutableLiveData=new MutableLiveData<>();

     @ViewModelInject
     CreateAddressViewModel(){
    }




    // delete user api
    public void createAddress(Address address){

        addressApiService.insertAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            statusCreateAddress.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));




    }
    public  LiveData<Status> getStatusCreateAddress(){
        return statusCreateAddress;
    }



}
