package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.ScheduleRes;
import com.bpdev.hellokids.model.classList;
import com.bpdev.hellokids.model.nurseryClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleListActivity extends AppCompatActivity  { // implements AdapterView.OnItemSelectedListener

    Spinner spinnerClass;
    ArrayList<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<nurseryClass> classArrayList = new ArrayList<>(); // api에 쓸 것

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
    Button btnAdd;

    // 메인 기능

    RecyclerView recyclerView;

    ScheduleAdapter adapter;

    ArrayList<ScheduleRes> scheduleArrayList = new ArrayList<>();

    int classId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        spinnerClass = findViewById(R.id.spinnerClass);


        // 스피너에 반 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleListActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<classList> call = api.classListView("Bearer " + token);
        call.enqueue(new Callback<classList>() {
            @Override
            public void onResponse(Call<classList> call, Response<classList> response) {
                if (response.isSuccessful()) {
                    classList classList = response.body();
                    classArrayList.addAll(classList.getItems());
                    Log.i("classArrayList", classArrayList.get(1).getClassName());

                    for (int i = 0; i < classArrayList.size(); i++) {
                        classNameArrayList.add(classArrayList.get(i).getClassName());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<classList> call, Throwable t) {
            }
        });

        // 스피너
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>(ScheduleListActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                classNameArrayList);

        spinnerClass.setAdapter(classArrayAdapter);
        spinnerClass.setSelection(Adapter.NO_SELECTION, true);

        // spinnerClass.setOnItemSelectedListener(this);

        // 스피너에서 반 선택 했을 때
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("kkkk", "" + i);
                Toast.makeText(getApplicationContext(), classArrayList.get(i).getClassName() + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                classId = classArrayList.get(i).getId();

                Log.i("classId", classId + "");

                // 반별 일정표 리스트 조회
                Retrofit retrofit1 = NetworkClient.getRetrofitClient(ScheduleListActivity.this);

                ScheduleApi api1 = retrofit1.create(ScheduleApi.class);

                SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

                Log.i("token1", token1);

                Call<ScheduleList> call1 = api1.scheduleClassList(classId, "Bearer " + token1);
                call1.enqueue(new Callback<ScheduleList>() {
                    @Override
                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
                        if (response.isSuccessful()) {
                            ScheduleList scheduleList1 = response.body();

                            Log.i("aaa1", scheduleList1.getResult());

                            scheduleArrayList.addAll(scheduleList1.getItems());

                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                            adapter = new ScheduleAdapter(ScheduleListActivity.this, scheduleArrayList);

                            recyclerView.setAdapter(adapter);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleList> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 로그인 한 선생님이 속한 어린이집 전체 일정 리스트 조회
                Retrofit retrofit2 = NetworkClient.getRetrofitClient(ScheduleListActivity.this);

                ScheduleApi api2 = retrofit2.create(ScheduleApi.class);

                SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                Log.i("token2", token2);

                Call<ScheduleList> call2 = api2.scheduleList("Bearer " + token2);
                call2.enqueue(new Callback<ScheduleList>() {
                    @Override
                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
                        if (response.isSuccessful()) {
                            ScheduleList scheduleList2 = response.body();

                            Log.i("aaa2", scheduleList2.getResult());

                            scheduleArrayList.addAll(scheduleList2.getItems());

                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                            adapter = new ScheduleAdapter(ScheduleListActivity.this, scheduleArrayList);

                            recyclerView.setAdapter(adapter);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleList> call, Throwable t) {

                    }
                });
            }
        });


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

        // 메인 파트 버튼
        btnAdd = findViewById(R.id.btnAdd);

        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


// -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(ScheduleListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(ScheduleListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

        // 메인 파트 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleListActivity.this, ScheduleAddActivity.class);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Log.d("kkkk", "" + i);
//
//        Toast.makeText(getApplicationContext(), classArrayList.get(i).getClassName() + "가 선택되었습니다.",
//                Toast.LENGTH_SHORT).show();
//        classId = classArrayList.get(i).getId();
//
//        Log.i("classId", classId + "");
//
//        // 반별 일정표 리스트 조회
//        Retrofit retrofit1 = NetworkClient.getRetrofitClient(ScheduleListActivity.this);
//
//        ScheduleApi api1 = retrofit1.create(ScheduleApi.class);
//
//        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");
//
//        Log.i("token1", token1);
//
//        Call<ScheduleList> call1 = api1.scheduleClassList(classId, "Bearer " + token1);
//        call1.enqueue(new Callback<ScheduleList>() {
//            @Override
//            public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                if (response.isSuccessful()) {
//                    ScheduleList scheduleList1 = response.body();
//
//                    Log.i("aaa1", scheduleList1.getResult());
//
//                    scheduleArrayList.addAll(scheduleList1.getItems());
//
//                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                    adapter = new ScheduleAdapter(ScheduleListActivity.this, scheduleArrayList);
//
//                    recyclerView.setAdapter(adapter);
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        // 로그인 한 선생님이 속한 어린이집 전체 일정 리스트 조회
//        Retrofit retrofit2 = NetworkClient.getRetrofitClient(ScheduleListActivity.this);
//
//        ScheduleApi api2 = retrofit2.create(ScheduleApi.class);
//
//        SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//        String token2 = sp2.getString(Config.ACCESS_TOKEN, "");
//
//        Log.i("token2", token2);
//
//        Call<ScheduleList> call2 = api2.scheduleList("Bearer " + token2);
//        call2.enqueue(new Callback<ScheduleList>() {
//            @Override
//            public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                if (response.isSuccessful()) {
//                    ScheduleList scheduleList2 = response.body();
//
//                    Log.i("aaa2", scheduleList2.getResult());
//
//                    scheduleArrayList.addAll(scheduleList2.getItems());
//
//                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                    adapter = new ScheduleAdapter(ScheduleListActivity.this, scheduleArrayList);
//
//                    recyclerView.setAdapter(adapter);
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//            }
//        });

}
