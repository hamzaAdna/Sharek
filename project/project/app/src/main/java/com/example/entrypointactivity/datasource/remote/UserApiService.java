package com.example.entrypointactivity.datasource.remote;

import static com.example.entrypointactivity.resources.Constant.ADDRESS;
import static com.example.entrypointactivity.resources.Constant.USER;

import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface UserApiService {
    @GET(USER+"/read_user.php")
    Single<ResponseUser> getUsers();
    @GET(USER+"/read_user_by_user_id.php")
    Single<ResponseUser> getUserByUserId(@Query("userId") int userId);
    @POST(USER+"/create_user.php")
    Single<Status> insertUser(@Body User user);
    @PUT(USER+"/update_user.php")
    Single<Status>updateUser(@Body User user);
    @DELETE(USER+"/delete_user.php")
    Single<Status> deleteUser(@Query("userId") int userId);
    @POST(USER+"/read_user_by_email_and_password.php")
    Single<ResponseUser>login(@Body User user);




}
