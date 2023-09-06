package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuList;
import com.bpdev.hellokids.model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodMenuApi {

    @POST("/menu/add")
    Call<Result> foodMenuAdd(@Header("Authorization") String token, @Body FoodMenu foodMenu); // 개별 메뉴 입력

    @GET("/menu/{nurseryId}/list")
    Call<FoodMenuList>foodMenuList(@Path("nurseryId") int nurseryId); // 원 별 메뉴 목록

    @GET("/menu/{nurseryId}/{mealDate}")
    Call<FoodMenuList>foodMenuListDay(@Path("nurseryId") int nurseryId, @Path("mealDate") String mealDate); // 하루 메뉴 목록 /menu/1/2023-09-01

    @GET("/menu/{id}")
    Call<FoodMenu>foodMenuView(@Path("id") int id);; // 개별 메뉴 정보 보기

    @PUT("/menu/{id}")
    Call<FoodMenu>foodMenuEdit(@Header("Authorization") String token, @Path("id") int id); // 개별 메뉴 정보 수정

    @DELETE("/menu/{id}")
    Call<FoodMenu>foodMenuDelete(@Header("Authorization") String token, @Path("id") int id); // 개별 메뉴 삭제

}
