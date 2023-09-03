package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodMenuApi {

    @POST("/menu/add")
    Call<FoodMenu> foodMenuAdd(@Body FoodMenu foodMenu); // 푸드 메뉴 등록

    @GET("/menu/{id}/list")
    Call<FoodMenuList> foodMenuList(@Path("id") int id); // 식단표 원별 리스트 조회

//    @GET("/menu/{id}/{mealDate}")
//    Call<FoodMenuList> foodMenuList(@Path("id") int id, @Path("mealDate") string mealDate); // 날짜별 식단 조회

    @GET("/menu/{id}")
    Call<FoodMenu> foodMenuView(@Path("id") int id); // 개별 식단 보기

    @PUT("/menu/{id}")
    Call<FoodMenu>foodMenuEdit(@Path("id") int id); // 개별 식단 수정

    @DELETE("/menu/{id}")
    Call<FoodMenu> foodMenuDelete(@Path("id") int id); // 개별 식단 삭제


}
