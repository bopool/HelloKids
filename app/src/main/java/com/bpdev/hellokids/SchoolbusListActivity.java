package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusListActivity extends AppCompatActivity {

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
    RecyclerView recyclerView;
    BusAdapter adapter;
    ArrayList<BusDailyRecord> busArrayList = new ArrayList<>();

    Button btnAdd;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_list);

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
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // -- -- -- 메인 파트 동작 -- -- -- //






        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼






        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SchoolbusListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SchoolbusListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this,  SchoolbusAddActivity.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusListActivity.this);

        BusApi api = retrofit.create(BusApi.class);

        Call<BusDailyRecordList> call = api.busList();
        call.enqueue(new Callback<BusDailyRecordList>() {
            @Override
            public void onResponse(Call<BusDailyRecordList> call, Response<BusDailyRecordList> response) {
                if(response.isSuccessful()){
                    BusDailyRecordList busList = response.body();

                    busArrayList.addAll( busList.getItems() );

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new BusAdapter(SchoolbusListActivity.this, busArrayList);

                    recyclerView.setAdapter(adapter);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<BusDailyRecordList> call, Throwable t) {

            }
        });

    }
}