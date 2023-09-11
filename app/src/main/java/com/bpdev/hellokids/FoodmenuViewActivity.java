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


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bpdev.hellokids.api.FoodMenuApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuView;
import com.bpdev.hellokids.model.Result;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodmenuViewActivity extends AppCompatActivity {

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
    TextView textContent;
    TextView contentType;
    Button btnEdit;
    Button btnDelete;
    int index;
    int id;

    FoodMenu foodMenu;
    ArrayList<FoodMenu> foodMenuArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu_view);

        // 화면 연결 //

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

        // 메인파트
        textDate = findViewById(R.id.textDate);
        photoContent = findViewById(R.id.photoContent);
        textContent = findViewById(R.id.textContent);
        contentType = findViewById(R.id.contentType);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        foodMenu = (FoodMenu) intent.getSerializableExtra("foodMenu");
        id = foodMenu.getId();

        Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuViewActivity.this);
        FoodMenuApi api = retrofit.create(FoodMenuApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN,"");
        Call<FoodMenuView> call = api.foodMenuView("Bearer " + token, id);
        call.enqueue(new Callback<FoodMenuView>() {
            @Override
            public void onResponse(Call<FoodMenuView> call, Response<FoodMenuView> response) {
                if(response.isSuccessful()){

                    foodMenu = response.body().getItem();
                    Log.i("뷰액티비티 foodMenuArrayList", "foodMenu : "+foodMenu);
                    textDate.setText(foodMenu.getMealDate());
                    textContent.setText(foodMenu.getMealContent());
                    contentType.setText(foodMenu.getMealType());
                    Glide.with(FoodmenuViewActivity.this)
                            .load(foodMenu.getMealPhotoUrl())
                            .into(photoContent);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<FoodMenuView> call, Throwable t) {

            }

        });




        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FoodmenuViewActivity.this, FoodmenuEditActivity.class);
                intent.putExtra("foodMenu",foodMenu);
                intent.putExtra("index",index);
                startActivity(intent);

            }
        });




        // 최상단 헤더 버튼 //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼



        // 하단 바로가기 메뉴 버튼 //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuViewActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(FoodmenuViewActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(FoodmenuViewActivity.this, SettingListActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();


    }

    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제");
        builder.setMessage("정말 삭제하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuViewActivity.this);
                FoodMenuApi api = retrofit.create(FoodMenuApi.class);
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");
                Call<Result> call = api.foodMenuDelete("Bearer " + token, id);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        dismissProgress();

                        if(response.isSuccessful()){

                            // 선생님일 때
//                            Intent intent = new Intent(DailynoteViewActivity.this, DailynoteListActivity .class);
//                            startActivity(intent);
                            // 학부모일 때
                            Intent intent = new Intent(FoodmenuViewActivity.this, FoodmenuListActivity .class);
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