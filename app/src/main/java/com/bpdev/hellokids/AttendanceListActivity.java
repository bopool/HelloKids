package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.AttendanceAdapter;
import com.bpdev.hellokids.adapter.DailyNoteAdapter;
import com.bpdev.hellokids.api.AttendanceApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;

import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.AttendanceResList;
import com.bpdev.hellokids.model.ClassChild;
import com.bpdev.hellokids.model.ClassChildList;
import com.bpdev.hellokids.model.DailyNoteRow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AttendanceListActivity extends AppCompatActivity {

    // 최상단 헤더의 버튼
    TextView btnRegister;
    TextView btnLogin;
    ImageButton btnTranslate;

    // 하단 바로가기 메뉴 버튼
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;

    // 메인 기능
    TextView textClassName;
    RecyclerView recyclerView;

    ArrayList<ClassChild> classChildArrayList = new ArrayList<>();
    ArrayList<AttendanceRes> attendanceResArrayList = new ArrayList<>();

    AttendanceAdapter adapter;

    // 메인 파트 버튼
    Button btnSelectDate;
    Button btnCreate;
    Button btnCalendar;

    int classId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);


        // -- -- -- 화면 연결 -- -- -- //

        // 최상단 헤더 버튼 화면 연결
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnTranslate = findViewById(R.id.btnTranslate);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);

        // 메인 기능
        textClassName = findViewById(R.id.textClassName);
        recyclerView = findViewById(R.id.recyclerView);

        // 메인 버튼
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnCreate = findViewById(R.id.btnCreate);
        btnCalendar = findViewById(R.id.btnCalendar);

        // -- -- -- 메인 파트 동작 -- -- -- //
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = NetworkClient.getRetrofitClient(AttendanceListActivity.this);
        AttendanceApi api = retrofit.create(AttendanceApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ClassChildList> call = api.classChildList("Bearer " + token);
        call.enqueue(new Callback<ClassChildList>() {
            @Override
            public void onResponse(Call<ClassChildList> call, Response<ClassChildList> response) {
                if (response.isSuccessful()) {
                    ClassChildList classChildList = response.body();

                    classChildArrayList.addAll(classChildList.getItems());
                    classId = classChildArrayList.get(0).getClassId();
                    Log.i("classId", classId + "");
                    textClassName.setText(classChildArrayList.get(0).getClassName());

                } else {
                }
            }

            @Override
            public void onFailure(Call<ClassChildList> call, Throwable t) {
            }
        });

        Retrofit retrofit1 = NetworkClient.getRetrofitClient(AttendanceListActivity.this);
        AttendanceApi api1 = retrofit1.create(AttendanceApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        // Log.i("classId1",classId+"");
        Call<AttendanceResList> call1 = api1.attendanceList("Bearer " + token1);
        call1.enqueue(new Callback<AttendanceResList>() {
            @Override
            public void onResponse(Call<AttendanceResList> call, Response<AttendanceResList> response) {
                if (response.isSuccessful()) {
                    AttendanceResList attendanceResList = response.body();

                    attendanceResArrayList.addAll(attendanceResList.getItems());

                    adapter = new AttendanceAdapter(AttendanceListActivity.this, attendanceResArrayList);
                    recyclerView.setAdapter(adapter);
                    // attendanceResArrayList = new ArrayList<>(); // 중복 방지 위한 초기화

                } else {
                }
            }

            @Override
            public void onFailure(Call<AttendanceResList> call, Throwable t) {
            }
        });





        // 메인 버튼
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this,AttendanceAddActivity.class);
                intent.putExtra("classId",classId);
                startActivity(intent);
            }
        });



        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼




        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(AttendanceListActivity.this, SchoolbusListActivity.class);
                startActivity(intent);

                // 학부모화면
//                Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
//                startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AttendanceListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });












    }
}