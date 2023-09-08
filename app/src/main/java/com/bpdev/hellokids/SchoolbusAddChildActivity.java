package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.ChildAdapter;
import com.bpdev.hellokids.adapter.ChildTimeAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusAddChildActivity extends AppCompatActivity {

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
    Button btnSave;
    // 메인 기능
    RecyclerView recyclerView;

    int id; // 차량 운행 기록 id

    ArrayList<Child> childArrayList = new ArrayList<>();

    ChildTimeAdapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_add_child);

        id = getIntent().getIntExtra("id",0);

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
        btnSave = findViewById(R.id.btnSave);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // -- -- -- 메인 파트 동작 -- -- -- //
        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusAddChildActivity.this);

        BusApi api = retrofit.create(BusApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ChildList> call = api.busBoardingList(id,"Bearer "+ token);
        call.enqueue(new Callback<ChildList>() {
            @Override
            public void onResponse(Call<ChildList> call, Response<ChildList> response) {
                if (response.isSuccessful()) {
                    ChildList childList = response.body();

                    childArrayList.addAll(childList.getItems());

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter1 = new ChildTimeAdapter(SchoolbusAddChildActivity.this, childArrayList);

                    recyclerView.setAdapter(adapter1);
                } else {

                }
            }
                @Override
                public void onFailure(Call<ChildList> call, Throwable t) {

                }
            });





        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddChildActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddChildActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼



        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddChildActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddChildActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddChildActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SchoolbusAddChildActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SchoolbusAddChildActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });



    }
}