package com.bpdev.hellokids;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.adapter.NoticeAdapter;
import com.bpdev.hellokids.api.NoticeApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.NoticeRes;
import com.bpdev.hellokids.model.Result;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeViewActivity extends AppCompatActivity {

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
    TextView textDate;
    ImageView photoContent;
    TextView textContents;
    TextView textTitle;
    Button btnEdit;
    Button btnDelete;


    Notice notice;
    int index;
    int id;
    ArrayList<Notice> noticeArrayList;
    NoticeAdapter noticeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);

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


        // 메인파트
        textDate = findViewById(R.id.textDate);
        photoContent = findViewById(R.id.photoContent);
        textContents = findViewById(R.id.textContents);
        textTitle = findViewById(R.id.textTitle);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);


        index = getIntent().getIntExtra("index", 0);
        notice = (Notice) getIntent().getSerializableExtra("notice");
        id = notice.getId();

        Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeViewActivity.this);
        NoticeApi noticeApi = retrofit.create(NoticeApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN,"");
        Call<NoticeRes> call = noticeApi.noticeView("Bearer " + token, id);
        call.enqueue(new Callback<NoticeRes>() {
            @Override
            public void onResponse(Call<NoticeRes> call, Response<NoticeRes> response) {
                if(response.isSuccessful()){
                    Log.i("뷰액티비티 noticeArrayList", ""+ response.body().getItems() + ", " + notice+ ", " + index);
                    notice = response.body().getItems().get(0);
                    textDate.setText(notice.getNoticeDate());
                    textContents.setText(notice.getNoticeContents());
                    textTitle.setText(notice.getNoticeTitle());
                    Glide.with(NoticeViewActivity.this)
                            .load(notice.getNoticePhotoUrl())
                            .into(photoContent);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<NoticeRes> call, Throwable t) {

            }

        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoticeViewActivity.this, NoticeEditActivity.class);
                intent.putExtra("notice",notice);
                intent.putExtra("index",index);
                startActivity(intent);

            }
        });






        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeViewActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeViewActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼






        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeViewActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeViewActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(NoticeViewActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(NoticeViewActivity.this, SettingListActivity.class);
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


    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제");
        builder.setMessage("정말 삭제하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showProgress();
                index = getIntent().getIntExtra("index", 0);
                Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeViewActivity.this);
                NoticeApi api = retrofit.create(NoticeApi.class);
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");
                Call<Result> call = api.noticeDelete("Bearer " + token, id);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        dismissProgress();
                        if(response.isSuccessful()){
                            // 선생님일 때
//                            Intent intent = new Intent(DailynoteViewActivity.this, DailynoteListActivity .class);
//                            startActivity(intent);
                            // 학부모일 때
                            Intent intent = new Intent(NoticeViewActivity.this, NoticeListActivity .class);
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