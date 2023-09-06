package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.DailyNoteRow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingSchoolbusEditActivity extends AppCompatActivity {

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

    EditText editBusName;
    EditText editBusNum;
    EditText editBusTime;
    EditText editDriverName;
    EditText editDriverNum;

    Button btnSave;

    BusInfo busInfo;

    int id; // 차량 id







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_schoolbus_edit);

        busInfo = (BusInfo) getIntent().getSerializableExtra("busInfo");

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
        editBusName = findViewById(R.id.editBusName);
        editBusNum = findViewById(R.id.editBusNum);
        editBusTime = findViewById(R.id.editBusTime);
        editDriverName = findViewById(R.id.editDriverName);
        editDriverNum = findViewById(R.id.editDriverNum);
        btnSave = findViewById(R.id.btnSave);

        // -- -- -- 메인 파트 동작 -- -- -- //

        id = busInfo.getId();

        editBusName.setText(busInfo.getShuttleName());
        editBusNum.setText(busInfo.getShuttleNum());
        editBusTime.setText( busInfo.getShuttleTime());
        editDriverName.setText(busInfo.getShuttleDriver());
        editDriverNum.setText( busInfo.getShuttleDriverNum());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shuttleName = editBusName.getText().toString().trim();
                String shuttleNum = editBusNum.getText().toString().trim();
                String shuttleTime = editBusTime.getText().toString().trim();
                String shuttleDriver = editDriverName.getText().toString().trim();
                String shuttleDriverNum = editDriverNum.getText().toString().trim();


                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(SettingSchoolbusEditActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                BusApi api = retrofit.create(BusApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                // 3. 보낼 데이터를 준비한다.
                Bus bus = new Bus(shuttleName, shuttleNum, shuttleTime, shuttleDriver, shuttleDriverNum);

                Call<BusRes> call = api.busEdit(id,"Bearer "+token,bus);

                call.enqueue(new Callback<BusRes>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(SettingSchoolbusEditActivity.this,SettingSchoolbusListActivity.class);
                            startActivity(intent);

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
                Intent intent = new Intent(SettingSchoolbusEditActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusEditActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusEditActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingSchoolbusEditActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SettingSchoolbusEditActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SettingSchoolbusEditActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });






    }
}