package com.example.entrypointactivity.ui.register;

import android.util.Log;
import android.widget.Toast;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrypointactivity.datasource.remote.ApiClient;
import com.example.entrypointactivity.datasource.remote.UserApiService;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private UserApiService userApiService= ApiClient.UserServiceInstance();
    private MutableLiveData<Status> statusInsertUserLogin=new MutableLiveData<>();

    @ViewModelInject
    RegisterViewModel(){
       // this.userLoginRepository=userLoginRepository;
    }
    public void inserUser(User user){

        userApiService.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            statusInsertUserLogin.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));




    }
    public  LiveData<Status> getStatusInsertUserLogin(){
        return statusInsertUserLogin;
    }
}
