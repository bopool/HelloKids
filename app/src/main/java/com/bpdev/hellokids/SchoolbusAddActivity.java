package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.ScheduleRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusAddActivity extends AppCompatActivity {

    String strId;
    String name;

    int id; // 선택한 차량 id
    int teacherId; // 인솔교사 id

    // 메인 기능
    Spinner spinnerSelectBus;
    TextView textGO;
    Button btnSelectTeacher;
    TextView textTeacherName;
    TextView textBusName;
    TextView textBusNum;
    TextView textDriverName;
    TextView textDriverNum;
    Button btnCreate;
    List<String> busNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 버스 목록
    ArrayList<BusInfo> busInfoArrayList = new ArrayList<>(); // 버스 목록 조회 api에 쓸 것
    ArrayList<Bus> busArrayList = new ArrayList<>(); // 버스 정보 상세 조회 api에 쓸 것
    ArrayAdapter<String> arrayAdapter; // 스피너에 연결할 어댑터

    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는  차량이름을 클릭하면 그 이름을 가진 차량 데이터의 id를 반환할 때 사용

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_add);



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
        spinnerSelectBus = findViewById(R.id.spinnerSelectBus);
        textGO = findViewById(R.id.textGo);
        btnSelectTeacher = findViewById(R.id.btnSelectTeacher);
        textTeacherName = findViewById(R.id.textTeacherName);
        textBusName = findViewById(R.id.textBusName);
        textBusNum = findViewById(R.id.textBusNum);
        textDriverName = findViewById(R.id.textDriverName);
        textDriverNum = findViewById(R.id.textDriverNum);
        btnCreate = findViewById(R.id.btnCreate);


        // -- -- -- 메인 파트 동작 -- -- -- //
        // 스피너에 연결해줄 어댑터 생성
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, busNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 차량 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusAddActivity.this);
        BusApi api = retrofit.create(BusApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<BusInfoList> call = api.busInfoList("Bearer " + token);
        call.enqueue(new Callback<BusInfoList>() {
            @Override
            public void onResponse(Call<BusInfoList> call, Response<BusInfoList> response) {
                if (response.isSuccessful()) {
                    BusInfoList busInfoList = response.body();
                    busInfoArrayList.addAll(busInfoList.getItems());

                    for (int i = 0; i < busInfoArrayList.size(); i++) {
                        busNameArrayList.add(busInfoArrayList.get(i).getShuttleName());
                        map.put( busInfoArrayList.get(i).getShuttleName(), busInfoArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<BusInfoList> call, Throwable t) {
            }
        });

        // 스피너에 어댑터 연결
        spinnerSelectBus.setAdapter(arrayAdapter);

        // 스피너 초기화
        spinnerSelectBus.setSelection(0,false);

        spinnerSelectBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 하나 선택했을 때
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                spinnerSelectBus.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                id = map.get(spinnerValue);

                // 차량 정보 상세 보기
                Retrofit retrofit1 = NetworkClient.getRetrofitClient(SchoolbusAddActivity.this);

                BusApi api1 = retrofit1.create(BusApi.class);

                SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

                Log.i("token1", token1);

                Call<BusList> call1 = api1.busInfoView(id, "Bearer " + token1);
                call1.enqueue(new Callback<BusList>() {
                    @Override
                    public void onResponse(Call<BusList> call, Response<BusList> response) {
                        if (response.isSuccessful()) {
                            BusList busList = response.body();

                            busArrayList.addAll(busList.getItems());

                            int hour = Integer.parseInt(busInfoArrayList.get(i).getShuttleTime().substring(11,12));
                            if(hour == 0) {
                                textGO.setText("등원");
                            }else{
                                textGO.setText("하원");
                            }

                            textBusName.setText( busInfoArrayList.get(i).getShuttleName());
                            textBusNum.setText( busInfoArrayList.get(i).getShuttleNum());
                            textDriverName.setText( busInfoArrayList.get(i).getShuttleDriver());
                            textDriverNum.setText( busInfoArrayList.get(i).getShuttleDriverNum());

                            busArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BusList> call, Throwable t) {

                    }
                });

            }

            // 아무것도 선택 안했을 때 근데 onCreate()가 실행되면서 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다 -> 이 부분이 필요하면 방법을 찾아야한다
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSelectTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this,SchoolbusAddTeacherActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusAddActivity.this);
                BusApi api2 = retrofit2.create(BusApi.class);

                SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                BusDailyRecord busDailyRecord = new BusDailyRecord(teacherId,id);
                Call<BusRes> call2 = api2.addBusDailyRecord("Bearer " + token2,busDailyRecord);
                call2.enqueue(new Callback<BusRes>() {
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SchoolbusAddActivity.this, SchoolbusListActivity.class);
                            startActivity(intent);

                            finish();
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<BusRes> call, Throwable t) {
                    }
                });


            }
        });

        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SchoolbusAddActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SchoolbusAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        // Intent get
        teacherId = getIntent().getIntExtra("teacherId",0);
        name = getIntent().getStringExtra("name");
        // teacherId = Integer.parseInt(strId);
        textTeacherName.setText(name);
    }
}