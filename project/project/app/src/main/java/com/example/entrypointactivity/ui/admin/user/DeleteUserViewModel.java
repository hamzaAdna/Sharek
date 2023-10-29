package com.example.entrypointactivity.ui.admin.user;

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
import com.example.entrypointactivity.model.address.ResponseAddress;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteUserViewModel extends ViewModel {
    private UserApiService userApiService= ApiClient.UserServiceInstance();
    private PostApiService postApiService=ApiClient.PostServiceInstance();
    private AddressApiService addressApiService=ApiClient.AddressServiceInstance();


    private MutableLiveData<Status> statusDeleteUserLogin=new MutableLiveData<>();
    private MutableLiveData<ResponseUser> getUsersMutableLiveData=new MutableLiveData<>();

     @ViewModelInject
    DeleteUserViewModel(){
    }



    // get users
    public void getUsers(){


        userApiService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            getUsersMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));



    }
    public  LiveData<ResponseUser> getUsersLiveData(){
        return getUsersMutableLiveData;
    }



    // delete user api
    public void deleteUser(int userId){

        userApiService.deleteUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            statusDeleteUserLogin.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));




    }
    public  LiveData<Status> getStatusDeleteUserLogin(){
        return statusDeleteUserLogin;
    }



}
