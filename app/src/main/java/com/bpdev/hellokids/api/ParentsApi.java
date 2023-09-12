package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.ChildId;
import com.bpdev.hellokids.model.ParentsRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ParentsApi {
    @GET("/user/parent")
    Call<ParentsRes> parentsView(@Header("Authorization") String token);

    @GET("/user/waiting")
    Call<ChildId> parentsWaiting(@Header("Authorization") String token);

}
