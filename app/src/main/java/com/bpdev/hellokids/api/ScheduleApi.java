package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.ScheduleRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ScheduleApi {
    @POST("/schedule/write")
    Call<Result> scheduleAdd(@Header("Authorization") String token, @Body Schedule schedule);

    @GET("/schedule/all")
    Call<ScheduleList>scheduleList(@Header("Authorization") String token);

    @GET("/schedule/child/list")
    Call<ScheduleList>scheduleChildList(@Header("Authorization") String token);

    @GET("/schedule/{classId}/class")
    Call<ScheduleList>scheduleClassList(@Path("classId") int classId, @Header("Authorization") String token);


    @PUT("/schedule/{id}")
    Call<Result> scheduleEdit(@Path("id") int id, @Header("Authorization") String token, @Body Schedule Schedule);

    @DELETE("/schedule/{id}")
    Call<Result> scheduleDelete(@Path("id") int id, @Header("Authorization") String token);
}
