package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.Location;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BusApi {
    @POST("/schoolbus")
    Call<BusRes>busAdd(@Body Bus bus); // 차량 정보 등록

    @GET("/schoolbus/drive")
    Call<BusDailyRecordList>busList(); // 차량 운행 기록 리스트

    @POST("/schoolbus/drive/{id}/location")
    Call<BusRes>addLocation(@Path("id") int id, @Header("Authorization") String token, @Body Location location);

    @POST("/schoolbus/drive/{id}")
    Call<BusRes>addBusDailyRecord(@Body BusDailyRecord busDailyRecord); // 차량 운행 등록

}
