package com.bpdev.hellokids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    // 최상단 헤더의 버튼
    TextView btnRegister;
    TextView btnLogin;
    ImageButton btnTranslate;
    Button btnBack;

    // 메인 기능
    EditText userName;
    EditText userId;
    EditText editPassword;
    EditText editPassword2;
    EditText editEmail;
    EditText editPhoneNumber;
    Button registerBtn;

    // 하단 바로가기 메뉴 버튼
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;




    // -- -- -- -- 선생님 회원가입 -- -- -- -- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        editPassword2 = findViewById(R.id.editPassword2);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        registerBtn = findViewById(R.id.registerBtn);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);






        // -- -- -- 하단 바로가기 메뉴 버튼 -- -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(RegisterActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(RegisterActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });






        // -- -- -- 메인 파트 동작 -- -- -- //

        // '회원가입하기' 버튼 눌렀을 때
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =  editEmail.getText().toString().trim();

//                Pattern pattern = Patterns.EMAIL_ADDRESS;
//                if (pattern.matcher(email).matches() == false){
//                    Snackbar.make(btnRegister,
//                            "이메일 형식을 확인하세요.",
//                            Snackbar.LENGTH_SHORT).show();
//                    return;
//                }

                String password = editPassword.getText().toString().trim();

                if (password.length() < 4 || password.length() > 12) {
                    Snackbar.make(registerBtn,
                            "비밀번호 길이를 확인하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String teacherName = userName.getText().toString().trim();
                String id = userId.getText().toString().trim();
                String phone =editPhoneNumber.getText().toString().trim();

                // 회원가입 API 를 호출한다.

                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                UserApi api = retrofit.create(UserApi.class);

                // 3. 보낼 데이터를 준비한다.
                User user = new User(teacherName, id, password, email, phone);

                Call<UserRes> call = api.register(user);

                call.enqueue(new Callback<UserRes>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {


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
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();

                            // 이렇게 상태코드써서 코드짜면 클라이언트 개발자가 코드짜기 쉽다
                        } else if (response.code() == 400) {
                            Snackbar.make(registerBtn, "이미 회원가입 되어있습니다. 로그인 하세요.", Snackbar.LENGTH_SHORT).show();
                            return;

                        } else if (response.code() == 401) {

                        } else if (response.code() == 404) {

                        } else if (response.code() == 500) {

                        } else {
                            // 200OK 아닌경우

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });

            }

        });


        // '뒤로가기(화살표)' 버튼 눌렀을 때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}

