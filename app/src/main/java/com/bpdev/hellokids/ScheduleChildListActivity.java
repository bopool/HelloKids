package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.adapter.ScheduleParentsAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.ScheduleRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleChildListActivity extends AppCompatActivity {

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

    RecyclerView recyclerView;

    ScheduleParentsAdapter adapter;

    ArrayList<ScheduleRes> scheduleArrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_child_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



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


        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleChildListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleChildListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


// -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleChildListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleChildListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleChildListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 학부모화면
                Intent intent = new Intent(ScheduleChildListActivity.this, SchoolbusParentListActivity.class);
                startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScheduleChildListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleChildListActivity.this);

        ScheduleApi api = retrofit.create(ScheduleApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN,"");

        Log.i("token",token);

        Call<ScheduleList> call = api.scheduleChildList("Bearer "+token);
        call.enqueue(new Callback<ScheduleList>() {
            @Override
            public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
                if(response.isSuccessful()){
                    ScheduleList scheduleList1 = response.body();

                    Log.i("aaa",scheduleList1.getResult());

                    scheduleArrayList.addAll( scheduleList1.getItems() );

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new ScheduleParentsAdapter(ScheduleChildListActivity.this, scheduleArrayList);

                    recyclerView.setAdapter(adapter);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<ScheduleList> call, Throwable t) {

            }
        });


    }
}