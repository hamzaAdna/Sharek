package com.example.entrypointactivity.ui.user.post.updateMyPost;

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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateMyPostViewModel extends ViewModel {
    private UserApiService userApiService= ApiClient.UserServiceInstance();
    private PostApiService postApiService=ApiClient.PostServiceInstance();
    private AddressApiService addressApiService=ApiClient.AddressServiceInstance();


    private MutableLiveData<Status> statusInsertUserLogin=new MutableLiveData<>();
    private MutableLiveData<ResponseAddress> getAddressesMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Status> insertPostMutableLiveData=new MutableLiveData<>();

    private MutableLiveData<ResponsePost> getPostMutableLiveData=new MutableLiveData<>();

    private MutableLiveData<Status> deletePostMutableLiveData=new MutableLiveData<>();



























    @ViewModelInject
    UpdateMyPostViewModel(){
    }




    public void getAddresses(){


        addressApiService.getaddresss()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            getAddressesMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));



    }
    public  LiveData<ResponseAddress> getAddressesLiveData(){
        return getAddressesMutableLiveData;
    }


    public void UpdatePost(Post post){
        postApiService.updatePost(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            insertPostMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getStatus()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));



    }
    public  LiveData<Status>  insertPostLiveData(){
        return insertPostMutableLiveData;
    }


    public void getPostsJoinAddressJoinUser(int userId){
        postApiService.getPostsByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> {
                            getPostMutableLiveData.setValue(result);
                            Log.d("viwModel",result.getData()+"deaa");
                        },
                        error -> Log.d("viwModel", "jjjj  "+error));

    }
    public  LiveData<ResponsePost>  getPostLiveData(){
        return getPostMutableLiveData;
    }





}
