package com.example.entrypointactivity.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.entrypointactivity.R;
import com.example.entrypointactivity.datasource.remote.UserApiService;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginViewModel extends ViewModel {
    private UserApiService userApiService;
    private MutableLiveData<ResponseUser> listMutableLiveData=new MutableLiveData<ResponseUser>();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    @ViewModelInject
    public LoginViewModel(UserApiService userApiService ) {
        Log.d("TAG", "MainViewModel: ");
        this.userApiService = userApiService;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }
    public void login(User userLogin){
        Log.d("login start","go "+userLogin.getEmail());

        userApiService.login(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            listMutableLiveData.setValue(result);
                            Log.d("viwModel"," deaa");

                        },
                        error -> Log.d("viwModel", error+""));


    }
    public MutableLiveData<ResponseUser> getUserByPasswordAndEmail() {
        return listMutableLiveData;
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {


        if (username == null) {
            return false;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(username).matches()&&!username.trim().isEmpty()) {
          return  true;
        } else {
            return false;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}

