package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.BusInfoAdapter;
import com.bpdev.hellokids.adapter.DailyNoteAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.DailyNoteRow;
import com.bpdev.hellokids.model.DailyNoteRowList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingSchoolbusListActivity extends AppCompatActivity {

    //public static Context mContext; // Context 변수 선언
    //public String token; // Adapter 로 전달할 변수 선언


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

    Button btnCreate;

    RecyclerView recyclerView;
    BusInfoAdapter adapter;
    ArrayList<BusInfo> busInfoArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_schoolbus_list);

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

        // 메인 파트 화면 연결

        btnCreate = findViewById(R.id.btnCreate);

        // -- -- -- 메인 파트 동작 -- -- -- //

        // 원아별 알림장 리스트 조회
        Retrofit retrofit1 = NetworkClient.getRetrofitClient(SettingSchoolbusListActivity.this);

        BusApi api1 = retrofit1.create(BusApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp1.getString(Config.ACCESS_TOKEN, "");

        Call<BusInfoList> call1 = api1.busInfoList( "Bearer " + token);
        call1.enqueue(new Callback<BusInfoList>() {
            @Override
            public void onResponse(Call<BusInfoList> call, Response<BusInfoList> response) {
                if (response.isSuccessful()) {
                    BusInfoList busInfoList = response.body();

                    busInfoArrayList.addAll(busInfoList.getItems());

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new  BusInfoAdapter(SettingSchoolbusListActivity.this, busInfoArrayList);
                    recyclerView.setAdapter(adapter);
                    busInfoArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                } else {

                }
            }

            @Override
            public void onFailure(Call<BusInfoList> call, Throwable t) {

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this,SettingSchoolbusAddActivity.class);
                startActivity(intent);
            }
        });


        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SettingSchoolbusListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SettingSchoolbusListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });










    }
}