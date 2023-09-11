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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.MyClass;
import com.bpdev.hellokids.model.Nursery;
import com.bpdev.hellokids.model.NurseryRes;
import com.bpdev.hellokids.model.NurseryResList;
import com.bpdev.hellokids.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingClassAddActivity extends AppCompatActivity {
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

    // 메인 파트
    Spinner classSpinner;
    TextView editClassName;
    Button btnClassCreate;

    List<String> nurseryNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 어린이집 이름 목록
    ArrayList<NurseryRes> nurseryResArrayList = new ArrayList<>(); // 어린이집 목록 조회 api에 쓸 것

    ArrayAdapter<String> arrayAdapter; // 스피너에 연결할 어댑터

    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는  차량이름을 클릭하면 그 이름을 가진 차량 데이터의 id를 반환할 때 사용

    int nurseryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_class_add);

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
        classSpinner = findViewById(R.id.classSpinner);
        editClassName = findViewById(R.id.editClassName);
        btnClassCreate = findViewById(R.id.btnClassCreate);

        // 스피너에 연결해줄 어댑터 생성
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nurseryNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Retrofit retrofit = NetworkClient.getRetrofitClient(SettingClassAddActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<NurseryResList> call = api.nurseryList("Bearer " + token);
        call.enqueue(new Callback<NurseryResList>() {
            @Override
            public void onResponse(Call<NurseryResList> call, Response<NurseryResList> response) {
                if (response.isSuccessful()) {
                    NurseryResList nurseryResList = response.body();
                    nurseryResArrayList.addAll(nurseryResList.getItems());

                    for (int i = 0; i < nurseryResArrayList.size(); i++) {
                        nurseryNameArrayList.add(nurseryResArrayList.get(i).getNurseryName());
                        map.put( nurseryResArrayList.get(i).getNurseryName(),nurseryResArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<NurseryResList> call, Throwable t) {
            }
        });



        // 스피너에 어댑터 연결
        classSpinner.setAdapter(arrayAdapter);

        // 스피너 초기화
        classSpinner.setSelection(0,false);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 하나 선택했을 때
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                classSpinner.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                nurseryId = map.get(spinnerValue);


            }

            // 아무것도 선택 안했을 때 근데 onCreate()가 실행되면서 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다 -> 이 부분이 필요하면 방법을 찾아야한다
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnClassCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = editClassName.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient( SettingClassAddActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                SettingApi api = retrofit.create(SettingApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                // 3. 보낼 데이터를 준비한다.
                MyClass myClass= new MyClass(className);

                Call<Result> call = api.classAdd(nurseryId,"Bearer "+token,myClass);

                call.enqueue(new Callback<Result>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {


                            Intent intent = new Intent(SettingClassAddActivity.this, SettingClassinfoActivity.class);
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
                Intent intent = new Intent( SettingClassAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SettingClassAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼






        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SettingClassAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SettingClassAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SettingClassAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent( SettingClassAddActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent( SettingClassAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


    }
}