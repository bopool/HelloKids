package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.PhotoAlbumAllList;
import com.bpdev.hellokids.model.PhotoAlbumChildProfileRes;
import com.bpdev.hellokids.model.PhotoAlbumId;
import com.bpdev.hellokids.model.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


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
    @GET("/photoAlbum/rekoglist")
    Call<PhotoAlbumAllList>photoAlbumRecogList(@Header("Authorization") String token); // 사진첩 목록 조죄

    @GET("/photoAlbum/classlistView/{id}")
    Call<PhotoAlbumAllList>photoAlbumView(@Path("id") int id,@Header("Authorization") String token); // 사진첩 상세보기

    @GET("/photoAlbum/rekoglistView/{id}")
    Call<PhotoAlbumAllList>photoAlbumRecogView(@Path("id") int id,@Header("Authorization") String token);


    // 사진첩 얼굴인식 API
    @Multipart
    @POST("/photoAlbum/autoRekog")
    Call<Result> photoAlbumRekog (@Header("Authorization") String token,
                                  @Part("date") RequestBody date,
                                  @Part("title") RequestBody title,
                                  @Part("contents") RequestBody contents,
                                  @Part("classId") RequestBody classId,
                                  @Part("childId") RequestBody childId,
                                  @Part MultipartBody.Part photoUrl_1);

    // 사진첩 원아 프로필 가져오기 API
    @GET("/photoAlbum/getProfileUrl/{id}")
    Call<PhotoAlbumChildProfileRes> photochildProfile(@Path("id") int id, @Header("Authorization") String token);


    // 사진첩 얼굴인식 글 목록 생성 API
    @Multipart
    @POST("/photoAlbum/addChildProfileListId")
    Call<Result> photoAlbumRekogId (@Header("Authorization") String token,
                                  @Part("childId") RequestBody childId,
                                    @Part("totalAlbumNum") RequestBody totalAlbumNum);

}
