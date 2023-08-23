package com.bpdev.hellokids.config;


import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.User1;
import com.bpdev.hellokids.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/register/teacher")
    Call<UserRes> register(@Body User user);

    @POST("/register/parentp")
    Call<UserRes> register1(@Body User1 user);

}
