package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.BoardingRecord;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.Location;
import com.bpdev.hellokids.model.LocationList;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.TeacherList;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BusApi {
    @POST("/schoolbus")
    Call<BusRes>busAdd(@Header("Authorization") String token,@Body Bus bus); // 차량 정보 등록

    @PUT("/schoolbus/{id}")
    Call<BusRes> busEdit(@Path("id") int id, @Header("Authorization") String token, @Body Bus bus); // 차량 정보 수정

    @GET("/schoolbus/nursery")
    Call<BusInfoList>busInfoList(@Header("Authorization") String token); // 차량 정보 리스트 조회

    @GET("/schoolbus/drive/{createdAt}")
    Call<BusList>busInfoTodayList(@Path("createdAt") String createdAt,@Header("Authorization") String token); // 오늘 날짜에 해당하는 운행 기록이 있는 차량 정보 조회

    @GET("/schoolbus/{id}")
    Call<BusList>busView(@Path("id") int id, @Header("Authorization") String token); // 차량 정보 상세 조회

    @DELETE("/schoolbus/{id}")
    Call<BusRes> busInfoDelete(@Path("id") int id); // 차량 정보 삭제

    @GET("/schoolbus/drive")
    Call<BusDailyRecordList>busList(); // 차량 운행 기록 리스트 조회

    @POST("/schoolbus/drive/{id}/location")
    Call<BusRes>addLocation(@Path("id") int id, @Body Location location); // 인솔교사의 현재 위치 테이블에 저장

    @POST("/schoolbus/drive")
    Call<BusRes>addBusDailyRecord(@Header("Authorization") String token,@Body BusDailyRecord busDailyRecord); // 차량 운행 기록 등록

    @GET("/schoolbus/drive/{id}")
    Call<BusList>busInfoView(@Path("id") int id, @Header("Authorization") String token); // 차량 운행 기록 상세 조회 (이거 좀 이상하게 씀)

    @PUT("/schoolbus/drive/{id}")
    Call<BusRes> putShuttleTime(@Path("id") int id, @Header("Authorization") String token, @Body BusDailyRecord busDailyRecord); // 차량 정보 수정


    @GET("/schoolbus/drive/{id}/location")
    Call<LocationList>busLocation(@Path("id") int id); // 가장 최근 위치 조회

    @GET("/schoolbus/teacher")
    Call<TeacherList>teacherList(@Header("Authorization") String token); // 선생님 목록 불러오는 api

    @POST(" /schoolbus/drive/{id}/boarding")
    Call<BusRes>busBoardingAdd(@Path("id") int id, @Header("Authorization") String token); // 학부모가 원아 탑승 신청

    @DELETE("/schoolbus/boarding/{id}")
    Call<BusRes> busBoardingDelete(@Path("id") int id, @Header("Authorization") String token); // 학부모가 원아 탑승 취소

    @GET("/schoolbus/drive/{id}/boarding")
    Call<ChildList>busBoardingList(@Path("id") int id, @Header("Authorization") String token); // 차량 운행 기록 리스트 조회

    @PUT("/schoolbus/drive/{id}/boarding")
    Call<BusRes> busBoardingTime(@Path("id") int id, @Header("Authorization") String token, @Body BoardingRecord boardingRecord);



}
