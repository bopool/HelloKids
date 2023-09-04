package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.classList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SettingApi {

    @GET("/setting/class/list")
    Call<classList> classListView(@Header("Authorization") String token); // 선생님이 속한 반 리스트 조회
}
