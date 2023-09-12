package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainTeacherActivity extends AppCompatActivity {

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



    // -- -- -- -- 메인 화면 -- -- -- -- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -- -- -- 화면 연결 -- -- -- //

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





        // -- -- -- 기    능 -- -- -- //

//        // 회원가입이나 로그인이 되어있는지 확인(토큰 있는지 확인)
//        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//        token = sp.getString(Config.ACCESS_TOKEN, "");
//
//        if(token.isEmpty()){
//            Intent intent = new Intent(MainActivity.this, RegisterSelectActivity.class);
//            startActivity(intent);
//
//            // 메인 액티비티 종료
//            finish();
//            return;
//        }




        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });




        // -- -- 메인 파트 버튼 -- -- //
        // 알림장 버튼
        btnDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선생님 화면
                Intent intent = new Intent(MainTeacherActivity.this,DailynoteListActivity.class);
                startActivity(intent);

            }
        });


        // 공지사항 버튼
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this,NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 사진첩 버튼
        btnPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this,PhotoalbumListActivity.class);
                startActivity(intent);
            }
        });


        // 일정표 버튼
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선생님 화면
                Intent intent = new Intent(MainTeacherActivity.this,ScheduleListActivity.class);
                startActivity(intent);

            }
        });


        // 식단표 버튼
        btnFoodMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this,FoodmenuListActivity.class);
                startActivity(intent);
            }
        });


        // 출석부
        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선생님 화면
                Intent intent = new Intent(MainTeacherActivity.this,AttendanceListActivity.class);
                startActivity(intent);


            }
        });



        // 안심등하원 버튼
        btnSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님 화면
                Intent intent = new Intent(MainTeacherActivity.this,SchoolbusListActivity.class);
                startActivity(intent);

            }
        });



        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTeacherActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선생님 화면
                Intent intent = new Intent(MainTeacherActivity.this, DailynoteListActivity.class);
                startActivity(intent);

            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(MainTeacherActivity.this, SchoolbusListActivity.class);
                startActivity(intent);

            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainTeacherActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

        // 사진첩 테스트용 버튼 - 나중에 지우기
//        textPageTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, PhotoalbumViewActivity.class);
//                startActivity(intent);
//            }
//        });



    }
}