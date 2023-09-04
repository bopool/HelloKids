package com.bpdev.hellokids;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.ScheduleRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleViewActivity extends AppCompatActivity {

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

    // 기능 버튼
    Button btnEdit;
    Button btnDelete;

    // 메인 기능
    TextView textDate;
    TextView textTitle;
    TextView textContents;


    ScheduleRes schedule;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);

        schedule = (ScheduleRes) getIntent().getSerializableExtra("schedule");

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

        // 기능 버튼 화면 연결
        btnEdit  = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // 메인 파트 화면 연결
        textDate = findViewById(R.id.textDate);
        textTitle = findViewById(R.id.textTitle);
        textContents = findViewById(R.id.textContents);


        // -- -- -- 메인 파트 동작 -- -- -- //

        textDate.setText(schedule.getDate());
        textTitle.setText( schedule.getTitle() );
        textContents.setText(schedule.getContents());


        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(ScheduleViewActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(ScheduleViewActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

        // 기능 버튼 동작
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewActivity.this, ScheduleEditActivity.class);
                intent.putExtra("schedule",schedule);
                startActivity(intent);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    void showDialog(){  // 블로그에 넣어서 언제든 복사 붙여넣기 하기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제");
        builder.setMessage("정말 삭제하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewActivity.this);

                ScheduleApi api = retrofit.create(ScheduleApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                Call<Result> call = api.scheduleDelete(schedule.getId(),"Bearer " + token);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        dismissProgress();

                        if(response.isSuccessful()){

                            Intent intent = new Intent(ScheduleViewActivity.this, ScheduleListActivity .class);
                            startActivity(intent);

                            finish();


                        }else{
                            // 유저한테, 서버에 문제있다고 메시지 띄운다.

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        dismissProgress();
                    }
                });

            }
        });
        builder.setNegativeButton("NO",null);
        builder.setCancelable(true);
        builder.show();
    }

    Dialog dialog;

    void showProgress(){
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    void dismissProgress(){
        dialog.dismiss();
    }
}