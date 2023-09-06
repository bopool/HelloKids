package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.SomeResponse;

import java.util.List;

import okhttp3.MultipartBody;
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

public interface NoticeApi {

    @Multipart
    @POST("/notice/publish")
    Call<Result> noticeAdd(@Header("Authorization") String token, @Body Notice notice);
    // @Part List<MultipartBody.Part> files
 //
//                    @POST("api주소")
//                    Call<SomeResponse> request(@Part List<MultipartBody.Part> files);
//
//    @GET("/notice/all")
//    Call<NoticeList>noticeList(@Header("Authorization") String token);
//
//    @GET("/notice/child/list")
//    Call<NoticeList>noticeChildList(@Header("Authorization") String token);
//
//    @GET("/notice/{classId}/class")
//    Call<NoticeList>noticeClassList(@Path("classId") int classId, @Header("Authorization") String token);
//
//
//    @PUT("/notice/{id}")
//    Call<Result> noticeEdit(@Path("id") int id, @Header("Authorization") String token, @Body Notice notice);
//
//    @DELETE("/notice/{id}")
//    Call<Result> noticeDelete(@Path("id") int id, @Header("Authorization") String token);
}
