package com.bpdev.hellokids.api;


import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.TeacherAll;
import com.bpdev.hellokids.model.TeacherAllList;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.User1;
import com.bpdev.hellokids.model.UserCheck;
import com.bpdev.hellokids.model.UserRes;
import com.bpdev.hellokids.model.TeacherRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    //  회원 가입 - 선생님
    @POST("/user/register/teacher")
    Call<UserRes> register(@Body User user);

    @PUT("/user/teacher")
    Call<Result> update(@Header("Authorization") String token, @Body TeacherAll teacherAll);

    @GET("/user/teacher/{id}")
    Call<TeacherRes> teacherView(@Path("id") int Id,@Header("Authorization") String token);

    @GET("/user/teacher")
    Call<TeacherAllList> teacherViewAll(@Header("Authorization") String token);
//
//    @DELETE("/user/teacher/<int:id>")
//    Call<UserRes> delete(@Path("teacherId") int Id);


    // 회원 가입 - 학부모
    @POST("/user/register/parent")
    Call<UserRes> register1(@Body User1 user);

    @PUT("/user/parent/<int:id>")
    Call<UserRes> update1(@Path("parentId") int id, @Body User1 user);

    @GET("/user/parent/<int:id>")
    Call<UserRes> parentsView(@Path("parentId") int id);

    @DELETE("/user/parent/<int:id>")
    Call<UserRes> delete(@Path("parentId") int id);


    // 로그인, 로그아웃
    @POST("/user/login")
    Call<UserRes> login(@Body User user);

    @DELETE("/user/logout")
    Call<UserRes> logout(@Body User user);

    @GET("/user/check/{email}")
    Call<UserCheck> userCheck(@Header("Authorization") String token, @Path("email") String email);



//
//
//    // setting
//    @POST("/setting/child")
//    // JSON 버전 아이 입력
//    Call<UserRes> upload(@Path(""));
//
//    @GET("/setting/children/<int:nurseryId>/all")
//    Call<UserRes>
//    @GET("/setting/children/<int:classId>")
//    Call<UserRes>
//    @GET("/setting/child/<int:id>")
//    Call<UserRes>
//    @PUT("/setting/child/<int:id>")
//    Call<UserRes>
//    @DELETE("/setting/child/<int:id>")
//    Call<UserRes>
//
//
//    // 원 정보 입력
//    @POST("/setting/nursery") // 원 입력
//    Call<UserRes>
//    @GET("/setting/nursery/<int:id>/view") // 원 정보 보기
//    Call<UserRes>
//    @PUT("/setting/nursery/<int:id>/edit") // 원 정보 수정
//    Call<UserRes>
//    @DELETE("/setting/nursery/<int:id>") // 원 정보 삭제
//    Call<UserRes>
//
//
//    // 반 정보 입력
//    @POST("/setting/class") // 반 입력
//    Call<UserRes>
//    @GET("/setting/class/<int:id>/view") // 반 정보 보기
//    Call<UserRes>
//    @GET("/setting/class/<int:id>/list") // 반 목록 보기 id:nurseryId
//    Call<UserRes>
//    @PUT("/setting/class/<int:id>/edit") // 반 정보 수정
//    Call<UserRes>
//    @DELETE("/setting/class/<int:id>") // 반 정보 삭제
//    Call<UserRes>
//
//
//    // 학부모 승인
//    @POST("/setting/approve") // 학부모 테이블에서 childId가 없는 사람들은 미승인, 넣을 때 승인
//    Call<UserRes>
//
//
//    // 공지사항
//    @POST("/notice/<int:classId>/publish") // 공지사항-임시저장
//    Call<UserRes>
//    @DELETE("/notice/<int:id>/publish") //공지사항 발행 #noticeId
//    Call<UserRes>
//    @PUT("/notice/<int:id>") // 공지사항 - 수정
//    Call<UserRes>
//    @DELETE("/notice/<int:id>") // 공지사항 - 삭제
//    Call<UserRes>
//    @GET("/notice/<int:id>") // 공지사항 - 보기
//    Call<UserRes>
//    @GET("/notice/<int:nurseryId>/list") // 공지사항 - 목록
//    Call<UserRes>
//
//
//    // 안심등하원
//    @POST("/schoolbus") // 차량 추가
//    Call<UserRes>
//    @PUT("/schoolbus/<int:id>") // 차량 수정
//    Call<UserRes>
//    @GET("/schoolbus/<int:nurseryId>") // 차량 조회
//    Call<UserRes>
//    @GET("/schoolbus") // 차량 목록 조회
//    Call<UserRes>
//    @DELETE("/schoolbus/<int:id>") // 차량 삭제
//    Call<UserRes>
//
//    @GET("/schoolbus/teacher/<int:nurseryId>") // 인솔교사 리스트
//    Call<UserRes>
//    @PUT("/schoolbus/drive/<int:id>/teacher") // 인솔교사 등록
//    Call<UserRes>
//    @GET("/schoolbus/drive/<int:id>/boarding") // 탑승자 리스트
//    Call<UserRes>
//    @PUT("/schoolbus/drive/<int:id>/boarding") // 승하차 시간 체크
//    Call<UserRes>
//
//    @POST("/schoolbus/drive/<int:id>/boarding") // 탑승신청 - 학부모화면
//    Call<UserRes>
//    @DELETE("/schoolbus/boarding/<int:id>") // 탑승취소 - 학부모화면
//    Call<UserRes>
//
//    @POST("/schoolbus/drive/<int:id>") // 차량 운행 기록 생성 - 운행시작,운행종료 시간입력
//    Call<UserRes>
//    @PUT("/schoolbus/drive/<int:id>") // 차량 운행 기록 수정
//    Call<UserRes>
//    @GET("/schoolbus/drive/<int:id>") // 차량 운행 기록 보기
//    Call<UserRes>
//
//
//    // 알림장
//    @POST("/dailynote/write/<int:childId>") // 알림장 등록
//    Call<UserRes>
//    @GET("/dailynote/<int:id>") // 알림장 상세보기
//    Call<UserRes>
//    @GET("/dailynote/list/<int:childId>") // 알림장 목록(원아별)
//    Call<UserRes>
//    @PUT("/dailynote/<int:id>") // 알림장 수정
//    Call<UserRes>
//    @DELETE("/dailynote/<int:id>") // 알림장 삭제
//    Call<UserRes>
//
//
//    // 일정표
//    @POST("/schedule/write")
//    Call<UserRes>
//    @GET("/schedule/<int:id>")
//    Call<UserRes>
//    @PUT("/schedule/<int:id>")
//    Call<UserRes>
//    @DELETE("/schedule/<int:id>")
//    Call<UserRes>
//    @GET("/schedule/<int:id>/list")
//    Call<UserRes>
//    @GET("/schedule/<int:classId>/class")
//    Call<UserRes>
//    @GET("/schedule/<int:nurseryId>/all")
//    Call<UserRes>

}







