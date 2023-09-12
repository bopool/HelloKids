package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.BoardingRecord;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusParentListActivity extends AppCompatActivity {

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
    TextView textBusName;
    TextView textBusNum;
    TextView textBusTime;
    Button btnLocation;
    Button btnBoardCancel;
    Button btnBoardApply;


    TextView textBusName1;
    TextView textBusNum1;
    TextView textBusTime1;
    Button btnLocation1;
    Button btnBoardCancel1;
    Button btnBoardApply1;

    String createdAt;

    ArrayList<Bus> busArrayList = new ArrayList<>();

    int id = 1; // 버스 운행기록 id

    String token1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_parent_list);

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        createdAt =formatter.format(today);
        Log.i("createdAt",createdAt);

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
        textBusName = findViewById(R.id.textBusName);
        textBusNum = findViewById(R.id.textBusNum);
        textBusTime = findViewById(R.id.textBusTime);
        btnLocation = findViewById(R.id.btnLocation);
        btnBoardCancel = findViewById(R.id.btnBoardCancel);
        btnBoardApply = findViewById(R.id.btnBoardApply);

        textBusName1 = findViewById(R.id.textBusName1);
        textBusNum1 = findViewById(R.id.textBusNum1);
        textBusTime1 = findViewById(R.id.textBusTime1);
        btnLocation1 = findViewById(R.id.btnLocation1);
        btnBoardCancel1 = findViewById(R.id.btnBoardCancel1);
        btnBoardApply1 = findViewById(R.id.btnBoardApply1);


        // -- -- -- 메인 파트 동작 -- -- -- //
        Retrofit retrofit1 = NetworkClient.getRetrofitClient(SchoolbusParentListActivity.this);

        BusApi api1 = retrofit1.create(BusApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Call<BusList> call1 = api1.busInfoTodayList("2023-09-12", "Bearer " + token1);
        call1.enqueue(new Callback<BusList>() {
            @Override
            public void onResponse(Call<BusList> call, Response<BusList> response) {
                if(response.isSuccessful()){
                    BusList busList = response.body();

                    busArrayList.addAll( busList.getItems() );
                    if( busArrayList.size()!=0) {
                        int hour = Integer.parseInt(busArrayList.get(0).getShuttleTime().substring(11, 12));
                        if (hour == 0) { // 첫번째 버스가 등원일 경우 두번째 버스는 하원버스
                            textBusName.setText(busArrayList.get(0).getShuttleName());
                            int busbusid = busArrayList.get(0).getId();
                            Log.i("busbusid",busbusid+"");

                            textBusNum.setText(busArrayList.get(0).getShuttleNum());
                            textBusTime.setText(busArrayList.get(0).getShuttleTime().substring(11,13)+"시"+busArrayList.get(0).getShuttleTime().substring(14,16)+"분");
                            textBusName1.setText(busArrayList.get(1).getShuttleName());
                            textBusNum1.setText(busArrayList.get(1).getShuttleNum());
                            textBusTime1.setText(busArrayList.get(1).getShuttleTime().substring(11,13)+"시"+busArrayList.get(1).getShuttleTime().substring(14,16)+"분");
                        } else { // 첫번째 버스가 하원일 경우 두번째 버스는 등원버스
                            textBusName.setText(busArrayList.get(1).getShuttleName());
                            textBusNum.setText(busArrayList.get(1).getShuttleNum());
                            textBusTime.setText(busArrayList.get(1).getShuttleTime().substring(11,13)+"시"+busArrayList.get(1).getShuttleTime().substring(14,16)+"분");
                            textBusName1.setText(busArrayList.get(0).getShuttleName());
                            textBusNum1.setText(busArrayList.get(0).getShuttleNum());
                            textBusTime1.setText(busArrayList.get(0).getShuttleTime().substring(11,13)+"시"+busArrayList.get(0).getShuttleTime().substring(14,16)+"분");
                        }


                    }
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<BusList> call, Throwable t) {

            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = busArrayList.get(0).getId();
                String strId = Integer.toString(id);
                Intent intent = new Intent(SchoolbusParentListActivity.this,SchoolbusLocationActivity.class);
                intent.putExtra("strId",strId);
                Log.i("strId",strId);
                startActivity(intent);
            }
        });

        btnLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = busArrayList.get(1).getId();
                String strId = Integer.toString(id);
                Intent intent = new Intent(SchoolbusParentListActivity.this,SchoolbusLocationActivity.class);
                intent.putExtra("strId",strId);
                Log.i("strId",strId);
                startActivity(intent);
            }
        });



        btnBoardApply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 id = busArrayList.get(0).getId();

                 Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusParentListActivity.this);

                 BusApi api2 = retrofit2.create(BusApi.class);

                 //SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                 //String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                 Call<BusRes> call2 = api2.busBoardingAdd(id,"Bearer " + token1);
                 call2.enqueue(new Callback<BusRes>() {
                     @Override
                     public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                         if (response.isSuccessful()) {

                         } else {

                         }
                     }

                     @Override
                     public void onFailure(Call<BusRes> call, Throwable t) {

                     }
                 });
             }
         });

        btnBoardApply1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = busArrayList.get(1).getId();

                Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusParentListActivity.this);

                BusApi api2 = retrofit2.create(BusApi.class);

                //SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                //String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                Call<BusRes> call2 = api2.busBoardingAdd(id,"Bearer " + token1);
                call2.enqueue(new Callback<BusRes>() {
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                        if (response.isSuccessful()) {

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BusRes> call, Throwable t) {

                    }
                });

            }
        });
        btnBoardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = busArrayList.get(0).getId();

                Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusParentListActivity.this);

                BusApi api2 = retrofit2.create(BusApi.class);

                //SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                //String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                Call<BusRes> call2 = api2.busBoardingDelete(id,"Bearer " + token1);
                call2.enqueue(new Callback<BusRes>() {
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                        if (response.isSuccessful()) {

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BusRes> call, Throwable t) {

                    }
                });

            }
        });
        btnBoardCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = busArrayList.get(1).getId();

                Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusParentListActivity.this);

                BusApi api2 = retrofit2.create(BusApi.class);

                //SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                //String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

                Call<BusRes> call2 = api2.busBoardingDelete(id,"Bearer " + token1);
                call2.enqueue(new Callback<BusRes>() {
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                        if (response.isSuccessful()) {

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BusRes> call, Throwable t) {

                    }
                });

            }
        });




        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusParentListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusParentListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼



        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusParentListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusParentListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusParentListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SchoolbusParentListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SchoolbusParentListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


    }
}