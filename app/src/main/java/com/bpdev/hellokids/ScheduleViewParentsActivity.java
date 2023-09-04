package com.bpdev.hellokids;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.ScheduleRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleViewParentsActivity extends AppCompatActivity {

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
    TextView textDate;
    TextView textTitle;
    TextView textContents;


    ScheduleRes schedule;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view_parents);

        schedule = (ScheduleRes) getIntent().getSerializableExtra("schedule");

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



        // 메인 파트 화면 연결
        textDate = findViewById(R.id.textDate);
        textTitle = findViewById(R.id.textTitle);
        textContents = findViewById(R.id.textContents);


        // -- -- -- 메인 파트 동작 -- -- -- //

        textDate.setText(schedule.getDate());
        textTitle.setText( schedule.getTitle() );
        textContents.setText(schedule.getContents());


        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //학부모화면
                Intent intent = new Intent(ScheduleViewParentsActivity.this, SchoolbusParentListActivity.class);
                startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScheduleViewParentsActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

    }

}