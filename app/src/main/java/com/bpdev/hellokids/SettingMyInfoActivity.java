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

import com.bpdev.hellokids.adapter.ScheduleAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.NurseryRes;
import com.bpdev.hellokids.model.NurseryResList;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.ScheduleList;
import com.bpdev.hellokids.model.TeacherAll;
import com.bpdev.hellokids.model.TeacherAllList;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingMyInfoActivity extends AppCompatActivity {

    // 최상단 헤더의 버튼
    TextView btnRegister;
    TextView btnLogin;
    ImageButton btnTranslate;
    Button btnBack;

    // 메인 기능
    EditText userName;
    EditText userId;
    EditText editPassword;
    EditText editEmail;
    EditText editPhoneNumber;
    Button btnEdit;

    // 하단 바로가기 메뉴 버튼
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;

    Spinner nurserySpinner;
    Spinner classSpinner;

    List<String> nurseryNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    List<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<NurseryClass> classArrayList = new ArrayList<>(); // 반 목록 조회 api에 쓸 것
    ArrayList<NurseryRes> nurseryResArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter; // 스피너에 연결할 어댑터
    ArrayAdapter<String> arrayAdapter1; // 스피너에 연결할 어댑터
    int classId; // 반별 조회시 사용할 반 id
    int nurseryId; // 어린이집 id
    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는 반이름을 클릭하면 그 반이름을 가진 반 데이터의 id를 반환할 때 사용

    ArrayList<TeacherAll> teacherArrayList = new ArrayList<>(); // 내 정보(선생님) 불러올 때 사용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_my_info);


        // -- -- -- 화면 연결 -- -- -- //
        // 최상단 헤더 버튼 화면 연결
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnBack = findViewById(R.id.btnBack);


        // 메인 파트 화면 연결
        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        btnEdit = findViewById(R.id.btnEdit);
        nurserySpinner = findViewById(R.id.nurserySpinner);
        classSpinner = findViewById(R.id.classSpinner);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);

        // 메인 기능 구현
        // 내 정보(선생님) 가져오기
        Retrofit retrofit1 = NetworkClient.getRetrofitClient(SettingMyInfoActivity.this);

        UserApi api1 = retrofit1.create(UserApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Call<TeacherAllList> call1 = api1.teacherViewAll("Bearer " + token1);
        call1.enqueue(new Callback<TeacherAllList>() {
            @Override
            public void onResponse(Call<TeacherAllList> call, Response<TeacherAllList> response) {
                if(response.isSuccessful()){
                    TeacherAllList teacherList = response.body();

                    teacherArrayList.addAll( teacherList.getItems() );
                    if( teacherArrayList.size()!=0) {
                        userName.setText(teacherArrayList.get(0).getTeacherName());
                        userId.setText(teacherArrayList.get(0).getTeacherUserId());
                        //editPassword.setText(teacherArrayList.get(0).getPassword());
                        editEmail.setText(teacherArrayList.get(0).getEmail());
                        editPhoneNumber.setText(teacherArrayList.get(0).getPhone());
                    }
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<TeacherAllList> call, Throwable t) {

            }
        });

        // 스피너에 연결해줄 어댑터 생성
        arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nurseryNameArrayList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 연결해줄 어댑터 생성
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Retrofit retrofit2 = NetworkClient.getRetrofitClient(SettingMyInfoActivity.this);
        SettingApi api2 = retrofit2.create(SettingApi.class);

        SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

        Call<NurseryResList> call2 = api2.nurseryList("Bearer " + token2);
        call2.enqueue(new Callback<NurseryResList>() {
            @Override
            public void onResponse(Call<NurseryResList> call, Response<NurseryResList> response) {
                if (response.isSuccessful()) {
                    NurseryResList nurseryResList = response.body();
                    nurseryResArrayList.addAll(nurseryResList.getItems());

                    for (int i = 0; i < nurseryResArrayList.size(); i++) {
                        nurseryNameArrayList.add(nurseryResArrayList.get(i).getNurseryName());
                        map.put( nurseryResArrayList.get(i).getNurseryName(),nurseryResArrayList.get(i).getId());
                        arrayAdapter1.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<NurseryResList> call, Throwable t) {
            }
        });

        // 스피너에 어댑터 연결
        nurserySpinner.setAdapter(arrayAdapter1);

        // 스피너 초기화
        classSpinner.setSelection(0,false);

        nurserySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 하나 선택했을 때
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                nurserySpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                nurseryId = map.get(spinnerValue);

                // 스피너에 반 이름 가져오기
                Retrofit retrofit = NetworkClient.getRetrofitClient(SettingMyInfoActivity.this);
                SettingApi api = retrofit.create(SettingApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                Call<ClassList> call = api.nurseryClassList(nurseryId,"Bearer " + token);
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

            }

            // 아무것도 선택 안했을 때 근데 onCreate()가 실행되면서 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다 -> 이 부분이 필요하면 방법을 찾아야한다
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // -----------------------------------------------------------------------------------------------------------------------------------------
        // 스피너에 연결해줄 어댑터 생성
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // 스피너에 반 이름 가져오기
//        Retrofit retrofit = NetworkClient.getRetrofitClient(SettingMyInfoActivity.this);
//        SettingApi api = retrofit.create(SettingApi.class);
//
//        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//        String token = sp.getString(Config.ACCESS_TOKEN, "");
//
//        Call<ClassList> call = api.nurseryClassList(nurseryId,"Bearer " + token);
//        call.enqueue(new Callback<ClassList>() {
//            @Override
//            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
//                if (response.isSuccessful()) {
//                    ClassList classList = response.body();
//                    classArrayList.addAll(classList.getItems());
//
//                    for (int i = 0; i < classArrayList.size(); i++) {
//                        classNameArrayList.add(classArrayList.get(i).getClassName());
//                        map.put(classArrayList.get(i).getClassName(), classArrayList.get(i).getId());
//                        arrayAdapter.notifyDataSetChanged();
//                    }
//                } else {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ClassList> call, Throwable t) {
//            }
//        });

        // 스피너에 어댑터 연결
        classSpinner.setAdapter(arrayAdapter);

        //classArrayList = new ArrayList<>(); // 중복 방지 위한 초기화 해줬는데도 중복되서 나온다

        // 스피너 초기화
        classSpinner.setSelection(0,false);

        // 스피너 클릭 이벤트 리스너
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 하나 선택했을 때
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                classSpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                classId = map.get(spinnerValue);

                Log.i("classId", classId + "");


            }

            // 아무것도 선택 안했을 때 근데 onCreate()가 실행되면서 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다 -> 이 부분이 필요하면 방법을 찾아야한다
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // -----------------------------------------------------------------------------------------------------------------------------------

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacherName = userName.getText().toString().trim();
                String teacherUserId = userId.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phone = editPhoneNumber.getText().toString().trim();

                Retrofit retrofit2 = NetworkClient.getRetrofitClient(SettingMyInfoActivity.this);
                UserApi api2 = retrofit2.create(UserApi.class);

                SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                TeacherAll teacherAll = new TeacherAll(classId,nurseryId,teacherName,teacherUserId,password,email,phone);
                Call<Result> call2 = api2. update("Bearer " + token2, teacherAll);
                call2.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SettingMyInfoActivity.this, SettingListActivity.class);
                            startActivity(intent);

                            finish();
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                    }
                });


            }
        });


        // -- -- -- 하단 바로가기 메뉴 버튼 -- -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMyInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMyInfoActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMyInfoActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SettingMyInfoActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SettingMyInfoActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

        // '뒤로가기(화살표)' 버튼 눌렀을 때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMyInfoActivity.this,  SettingListActivity.class);
                startActivity(intent);
            }
        });


    }

}