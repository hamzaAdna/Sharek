package com.example.entrypointactivity.datasource.remote;

import static com.example.entrypointactivity.resources.Constant.ADDRESS;

import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.ResponseAddress;
import com.example.entrypointactivity.model.address.Address;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AddressApiService {

    @GET(ADDRESS+"/read_address.php")
    Single<ResponseAddress> getaddresss();
    @POST(ADDRESS+"/create_address.php")
    Single<Status> insertAddress(@Body Address address);
    @PUT(ADDRESS+"/update_address.php")
    Single<Status> updateAddress(@Body Address address);
    @DELETE(ADDRESS+"/delete_address.php")
    Single<Status> deleteAddress(@Query("addressId") int addressId);
}
