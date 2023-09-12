package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.model.ChildId;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ParentsApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserCheck;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    // 최상단 헤더의 버튼
    TextView btnRegister;
    ImageButton btnTranslate;

    // 하단 바로가기 메뉴 버튼
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;

    // 메인 파트 버튼
    EditText editEmail;
    EditText editPassword;
    Button registerBtn;

    String email;
    int isTeacher;
    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // -- -- -- 화면 연결 -- -- -- //

        // 최상단 헤더 버튼 화면 연결
        btnRegister = findViewById(R.id.btnRegister);
        btnTranslate = findViewById(R.id.btnTranslate);

        //
        editEmail = findViewById(R.id.editUserEmail);
        editPassword = findViewById(R.id.editUserPassword);
        registerBtn = findViewById(R.id.btnlogin);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);






        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 번역 버튼







        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(LoginActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(LoginActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });





        // -- -- -- 메인 파트 동작 -- -- -- //

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(email).matches() == false){
                    Snackbar.make(registerBtn,
                            "이메일 형식을 바르게 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 4 || password.length() > 12 ){
                    Snackbar.make(registerBtn,
                            "비밀번호 길이가 잘못되었습니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }


                Retrofit retrofit = NetworkClient.getRetrofitClient(LoginActivity.this);
                UserApi api = retrofit.create(UserApi.class);
                User user = new User(email, password);
                Call<UserRes> call = api.login(user);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {

                        if( response.isSuccessful() ){
                            // 200 OK 일때의 코드 작성.
                            UserRes res = response.body();

                            String accessToken = res.getAccess_token();

                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, accessToken);
                            editor.apply();

                            UserApi userApi = retrofit.create(UserApi.class);
                            Call<UserCheck> call2 = userApi.userCheck("Bearer "+accessToken, email);

                            call2.enqueue(new Callback<UserCheck>() {
                                @Override
                                public void onResponse(Call<UserCheck> call2, Response<UserCheck> response2) {
                                    if (response2.isSuccessful()) {

                                        isTeacher = response2.body().getIsTeacher();

                                        if( isTeacher == 1 ){
                                            // 선생님인 경우
                                            Intent intent = new Intent(LoginActivity.this, MainTeacherActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // 선생님이 아닌 경우
                                            ParentsApi parentsApi = retrofit.create(ParentsApi.class);
                                            Call<ChildId> call1 = parentsApi.parentsWaiting("Bearer "+accessToken);

                                            call1.enqueue(new Callback<ChildId>() {
                                                @Override
                                                public void onResponse(Call<ChildId> call1, Response<ChildId> responseCheck) {
                                                    if (responseCheck.isSuccessful()) {

                                                        number = responseCheck.body().getNum();

                                                        Log.i("로그인액티비티 number", "number : " + responseCheck.body().getNum() + number );

                                                        if( number == 0 ){
                                                            // 승인한 경우
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else if (number == 1) {
                                                            // 미승인한 경우
                                                            Intent intent = new Intent(LoginActivity.this, MainWaitingActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Log.i("로그인액티비티 0도 1도 아닐 때", "num : " + number );

                                                        }

                                                    } else {

                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<ChildId> call1, Throwable t) {
                                                    Log.i("로그인액티비티 number onFailure", "num : " + number +", t: "+ t  );

                                                }
                                            });
                                        }
                                    } else {
//                                       num = 1;
                                    }
                                }
                                @Override
                                public void onFailure(Call<UserCheck> call2, Throwable t) {
                                    Log.i("로그인액티비티 유저 구분", "isTeacher : " + isTeacher +", t: "+ t  );

                                }
                            });

                        } else if (response.code() == 400){
                            Snackbar.make(registerBtn,
                                    "비밀번호가 맞지 않습니다.",
                                    Snackbar.LENGTH_SHORT).show();
                            return;
                        } else {
                            Snackbar.make(registerBtn,
                                    "서버에 문제가 있습니다.",
                                    Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });
            }
        });

    }
}