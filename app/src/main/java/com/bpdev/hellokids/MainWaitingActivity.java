package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainWaitingActivity extends AppCompatActivity {
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

    // 메인 파트
    ImageButton btnDailyNote;
    ImageButton btnNotice;
    ImageButton btnPhotoAlbum;
    ImageButton btnSchedule;
    ImageButton btnFoodMenu;
    ImageButton btnAttendance;
    ImageButton btnSchoolbus;



    // 토큰
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_waiting);

        // 최상단 헤더 버튼 화면 연결
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnTranslate = findViewById(R.id.btnTranslate);

        // 메인 파트 버튼 화면 연결
        btnDailyNote = findViewById(R.id.btnDailyNote);
        btnNotice = findViewById(R.id.btnNotice);
        btnPhotoAlbum = findViewById(R.id.btnPhotoAlbum);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnFoodMenu = findViewById(R.id.btnFoodMenu);
        btnAttendance = findViewById(R.id.btnAttendance);
        btnSchoolbus = findViewById(R.id.btnSchoolbus);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);



        // -- -- 메인 파트 버튼 -- -- //
        // 알림장 버튼
        btnDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // 공지사항 버튼
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 사진첩 버튼
        btnPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 일정표 버튼
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 식단표 버튼
        btnFoodMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 출석부
        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();



            }
        });



        // 안심등하원 버튼
        btnSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });



        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();


            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainWaitingActivity.this, "소속 어린이집의 승인이 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        });




    }
}