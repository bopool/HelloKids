package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ScheduleApi {
    @POST("/schedule/write")
    Call<Result> scheduleAdd(@Header("Authorization") String token, @Body Schedule schedule);

    @GET("/schedule/all")
    Call<ScheduleList>scheduleList(@Header("Authorization") String token);
}
