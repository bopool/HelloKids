package com.bpdev.hellokids;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.DailyNoteAdapter;
import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.DailyNoteRow;
import com.bpdev.hellokids.model.DailyNoteRowList;
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

public class DailynoteListActivity extends AppCompatActivity {

    // 메인 기능
    Spinner spinnerChild;
    List<String> childNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 원아 목록
    ArrayList<Child> childArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;


    RecyclerView recyclerView;
    DailyNoteAdapter adapter;

    ArrayList<DailyNoteRow> dailyNoteArrayList = new ArrayList<>();

    int childId;

    public static Context mContext; // Context 변수 선언

    public String childName; // Adapter 로 전달할 변수 선언




    HashMap<String, Integer> map = new HashMap<>();

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

    // 화면 이동
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailynote_list);

        mContext = this; // oncreate 에 this(는 액티비티 클래스 자체를 의미) 할당

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        spinnerChild = findViewById(R.id.spinnerChild);

        // 스피너
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, childNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 원아 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(DailynoteListActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ChildList> call = api.childListView("Bearer " + token);
        call.enqueue(new Callback<ChildList>() {
            @Override
            public void onResponse(Call<ChildList> call, Response<ChildList> response) {
                if (response.isSuccessful()) {
                    ChildList childList = response.body();
                    childArrayList.addAll(childList.getItems());

                    for (int i = 0; i < childArrayList.size(); i++) {
                        childNameArrayList.add(childArrayList.get(i).getChildName());
                        map.put(childArrayList.get(i).getChildName(), childArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ChildList> call, Throwable t) {
            }
        });

        spinnerChild.setAdapter(arrayAdapter);

        spinnerChild.setSelection(0,false);

        spinnerChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                spinnerChild.setSelection(i);
                childName = spinnerValue;
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                childId = map.get(spinnerValue);
                

                // 원아별 알림장 리스트 조회
                Retrofit retrofit1 = NetworkClient.getRetrofitClient(DailynoteListActivity.this);

                DailyNoteApi api1 = retrofit1.create(DailyNoteApi.class);

                SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

                Call<DailyNoteRowList> call1 = api1.dailyNoteList(childId, "Bearer " + token1);
                call1.enqueue(new Callback<DailyNoteRowList>() {
                    @Override
                    public void onResponse(Call<DailyNoteRowList> call, Response<DailyNoteRowList> response) {
                        if (response.isSuccessful()) {
                            DailyNoteRowList dailyNoteList1 = response.body();

                            dailyNoteArrayList.addAll(dailyNoteList1.getItems());

                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                            adapter = new DailyNoteAdapter(DailynoteListActivity.this, dailyNoteArrayList);
                            recyclerView.setAdapter(adapter);
                            dailyNoteArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<DailyNoteRowList> call, Throwable t) {

                    }
                });

            }


            public void onNothingSelected(AdapterView<?> adapterView) { 
                
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

        // 화면 이동 버튼 연결
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this,DailynoteAddActivity.class);
                startActivity(intent);
            }
        });

        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailynoteListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(DailynoteListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(DailynoteListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });






    }
}