package com.example.entrypointactivity.ui.profile;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrypointactivity.datasource.remote.ApiClient;
import com.example.entrypointactivity.datasource.remote.PostApiService;
import com.example.entrypointactivity.datasource.remote.UserApiService;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {
    private UserApiService userApiService= ApiClient.UserServiceInstance();
    private PostApiService postApiService= ApiClient.PostServiceInstance();
    private MutableLiveData<Status> statusInsertUserLogin=new MutableLiveData<>();

    private MutableLiveData<ResponsePost> getPostMutableLiveData=new MutableLiveData<>();


    private MutableLiveData<ResponseUser> getUserMutableLiveData=new MutableLiveData<>();

    @ViewModelInject
    ProfileViewModel(){
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

    public void getPostsByUserId(int userId){
        postApiService.getPostsByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            getPostMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getData()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));

    }
    public  LiveData<ResponsePost>  getPostsByUserIdLiveData(){
        return getPostMutableLiveData;
    }



    public void getUserByUserId(int userId){
        userApiService.getUserByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            getUserMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getData()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));

    }
    public  LiveData<ResponseUser>  getUserByUserIdLiveData(){
        return getUserMutableLiveData;
    }


}
