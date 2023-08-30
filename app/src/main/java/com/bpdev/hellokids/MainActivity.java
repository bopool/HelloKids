package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.config.Config;

public class MainActivity extends AppCompatActivity {

    // 최상단 헤더의 버튼
    TextView btnSignup;
    TextView btnLogin;
    ImageButton btnTranslate;

    // 메인 파트
    ImageButton btnDailyNote;
    ImageButton btnNotice;
    ImageButton btnPhotoAlbum;
    ImageButton btnSchedule;
    ImageButton btnFoodMenu;
    ImageButton btnAttendance;
    ImageButton btnSchoolbus;

    // 하단 바로가기 메뉴
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;

    // 테스트용 버튼
    Button btnForTest;

    // 토큰
    String token;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnForTest = findViewById(R.id.btnForTest);


        // 회원가입이나 로그인이 되어있는지 확인(토큰 있는지 확인)
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        token = sp.getString(Config.ACCESS_TOKEN, "");

        if(token.isEmpty()){
            Intent intent = new Intent(MainActivity.this, RegisterSelectActivity.class);
            startActivity(intent);

            // 메인 액티비티 종료
            finish();
            return;
        }




        // -- -- -- 최상단 헤더 버튼 -- -- -- //
        // 회원가입 버튼
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼




        // -- -- -- 메인 파트 버튼 -- -- -- //
        // 알림장 버튼 
        btnDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 버튼
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 사진첩 버튼
        btnPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhotoalbumListActivity.class);
                startActivity(intent);
            }
        });


        // 일정표 버튼
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhotoalbumListActivity.class);
                startActivity(intent);
            }
        });


        // 식단표 버튼
        btnFoodMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FoodmenuListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 버튼
        btnSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님 화면
                Intent intent = new Intent(MainActivity.this,SchoolbusListActivity.class);
                startActivity(intent);

                // 학부모 화면
//                Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
//                startActivity(intent);
            }
        });



        // -- -- -- 하단 바로가기 버튼 -- -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(MainActivity.this, SchoolbusListActivity.class);
                startActivity(intent);

                // 학부모화면
//                Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
//                startActivity(intent);
            }
        });

        



        // 다른화면 테스트용 버튼 - 나중에 지우기
        btnForTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SchoolbusListActivity.class);
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