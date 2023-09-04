package com.bpdev.hellokids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.FoodMenuApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.FoodMenu;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodmenuViewActivity extends AppCompatActivity {

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

    // 메인 파트 버튼
    TextView textDate;
    ImageView photoContent;
    TextView textContent;
    TextView contentType;
    ArrayList<FoodMenu> foodMenuArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu_view);

        // 화면 연결 //

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

        // 메인파트
        textDate = findViewById(R.id.textDate);
        photoContent = findViewById(R.id.photoContent);
        textContent = findViewById(R.id.textContent);
        contentType = findViewById(R.id.contentType);



        // 메인 파트 동작 //
        Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuViewActivity.this);
        FoodMenuApi api = retrofit.create(FoodMenuApi.class);
        Call<FoodMenu> call = api.foodMenuView(1);
        call.enqueue(new Callback<FoodMenu>() {
            @Override
            public void onResponse(Call<FoodMenu> call, Response<FoodMenu> response) {
                if(response.isSuccessful()){

                    FoodMenu foodMenu = response.body();

                    textDate.setText(foodMenu.getMealDate());
                    textContent.setText(foodMenu.getMealContent());
                    contentType.setText(foodMenu.getMealType());
                    Glide.with(FoodmenuViewActivity.this)
                            .load(foodMenu.getMealPhotoUrl())
                            .into(photoContent);
                    }

                else{


                }
            }

            @Override
            public void onFailure(Call<FoodMenu> call, Throwable t) {

            }

        });














        // 최상단 헤더 버튼 //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼







        // 하단 바로가기 메뉴 버튼 //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(FoodmenuViewActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(FoodmenuViewActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });



    }
}