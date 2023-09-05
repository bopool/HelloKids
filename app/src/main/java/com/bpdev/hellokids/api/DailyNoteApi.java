package com.bpdev.hellokids.api;

import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DailyNoteApi {
    @POST("/dailynote/write/{childId}")
    Call<Result> dailyNoteAdd(@Path("childId") int childId, @Header("Authorization") String token, @Body DailyNote dailyNote);
}
