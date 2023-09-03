package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.model.User1;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity1 extends AppCompatActivity {

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
    EditText userName;
    EditText userId;
    EditText userPassword;
    EditText userPassword2;
    EditText userEmail;
    EditText userPhoneNumber;
    EditText userKidsName;
    EditText userKidsBirth;
    Button registerBtn;





    // -- -- -- -- 학부모 회원가입 -- -- -- -- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        // -- -- -- 화면 연결 -- -- -- //
        // 최상단 헤더 버튼 화면 연결
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        // 메인 파트 화면 연결
        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.editPassword);
        userPassword2 = findViewById(R.id.editPassword2);
        userEmail = findViewById(R.id.editEmail);
        userPhoneNumber = findViewById(R.id.editPhoneNumber);
        userKidsName = findViewById(R.id.userKidsName);
        userKidsBirth = findViewById(R.id.userKidsBirth);
        registerBtn = findViewById(R.id.registerBtn);

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
                Intent intent = new Intent(RegisterActivity1.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity1.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼






        // -- -- -- 하단 바로가기 메뉴 버튼 -- -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity1.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity1.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(RegisterActivity1.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(RegisterActivity1.this, SettingListActivity.class);
                startActivity(intent);
            }
        });





        // -- -- -- 메인 파트 동작 -- -- -- //

        // '회원가입하기' 버튼 눌렀을 때
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userEmail.getText().toString().trim();

//                Pattern pattern = Patterns.EMAIL_ADDRESS;
//                if (pattern.matcher(email).matches() == false){
//                    Snackbar.make(btnRegister,
//                            "이메일 형식을 확인하세요.",
//                            Snackbar.LENGTH_SHORT).show();
//                    return;
//                }

                String password = userPassword.getText().toString().trim();

                if(password.length() < 4 || password.length() > 12){
                    Snackbar.make(registerBtn,
                            "비밀번호 길이를 확인하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String parentsName = userName.getText().toString().trim();
                String id = userId.getText().toString().trim();
                String phone = userPhoneNumber.getText().toString().trim();
                String childNameP = userKidsName.getText().toString().trim();
                String birthP = userKidsBirth.getText().toString().trim();

                // 회원가입 API 를 호출한다.

                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity1.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                UserApi api = retrofit.create(UserApi.class);

                // 3. 보낼 데이터를 준비한다.
                User1 user1 = new User1(parentsName, id, password, email, phone,childNameP,birthP);

                Call<UserRes> call = api.register1(user1);

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
                            Intent intent = new Intent(RegisterActivity1.this, MainActivity.class);
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



    }
}