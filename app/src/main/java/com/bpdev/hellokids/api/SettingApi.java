package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.classList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SettingApi {

    @GET("/setting/class/list")
    Call<classList> classListView(@Header("Authorization") String token); // 차량 운행 기록 리스트 조회
}
