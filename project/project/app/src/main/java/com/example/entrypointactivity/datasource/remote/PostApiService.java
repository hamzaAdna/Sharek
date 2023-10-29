package com.example.entrypointactivity.datasource.remote;

import static com.example.entrypointactivity.resources.Constant.ADDRESS;

import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.resources.Constant;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PostApiService {

    @GET(Constant.POST+"/read_post.php")
    Single<ResponsePost> getposts();
    @GET(Constant.POST+"/read_post_join_user_join_address.php")
    Single<ResponsePost> getPostsJoinAddressJoinUser();
    @GET(Constant.POST+"/read_post_by_user_id.php")
    Single<ResponsePost> getPostsByUserId(@Query("userId")int userId);
    @POST(Constant.POST+"/create_post.php")
    Single<Status> insertPost(@Body Post post);
    @PUT(Constant.POST+"/update_post.php")
    Single<Status> updatePost(@Body Post post);
    @DELETE(Constant.POST+"/delete_post.php")
    Single<Status> deletePost(@Query("postId")int postId);
}
