package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Attendance;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.ResultText;
import com.bpdev.hellokids.model.TranText;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TranslateApi {
    @POST("/translate")
    Call<ResultText> translate(@Body TranText tranText); // 출석 체크 생성
}
