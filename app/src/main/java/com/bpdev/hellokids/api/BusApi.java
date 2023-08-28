package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BusApi {
    @POST("/schoolbus")
    Call<BusRes>busAdd(@Body Bus bus);

    @GET("/schoolbus")
    Call<BusList>busList();
}
