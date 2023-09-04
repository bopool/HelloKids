package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;
import com.bpdev.hellokids.model.ScheduleRes;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleEditActivity extends AppCompatActivity {

    String[] classNameList = {"꽃잎반","씨앗반"}; // 일단 에러나지말라고 {} 써줌
    Spinner spinnerSelectClass;

    private int id;

    private int classId; // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서)
    private String date;  // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서)

    public int selectIcon; // 아이콘 선택

    Button btnSave;
    EditText textInputTitle;
    EditText textInputContents;

    ImageView imgBirth;
    ImageView imgPicnic;
    ImageView imgFieldStudy;
    ImageView imgEvent;
    ImageView imgFestival;

    ScheduleRes scheduleRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        scheduleRes = (ScheduleRes) getIntent().getSerializableExtra("schedule");

        spinnerSelectClass = findViewById(R.id.spinnerSelectClass);
        btnSave = findViewById(R.id.btnSave);
        textInputTitle = findViewById(R.id.textInputTitle);
        textInputContents = findViewById(R.id.textInputContents);
        imgBirth = findViewById(R.id.imgBirth);
        imgPicnic = findViewById(R.id.imgPicnic);
        imgFieldStudy = findViewById(R.id.imgFieldStudy);
        imgEvent = findViewById(R.id.imgEvent);
        imgFestival = findViewById(R.id.imgFestival);

        textInputTitle.setText(scheduleRes.getTitle());
        textInputContents.setText(scheduleRes.getContents());
        id = scheduleRes.getId();
        classId = scheduleRes.getClassId();
        date = scheduleRes.getDate();
        selectIcon = scheduleRes.getSelectIcon();

        // 스피너
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>(ScheduleEditActivity.this,
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

                String title = textInputTitle.getText().toString().trim();
                String contents = textInputContents.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleEditActivity.this);

                ScheduleApi api = retrofit.create(ScheduleApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                Schedule schedule = new Schedule(classId,title,contents,date,selectIcon);

                Call<Result> call = api.scheduleEdit( id,"Bearer " + token, schedule );

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {


                        if(response.isSuccessful()){

                            Intent intent = new Intent(ScheduleEditActivity.this, ScheduleListActivity .class);
                            startActivity(intent);

                            finish();

                        }else{

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