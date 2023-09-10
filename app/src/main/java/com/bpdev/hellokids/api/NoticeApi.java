package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.NoticeRes;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.SomeResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NoticeApi {

    @Multipart
    @POST("/notice/add")
    Call<Result> noticeAdd(@Header("Authorization") String token,
                           @Part("noticeDate") RequestBody noticeDate,
                           @Part("noticeTitle") RequestBody noticeTitle,
                           @Part("noticeContents") RequestBody noticeContents,
                           @Part MultipartBody.Part noticePhotoUrl,
                           @Part("isPublish") RequestBody isPublish);

    @GET("/notice/Class")
    Call<NoticeRes> noticeClassList(@Header("Authorization") String token);

    @GET("/notice/{id}")
    Call<NoticeRes> noticeView(@Header("Authorization") String token, @Path("id") int id);

    @GET("/notice/list")
    Call<NoticeRes> noticeList(@Header("Authorization") String token,
                              @Query("offset") int offset,
                              @Query("limit") int limit,
                              @Query("count") int count);

    @Multipart
    @PUT("/notice/{id}")
    Call<Result> noticeEdit(@Header("Authorization") String token,
                            @Path("id") int id,
                            @Part("noticeDate") RequestBody noticeDate,
                            @Part("noticeTitle") RequestBody noticeTitle,
                            @Part("noticeContents") RequestBody noticeContents,
                            @Part MultipartBody.Part noticePhotoUrl,
                            @Part("isPublish") RequestBody isPublish);

    @DELETE("/notice/{id}")
    Call<Result> noticeDelete(@Header("Authorization") String toke, @Path("id") int id);
}



