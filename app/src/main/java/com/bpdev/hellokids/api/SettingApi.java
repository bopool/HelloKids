package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.ApproveRes;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.ChildInfoList;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.MyClass;
import com.bpdev.hellokids.model.Nursery;
import com.bpdev.hellokids.model.NurseryResList;
import com.bpdev.hellokids.model.ParentsRes;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.TeacherAll;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SettingApi {

    @GET("/setting/class/list")
    Call<ClassList> classListView(@Header("Authorization") String token); // 선생님이 속한 반 리스트 조회

    @GET("/setting/children/list")
    Call<ChildList> childListView(@Header("Authorization") String token); // 선생님이 속한 반의 원아 리스트 조회

    @POST("/setting/nursery")
    Call<Result>nurseryAdd(@Header("Authorization") String token, @Body Nursery nursery); // 원 등록

    @GET("/setting/nursery/list")
    Call<NurseryResList> nurseryList(@Header("Authorization") String token); // 어린이집 리스트 조회

    @POST("/setting/class/{nurseryId}")
    Call<Result>classAdd(@Path("nurseryId") int nurseryId,@Header("Authorization") String token, @Body MyClass myClass); // 반 등록

    @GET("/setting/class/list/{nurseryId}")
    Call<ClassList> nurseryClassList(@Path("nurseryId") int nurseryId,@Header("Authorization") String token); // 선택한 어린이집 반 목록 조회

    @POST("/setting/child/add/{classId}")
    Call<Result>childAdd(@Path("classId") int classId,@Header("Authorization") String token, @Body ChildInfo childInfo); // 원아 등록
    @Multipart
    @PUT("/setting/child/{id}/photo")
    Call<Result> childProfileAdd(@Path("id") int id, @Header("Authorization") String token, @Part MultipartBody.Part profileUrl); // 원아 프로필 등록

    @GET("/setting/children/{classId}")
    Call<ChildInfoList> classChildListView(@Path("classId") int classId, @Header("Authorization") String token); // 선생님이 속한 반의 원아 리스트 조회

    @GET("/setting/approve")
    Call<ParentsRes> notApproveList(@Header("Authorization") String token); // 선택한 어린이집 반 목록 조회

    @PUT("/setting/approve/{parentsId}")
    Call<ApproveRes> approve(@Path("parentsId") int parentsId);



}
