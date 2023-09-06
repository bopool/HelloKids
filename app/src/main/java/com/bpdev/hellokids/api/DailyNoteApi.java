package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.DailyNoteRowList;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DailyNoteApi {
    @POST("/dailynote/write/{childId}")
    Call<Result> dailyNoteAdd(@Path("childId") int childId, @Header("Authorization") String token, @Body DailyNote dailyNote);

    @POST("/dailynote/parents/write")
    Call<Result> dailyNoteParentsAdd(@Header("Authorization") String token, @Body DailyNote dailyNote);

    @GET("/dailynote/list/{childId}")
    Call<DailyNoteRowList>dailyNoteList(@Path("childId") int childId,@Header("Authorization") String token);

    @GET("/dailynote/child/list")
    Call<DailyNoteRowList>dailyNoteParentsChildList(@Header("Authorization") String token);

    @PUT("/dailynote/{id}")
    Call<Result> dailyNoteEdit(@Path("id") int id, @Header("Authorization") String token, @Body DailyNote dailyNote);

    @DELETE("/dailynote/{id}")
    Call<Result> dailyNoteDelete(@Path("id") int id, @Header("Authorization") String token);
}
