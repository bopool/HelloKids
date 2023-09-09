package com.bpdev.hellokids;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.api.AttendanceApi;
import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Attendance;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AttendanceAddActivity extends AppCompatActivity {

    // 최상단 헤더 버튼
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

    // 메인 기능
    Spinner childSpinner;
    Spinner checkSpinner;
    List<String> childNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 원아 목록
    String[] items = {"출석","결석","인정결석","병결","사고"}; // 스피너에 넣어줄 출석 상태 목록
    ArrayList<Child> childArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter1;
    HashMap<String, Integer> map = new HashMap<>();

    TextView childName;
    TextView applyDate;
    TextView editMemo;

    int childId;

    int classId;

    String date;
    String todayDate;
    String status;
    String memo;

    String selectName = "원아 이름";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_add);

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
        childSpinner = findViewById(R.id.childSpinner);
        checkSpinner = findViewById(R.id.checkSpinner);
        childName = findViewById(R.id.childName);
        applyDate = findViewById(R.id.applyDate);
        editMemo = findViewById(R.id.editMemo);


        // -- -- -- 메인 파트 동작 -- -- -- //
        classId = getIntent().getIntExtra("classId",0);

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd HH:mm:ss"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        date =formatter.format(today);

        String pattern1 = "yyyy년 MM월 dd일"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter1 = new SimpleDateFormat(pattern1, currentLocale);
        todayDate =formatter1.format(today);
        applyDate.setText(todayDate);

        // 스피너
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, childNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 원아 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(AttendanceAddActivity.this);
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

        childSpinner.setAdapter(arrayAdapter);

        childSpinner.setSelection(0,false);

        checkSpinner.setAdapter(arrayAdapter1);

        checkSpinner.setSelection(0,false);

        childSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectName = adapterView.getItemAtPosition(i).toString();
                childSpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), selectName+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                childName.setText(selectName);
                childId = map.get(selectName);
                Log.i("childId",childId+"");

            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = items[i];
                checkSpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), status+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo =  editMemo.getText().toString().trim();

                // API 를 호출한다.

                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(AttendanceAddActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                AttendanceApi api = retrofit.create(AttendanceApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                // 3. 보낼 데이터를 준비한다.
                Attendance attendance = new Attendance(classId,date,status,memo);

                Call<Result> call = api.attendanceAdd(childId,"Bearer "+token,attendance);

                call.enqueue(new Callback<Result>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {


                            Intent intent = new Intent(AttendanceAddActivity.this, AttendanceListActivity.class);
                            startActivity(intent);

                            finish();

                            // 이렇게 상태코드써서 코드짜면 클라이언트 개발자가 코드짜기 쉽다
                        } else if (response.code() == 400) {

                        } else if (response.code() == 401) {

                        } else if (response.code() == 404) {

                        } else if (response.code() == 500) {

                        } else {
                            // 200OK 아닌경우

                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });







        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(AttendanceAddActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(AttendanceAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


    }
}