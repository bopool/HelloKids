package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;


import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleAddActivity extends AppCompatActivity {

    String[] classNameList = {"꽃잎반","씨앗반"}; // 일단 에러나지말라고 {} 써줌
    Spinner spinnerSelectClass;


    // api호출 시 들어갈 데이터
    private int classId = 5; // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서)
    private String title;
    private String contents;
    private String date = "2023-09-30";  // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서) 근데 데이터베이스에 0000-00-00으로 들어감!!!
    public int selectIcon = 0; // 아이콘 선택


    Button btnSave;
    EditText textInputTitle;
    EditText textInputContents;

    ImageView imgBirth;
    ImageView imgPicnic;
    ImageView imgFieldStudy;
    ImageView imgEvent;
    ImageView imgFestival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);

        spinnerSelectClass = findViewById(R.id.spinnerSelectClass);
        btnSave = findViewById(R.id.btnSave);
        textInputTitle = findViewById(R.id.textInputTitle);
        textInputContents = findViewById(R.id.textInputContents);
        imgBirth = findViewById(R.id.imgBirth);
        imgPicnic = findViewById(R.id.imgPicnic);
        imgFieldStudy = findViewById(R.id.imgFieldStudy);
        imgEvent = findViewById(R.id.imgEvent);
        imgFestival = findViewById(R.id.imgFestival);


        // 스피너
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>(ScheduleAddActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                classNameList);

        spinnerSelectClass.setAdapter(classArrayAdapter);
        spinnerSelectClass.setSelection(0);

        spinnerSelectClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),classNameList[i]+"이 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title =  textInputTitle.getText().toString().trim();
                contents = textInputContents.getText().toString().trim();


                // API 를 호출한다.

                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleAddActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                ScheduleApi api = retrofit.create(ScheduleApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                // 3. 보낼 데이터를 준비한다.
                Schedule schedule = new Schedule(classId,title,contents,date,selectIcon);

                Call<Result> call = api.scheduleAdd("Bearer "+token,schedule);

                call.enqueue(new Callback<Result>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {

                            // 회원가입에서 받은 억세스토큰은,
                            // 앱 내에 저장해야 한다.
                            //                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            //                            SharedPreferences.Editor editor = sp.edit();
                            //                            UserRes res = response.body();
                            //
                            //                            editor.putString(Config.ACCESS_TOKEN, res.getAccess_token());
                            //                            editor.apply();

                            // 회원가입이 정상적으로 끝났으니까,
                            // 메인 액티비티를 실행하고,
                            // 이 액티비티는 종료해야 한다.
                            Intent intent = new Intent(ScheduleAddActivity.this, ScheduleListActivity .class);
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

        imgBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIcon = 1;
                Snackbar.make(imgBirth,"생일 아이콘을 선택했습니다.",Snackbar.LENGTH_SHORT).show();
            }
        });
        imgPicnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIcon = 2;
                Snackbar.make(imgBirth,"소풍 아이콘을 선택했습니다.",Snackbar.LENGTH_SHORT).show();
            }

        });
        imgFieldStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIcon = 3;
                Snackbar.make(imgBirth,"체험학습 아이콘을 선택했습니다.",Snackbar.LENGTH_SHORT).show();
            }
        });
        imgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIcon = 4;
                Snackbar.make(imgBirth,"행사 아이콘을 선택했습니다.",Snackbar.LENGTH_SHORT).show();
            }
        });
        imgFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIcon = 5;
                Snackbar.make(imgBirth,"축제 아이콘을 선택했습니다.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}


