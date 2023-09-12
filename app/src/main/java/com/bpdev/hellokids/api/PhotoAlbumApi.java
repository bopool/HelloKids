package com.bpdev.hellokids.api;




import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.PhotoAlbumAllList;
import com.bpdev.hellokids.model.PhotoAlbumId;
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

    // 사진첩 생성( 사진 추가 )  API
    @Multipart
    @POST("/photoAlbum/add")
    Call<Result> photoAlbumAdd (@Header("Authorization") String token,
                                @Part("classId") RequestBody classId,
                                @Part("date") RequestBody date,
                                @Part("title") RequestBody title,
                                @Part("contents") RequestBody contents,
                                @Part MultipartBody.Part photoUrl);


    // 사진첩 글 아이디 생성 API
    @POST("/photoAlbum/addId")
    Call<Result> photoAlbumAddId (@Header("Authorization") String token, @Body PhotoAlbumId photoAlbumId);

    // 사진첩(전체 사진폴더) 리스트 불러오기
    @GET("/photoAlbum/classlist")
    Call<PhotoAlbumAllList>photoAlbumList(@Header("Authorization") String token); // 사진첩 목록 조죄

    // 사진첩(얼굴인식폴더) 리스트 불러오기



}
