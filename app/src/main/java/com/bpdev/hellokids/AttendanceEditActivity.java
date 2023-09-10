package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.api.AttendanceApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.DailyNoteRow;
import com.bpdev.hellokids.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AttendanceEditActivity extends AppCompatActivity {

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

    // 메인 파트 버튼
    Button btnSave;

    // 메인 기능

    Spinner checkSpinner;
    String[] items = {"출석","결석","인정결석","병결","사고"}; // 스피너에 넣어줄 출석 상태 목록
    ArrayAdapter<String> arrayAdapter1;

    TextView childName;
    TextView applyDate;
    TextView editMemo;

    int id; // 출석부 id

    int childId;

    int classId;

    String date;
    String todayDate;
    String status;
    String memo;

    String selectName = "원아 이름";
    AttendanceRes attendanceRes;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_edit);

        attendanceRes = (AttendanceRes) getIntent().getSerializableExtra("attendanceRes");

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
        btnSave = findViewById(R.id.btnSave);
        checkSpinner = findViewById(R.id.checkSpinner);
        childName = findViewById(R.id.childName);
        applyDate = findViewById(R.id.applyDate);
        editMemo = findViewById(R.id.editMemo);

        // -- -- -- 메인 파트 동작 -- -- -- //

        date = attendanceRes.getDate().substring(0,4)+"년"+attendanceRes.getDate().substring(5,7)+"월"+attendanceRes.getDate().substring(8,10)+"일";
        id = attendanceRes.getId();


        childName.setText(attendanceRes.getChildName());
        applyDate.setText(date);
        editMemo.setText( attendanceRes.getMemo());

        arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        checkSpinner.setAdapter(arrayAdapter1);
        checkSpinner.setSelection(0,false);

        checkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = items[i];
                checkSpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), status+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo = editMemo.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(AttendanceEditActivity.this);
                AttendanceApi api = retrofit.create(AttendanceApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                AttendanceRes attendanceInfo = new AttendanceRes(status,memo);

                Call<Result> call = api.attendanceEdit(id,"Bearer " +token,attendanceInfo);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(AttendanceEditActivity.this,AttendanceListActivity.class);
                            startActivity(intent);

                        }else {
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
                Intent intent = new Intent(AttendanceEditActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceEditActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceEditActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceEditActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(AttendanceEditActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(AttendanceEditActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });










    }
}