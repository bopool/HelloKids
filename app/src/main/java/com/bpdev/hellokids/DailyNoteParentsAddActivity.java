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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DailyNoteParentsAddActivity extends AppCompatActivity {

    private int childId = 4;
    private String title;
    private String contents;
    private String dailyTemperCheck;
    private String dailyMealCheck;
    private String dailyNapCheck;
    private String dailyPooCheck;


    ArrayList<Child> childArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;
    HashMap<String, Integer> map = new HashMap<>();


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
    Button btnCreate;

    // 메인 기능

    EditText editInputTitle;
    EditText editInputContents;
    EditText editInputTemp;

    RadioGroup radioGroupMeal;

    RadioButton radioMealSmall;
    RadioButton radioMealMiddle;
    RadioButton radioMealBig;

    RadioGroup radioGroupNap;

    RadioButton radioNapSmall;
    RadioButton radioNapMiddle;
    RadioButton radioNapBig;

    RadioGroup radioGroupPoo;

    RadioButton radioPooSmall;
    RadioButton radioPooMiddle;
    RadioButton radioPooBig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_note_parents_add);
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
        btnCreate = findViewById(R.id.btnCreate);
        editInputTitle = findViewById(R.id.editInputTitle);
        editInputContents = findViewById(R.id.editInputContents);
        editInputTemp = findViewById(R.id.editInputTemp);

        radioGroupMeal = findViewById(R.id.radioGroupMeal);
        radioGroupNap = findViewById(R.id.radioGroupNap);
        radioGroupPoo = findViewById(R.id.radioGroupPoo);

        radioMealSmall = findViewById(R.id.radioMealSmall);
        radioMealMiddle = findViewById(R.id.radioMealMiddle);
        radioMealBig = findViewById(R.id.radioMealBig);
        radioNapSmall = findViewById(R.id.radioNapSmall);
        radioNapMiddle = findViewById(R.id.radioNapMiddle);
        radioNapBig = findViewById(R.id.radioNapBig);
        radioPooSmall = findViewById(R.id.radioPooSmall);
        radioPooMiddle = findViewById(R.id.radioNapMiddle);
        radioPooBig = findViewById(R.id.radioPooBig);


        radioGroupMeal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMealSmall) {
                    dailyMealCheck = "적게";
                } else if (checkedId == R.id.radioMealMiddle) {
                    dailyMealCheck = "보통";
                } else if (checkedId == R.id.radioMealBig) {
                    dailyMealCheck = "많이";
                }
            }
        });
        radioGroupNap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioNapSmall) {
                    dailyNapCheck = "적게";
                } else if (checkedId == R.id.radioNapMiddle) {
                    dailyNapCheck = "보통";
                } else if (checkedId == R.id.radioNapBig) {
                    dailyNapCheck = "많이";
                }
            }
        });
        radioGroupPoo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioPooSmall) {
                    dailyPooCheck = "적게";
                } else if (checkedId == R.id.radioPooMiddle) {
                    dailyPooCheck = "보통";
                } else if (checkedId == R.id.radioPooBig) {
                    dailyPooCheck = "많이";
                }
            }
        });



        // -- -- -- 메인 파트 동작 -- -- -- //

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title =  editInputTitle.getText().toString().trim();
                contents = editInputContents.getText().toString().trim();
                dailyTemperCheck = editInputTemp.getText().toString().trim();

                // API 를 호출한다.

                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(DailyNoteParentsAddActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                DailyNoteApi api = retrofit.create(DailyNoteApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");

                // 3. 보낼 데이터를 준비한다.
                DailyNote dailyNote = new DailyNote(title,contents,dailyTemperCheck,dailyMealCheck,dailyNapCheck,dailyPooCheck);

                Call<Result> call = api.dailyNoteParentsAdd("Bearer "+token,dailyNote);

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
                            Intent intent = new Intent(DailyNoteParentsAddActivity.this,DailyNoteParentsListActivity.class);
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
                Intent intent = new Intent(DailyNoteParentsAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyNoteParentsAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyNoteParentsAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyNoteParentsAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyNoteParentsAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(DailyNoteParentsAddActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(DailyNoteParentsAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


    }
}