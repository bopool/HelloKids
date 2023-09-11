package com.bpdev.hellokids;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.TranslateApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.DailyNote;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.ResultText;
import com.bpdev.hellokids.model.ScheduleRes;
import com.bpdev.hellokids.model.TranText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleViewParentsActivity extends AppCompatActivity {

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

    // 메인 기능
    TextView textDate;
    TextView textTitle;
    TextView textContents;

    TextView textEn;
    TextView textCn;
    TextView textVi;
    TextView textTh;
    TextView textJa;

    CardView cardViewEn;
    CardView cardViewCn;
    CardView cardViewVi;
    CardView cardViewTh;
    CardView cardViewJa;

    ScheduleRes schedule;

    ResultText resultText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view_parents);

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



        // 메인 파트 화면 연결
        textDate = findViewById(R.id.textDate);
        textTitle = findViewById(R.id.textTitle);
        textContents = findViewById(R.id.textContents);

        textEn = findViewById(R.id.textEn);
        textCn = findViewById(R.id.textCn);
        textVi = findViewById(R.id.textVi);
        textTh = findViewById(R.id.textTh);
        textJa = findViewById(R.id.textJa);

        cardViewEn = findViewById(R.id.cardViewEn);
        cardViewCn = findViewById(R.id.cardViewCn);
        cardViewVi = findViewById(R.id.cardViewVi);
        cardViewTh = findViewById(R.id.cardViewTh);
        cardViewJa = findViewById(R.id.cardViewJa);


        // -- -- -- 메인 파트 동작 -- -- -- //

        textDate.setText(schedule.getDate());
        textTitle.setText( schedule.getTitle() );
        textContents.setText(schedule.getContents());


        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEn.setVisibility(View.VISIBLE);
                textCn.setVisibility(View.VISIBLE);
                textVi.setVisibility(View.VISIBLE);
                textTh.setVisibility(View.VISIBLE);
                textJa.setVisibility(View.VISIBLE);

                cardViewEn.setVisibility(View.VISIBLE);
                cardViewCn.setVisibility(View.VISIBLE);
                cardViewVi.setVisibility(View.VISIBLE);
                cardViewTh.setVisibility(View.VISIBLE);
                cardViewJa.setVisibility(View.VISIBLE);
            }
        });

        textEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textT = textTitle.getText().toString().trim();
                String textC = textContents.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewParentsActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                TranslateApi api = retrofit.create(TranslateApi.class);

                // 3. 보낼 데이터를 준비한다.
                TranText tranText = new TranText("en",textT);
                TranText tranText1 = new TranText("en",textC);

                Call<ResultText> call = api.translate(tranText);
                Call<ResultText> call1 = api.translate(tranText1);

                call.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textTitle.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
                call1.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textContents.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
            }
        });

        textCn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textT = textTitle.getText().toString().trim();
                String textC = textContents.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewParentsActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                TranslateApi api = retrofit.create(TranslateApi.class);

                // 3. 보낼 데이터를 준비한다.
                TranText tranText = new TranText("zh-CN",textT);
                TranText tranText1 = new TranText("zh-CN",textC);

                Call<ResultText> call = api.translate(tranText);
                Call<ResultText> call1 = api.translate(tranText1);

                call.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textTitle.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
                call1.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textContents.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
            }
        });

        textVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textT = textTitle.getText().toString().trim();
                String textC = textContents.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewParentsActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                TranslateApi api = retrofit.create(TranslateApi.class);

                // 3. 보낼 데이터를 준비한다.
                TranText tranText = new TranText("vi",textT);
                TranText tranText1 = new TranText("vi",textC);

                Call<ResultText> call = api.translate(tranText);
                Call<ResultText> call1 = api.translate(tranText1);

                call.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textTitle.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
                call1.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textContents.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
            }
        });

        textTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textT = textTitle.getText().toString().trim();
                String textC = textContents.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewParentsActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                TranslateApi api = retrofit.create(TranslateApi.class);

                // 3. 보낼 데이터를 준비한다.
                TranText tranText = new TranText("th",textT);
                TranText tranText1 = new TranText("th",textC);

                Call<ResultText> call = api.translate(tranText);
                Call<ResultText> call1 = api.translate(tranText1);

                call.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textTitle.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
                call1.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textContents.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
            }
        });

        textJa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textT = textTitle.getText().toString().trim();
                String textC = textContents.getText().toString().trim();
                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(ScheduleViewParentsActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                TranslateApi api = retrofit.create(TranslateApi.class);

                // 3. 보낼 데이터를 준비한다.
                TranText tranText = new TranText("ja",textT);
                TranText tranText1 = new TranText("ja",textC);

                Call<ResultText> call = api.translate(tranText);
                Call<ResultText> call1 = api.translate(tranText1);

                call.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textTitle.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
                call1.enqueue(new Callback<ResultText>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<ResultText> call, Response<ResultText> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {
                            resultText = response.body();
                            textContents.setText(resultText.getText());


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
                    public void onFailure(Call<ResultText> call, Throwable t) {

                    }
                });
            }
        });



        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewParentsActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //학부모화면
                Intent intent = new Intent(ScheduleViewParentsActivity.this, SchoolbusParentListActivity.class);
                startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScheduleViewParentsActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

    }

}