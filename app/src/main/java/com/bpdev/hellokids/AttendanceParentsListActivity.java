package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.AttendanceAdapter;
import com.bpdev.hellokids.api.AttendanceApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ParentsApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.AttendanceResList;
import com.bpdev.hellokids.model.ClassChild;
import com.bpdev.hellokids.model.ClassChildList;
import com.bpdev.hellokids.model.Parents;
import com.bpdev.hellokids.model.ParentsRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AttendanceParentsListActivity extends AppCompatActivity {

    // 메인 기능
    TextView textChildName;
    RecyclerView recyclerView;

    ArrayList<Parents> parentsArrayList = new ArrayList<>();
    ArrayList<AttendanceRes> attendanceResArrayList = new ArrayList<>();

    AttendanceAdapter adapter;

    // 메인 파트 버튼
    Button btnSelectDate;
    Button btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_parents_list);

        // 메인 기능
        textChildName = findViewById(R.id.textChildName);
        recyclerView = findViewById(R.id.recyclerView);

        // 메인 버튼
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnCalendar = findViewById(R.id.btnCalendar);

        // -- -- -- 메인 파트 동작 -- -- -- //
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = NetworkClient.getRetrofitClient(AttendanceParentsListActivity.this);
        ParentsApi api = retrofit.create(ParentsApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ParentsRes> call = api.parentsView("Bearer " + token);
        call.enqueue(new Callback<ParentsRes>() {
            @Override
            public void onResponse(Call<ParentsRes> call, Response<ParentsRes> response) {
                if (response.isSuccessful()) {
                    ParentsRes parentsRes = response.body();

                    parentsArrayList.addAll(parentsRes.getItems());
                    textChildName.setText(parentsArrayList.get(0).getChildNameP());

                } else {
                }
            }

            @Override
            public void onFailure(Call<ParentsRes> call, Throwable t) {
            }
        });

        Retrofit retrofit1 = NetworkClient.getRetrofitClient(AttendanceParentsListActivity.this);
        AttendanceApi api1 = retrofit1.create(AttendanceApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Call<AttendanceResList> call1 = api1.attendanceParentsList("Bearer " + token1);
        call1.enqueue(new Callback<AttendanceResList>() {
            @Override
            public void onResponse(Call<AttendanceResList> call, Response<AttendanceResList> response) {
                if (response.isSuccessful()) {
                    AttendanceResList attendanceResList = response.body();

                    attendanceResArrayList.addAll(attendanceResList.getItems());

                    adapter = new AttendanceAdapter(AttendanceParentsListActivity.this, attendanceResArrayList);
                    recyclerView.setAdapter(adapter);
                    // attendanceResArrayList = new ArrayList<>(); // 중복 방지 위한 초기화

                } else {
                }
            }

            @Override
            public void onFailure(Call<AttendanceResList> call, Throwable t) {
            }
        });

    }
}