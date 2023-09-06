package com.bpdev.hellokids.api;




import com.bpdev.hellokids.model.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PhotoAlbumApi {

    // 사진첩 생성 API
    @Multipart
    @POST("/photoAlbum/add")
    Call<Result> photoAlbumAdd (@Header("Authorization") String token,
                                @Part("classId") RequestBody classId,
                                @Part("date") RequestBody date,
                                @Part("title") RequestBody title,
                                @Part("contents") RequestBody contents,
                                @Part MultipartBody.Part photoUrl);


}
