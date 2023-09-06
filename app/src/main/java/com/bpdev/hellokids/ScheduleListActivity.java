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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.ScheduleRes;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.NurseryClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleListActivity extends AppCompatActivity {

    // 메인 기능
    Spinner spinnerClass; // 스피너
    List<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<NurseryClass> classArrayList = new ArrayList<>(); // 반 목록 조회 api에 쓸 것
    ArrayAdapter<String> arrayAdapter; // 스피너에 연결할 어댑터

    RecyclerView recyclerView; // 목록 보여주는 리사이클러뷰

    ScheduleAdapter adapter; // 리사이클러뷰에 row 연결할 어댑터

    ArrayList<ScheduleRes> scheduleArrayList = new ArrayList<>(); // 반별 일정표 리스트 조회 api에 쓸 것

    int classId; // 반별 조회시 사용할 반 id

    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는 반이름을 클릭하면 그 반이름을 가진 반 데이터의 id를 반환할 때 사용

    // ------------------------------------------------------------------------------------------------------------------------------

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        
        // 리사이클러뷰 화면 연결, 리사이클러뷰 설정
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        // 스피너 화면 연결
        spinnerClass = findViewById(R.id.spinnerClass);

        // 스피너에 연결해줄 어댑터 생성
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 반 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleListActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ClassList> call = api.classListView("Bearer " + token);
        call.enqueue(new Callback<ClassList>() {
            @Override
            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
                if (response.isSuccessful()) {
                    ClassList classList = response.body();
                    classArrayList.addAll(classList.getItems());

                    for (int i = 0; i < classArrayList.size(); i++) {
                        classNameArrayList.add(classArrayList.get(i).getClassName());
                        map.put(classArrayList.get(i).getClassName(), classArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ClassList> call, Throwable t) {
            }
        });

        // 스피너에 어댑터 연결
        spinnerClass.setAdapter(arrayAdapter);

        // 스피너 초기화
        spinnerClass.setSelection(0,false);

        // 스피너 클릭 이벤트 리스너
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 하나 선택했을 때
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                spinnerClass.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                classId = map.get(spinnerValue);

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

                            scheduleArrayList.addAll(scheduleList1.getItems());

                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                            adapter = new ScheduleAdapter(ScheduleListActivity.this, scheduleArrayList);
                            recyclerView.setAdapter(adapter);
                            scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleList> call, Throwable t) {

                    }
                });

            }

            // 아무것도 선택 안했을 때 근데 onCreate()가 실행되면서 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다 -> 이 부분이 필요하면 방법을 찾아야한다 
            public void onNothingSelected(AdapterView<?> adapterView) {

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

}
