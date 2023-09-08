package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.adapter.BusInfoAdapter;
import com.bpdev.hellokids.adapter.ChildAdapter;
import com.bpdev.hellokids.adapter.DailyNoteAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.DailyNoteApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusInfoList;
import com.bpdev.hellokids.model.BusList;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.DailyNoteRowList;
import com.bpdev.hellokids.model.Location;
import com.bpdev.hellokids.model.Teacher;
import com.bpdev.hellokids.model.TeacherRes;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SchoolbusInfoActivity extends AppCompatActivity {

    int id; // 차량 운행 기록 id
    int busId; // 해당 차량 운행 기록에 들어간 버스 id
    int teacherId; // 인솔교사 id

    String shuttleStart;
    String shuttleStop;

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
    LocationManager locationManager;
    LocationListener locationListener;

    double myLng, myLat;

    Button btnStart;
    Button btnEnd;
    Button btnChildCheck;
    Location location1 = new Location(0,0);
    
    TextView textToday;
    TextView textBigBusName;
    TextView textBusType;
    
    TextView textTeacherName;
    
    TextView textSmallBusName;
    TextView textBusNum;
    TextView textDriverName;
    TextView textDriverNum;

    ArrayList<Bus> busArrayList = new ArrayList<>();
    ArrayList<Teacher> teacherArrayList = new ArrayList<>();
    ArrayList<Child> childArrayList = new ArrayList<>();

    ChildAdapter adapter;

    RecyclerView recyclerView;

    String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_info);

        id = getIntent().getIntExtra("id",0);
        busId = getIntent().getIntExtra("busId",0);
        teacherId = getIntent().getIntExtra("teacherId",0);

        Log.i("id",id+"");
        Log.i("busId",busId+"");
        Log.i("teacherId",teacherId+"");


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
        btnStart = findViewById(R.id.btnStart);
        btnEnd = findViewById(R.id.btnEnd);
        btnChildCheck = findViewById(R.id.btnChildCheck);

        // 메인 기능 연결

        textToday = findViewById(R.id.textToday);
        textBigBusName = findViewById(R.id.textBigBusName);
        textBusType = findViewById(R.id.textBusType);
        textTeacherName = findViewById(R.id.textTeacherName);
        textSmallBusName = findViewById(R.id.textSmallBusName);
        textBusNum = findViewById(R.id.textBusNum);
        textDriverName = findViewById(R.id.textDriverName);
        textDriverNum = findViewById(R.id.textDriverNum);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼






        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(SchoolbusInfoActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(SchoolbusInfoActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


        // -- -- -- 메인 파트 동작 -- -- -- //

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy년 MM월 dd일"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        todayDate =formatter.format(today);

        textToday.setText(todayDate);

        // 1. 레트로핏 변수 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

        // 2. api 패키지의 인터페이스 생성.
        //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
        //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

        BusApi api = retrofit.create(BusApi.class);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date today = new Date();
                Locale currentLocale = new Locale("KOREAN", "KOREA");
                String pattern = "yyyy-MM-dd HH:mm:ss"; //hhmmss로 시간,분,초만 뽑기도 가능
                SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
                shuttleStart =formatter.format(today);
                Log.i("shuttleStart",shuttleStart);
                // 3.
                // 핸드폰의 위치를 가져오기 위해서는 시스템서비스로부터 로케이션 매니저를 받아온다
                // --> 빨간줄 뜨면 캐스트 하기
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // 3-1.
                // 로케이션 리스너를 만든다
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull android.location.Location location) {


                        // 이동해서 위치가 변하면 그때마다 실행된다
                        // 여기에 로직을 작성한다
                        // -- 예시 : 위치가 바뀌면 알람소리를 내겠다, 위도와경도의 정보를 표시하겠다...

                        // - (@NonNull Location location) 의 location에 위도와 경도의 정보가 있음.
                        // 위도 가져오는 코드
                        myLat = location.getLatitude();
                        // 경도 가져오는 코드
                        myLng = location.getLongitude();

                        Log.i("myLat",myLat+"");
                        Log.i("myLng",myLng+"");

                        location1.setLat(myLat);
                        location1.setLng(myLng);

                        Call<BusRes> call = api.addLocation(id,location1);

                        call.enqueue(new Callback<BusRes>() { // 받아왔을때 처리하는 코드
                            @Override
                            public void onResponse(Call<BusRes> call, Response<BusRes> response) {


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
                            public void onFailure(Call<BusRes> call, Throwable t) {

                            }
                        });

                        //Log.i("myLocation", "위도 : "+location.getLatitude());
                        //Log.i("myLocation", "경도 : "+location.getLongitude());
                    }
                };

                // 3-2. 위치 정보 허용 요청하기 (매뉴얼)
                if(ActivityCompat.checkSelfPermission(
                        SchoolbusInfoActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){

                    // 3-2. 위치기반서비스 사용하겠다고 허용 안했다면 허용 해 달라고 요청한다.
                    ActivityCompat.requestPermissions(
                            SchoolbusInfoActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            100);
                    return;
                }

                // 위치기반을 허용하였으므로
                // 로케이션 매니저에 리스너를 연결한다.
                // 파라미터 설명
                // - LocationManager.GPS_PROVIDER : 위치 정보 확인
                // - 숫자 : 몇초에 한번씩 위치를 파악하는가 (1000 = 1초)
                // - 숫자 : 미터. 몇 미터 갈때마다 파악하는가? (미터단위) : 마이너스라면 위치 안 재고, 설정한 초 마다 위치 파악
                // - : 몇초마다 또는 몇 미터마다 움직였을때 어떤 코드를 사용할까?
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, -1, locationListener);

            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(locationListener);
                Date today1 = new Date();
                Locale currentLocale1 = new Locale("KOREAN", "KOREA");
                String pattern1 = "yyyy-MM-dd HH:mm:ss"; //hhmmss로 시간,분,초만 뽑기도 가능
                SimpleDateFormat formatter1 = new SimpleDateFormat(pattern1, currentLocale1);
                shuttleStop =formatter1.format(today1);
                Log.i("shuttleStop",shuttleStop);

                Retrofit retrofit3 = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

                BusApi api3 = retrofit3.create(BusApi.class);

                SharedPreferences sp3 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token3 = sp3.getString(Config.ACCESS_TOKEN, "");

                BusDailyRecord busDailyRecord = new BusDailyRecord(shuttleStart,shuttleStop);

                Call<BusRes> call3 = api3. putShuttleTime(id, "Bearer " + token3,busDailyRecord);
                call3.enqueue(new Callback<BusRes>() {
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                        if(response.isSuccessful()){


                        }

                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<BusRes> call, Throwable t) {

                    }
                });


            }
        });

        btnChildCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusInfoActivity.this, SchoolbusAddChildActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Retrofit retrofit1 = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

        BusApi api1 = retrofit1.create(BusApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Call<BusList> call1 = api1.busView(busId, "Bearer " + token1);
        call1.enqueue(new Callback<BusList>() {
            @Override
            public void onResponse(Call<BusList> call, Response<BusList> response) {
                if(response.isSuccessful()){
                    BusList busList = response.body();

                    busArrayList.addAll( busList.getItems() );
                    if( busArrayList.size()!=0) {
                        textBigBusName.setText(busArrayList.get(0).getShuttleName());
                        textSmallBusName.setText(busArrayList.get(0).getShuttleName());
                        textBusNum.setText(busArrayList.get(0).getShuttleNum());
                        int hour = Integer.parseInt(busArrayList.get(0).getShuttleTime().substring(11, 12));
                        if (hour == 0) {
                            textBusType.setText("등원");
                        } else {
                            textBusType.setText("하원");
                        }
                        textDriverName.setText(busArrayList.get(0).getShuttleDriver());
                        textDriverNum.setText(busArrayList.get(0).getShuttleDriverNum());
                    }
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<BusList> call, Throwable t) {

            }
        });


        Retrofit retrofit2 = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

        UserApi api2 = retrofit2.create(UserApi.class);

        SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token2 = sp2.getString(Config.ACCESS_TOKEN, "");

        Call<TeacherRes> call2 = api2.teacherView(teacherId, "Bearer " + token2);
        call2.enqueue(new Callback<TeacherRes>() {
            @Override
            public void onResponse(Call<TeacherRes> call, Response<TeacherRes> response) {
                if (response.isSuccessful()) {
                    TeacherRes teacherRes= response.body();

                    teacherArrayList.addAll(teacherRes.getItems());
                    if( teacherArrayList.size()!=0) {
                        textTeacherName.setText(teacherArrayList.get(0).getTeacherName());
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<TeacherRes> call, Throwable t) {

            }
        });

        Retrofit retrofit3 = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

        BusApi api3 = retrofit3.create(BusApi.class);

        SharedPreferences sp3 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token3 = sp3.getString(Config.ACCESS_TOKEN, "");

        Call<ChildList> call3 = api3.busBoardingList(id,"Bearer "+ token3);
        call3.enqueue(new Callback<ChildList>() {
            @Override
            public void onResponse(Call<ChildList> call, Response<ChildList> response) {
                if(response.isSuccessful()){
                    ChildList childList = response.body();

                    childArrayList.addAll( childList.getItems() );

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new ChildAdapter(SchoolbusInfoActivity.this, childArrayList);

                    recyclerView.setAdapter(adapter);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<ChildList> call, Throwable t) {

            }
        });



    }



    // 4.
    // 3-2 에서 연결
    // 위치기반 서비스 권한 허용했는지
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 위치기반 허용하겠다 했다면 --> 다시한번 허용했는지 확인
        if (requestCode == 100) {

            if (ActivityCompat.checkSelfPermission(
                    SchoolbusInfoActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // 3-2. 위치기반서비스 사용하겠다고 허용 안했다면 허용 해 달라고 요청한다.
                ActivityCompat.requestPermissions(
                        SchoolbusInfoActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        100);
                return;
            }

            // 위치기반을 허용하였으므로
            // 로케이션 매니저에 리스너를 연결한다.
            // 파라미터 설명
            // - LocationManager.GPS_PROVIDER : 위치 정보 확인
            // - 숫자 : 몇초에 한번씩 위치를 파악하는가 (1000 = 1초)
            // - 숫자 : 미터. 몇 미터 갈때마다 파악하는가? (미터단위) : 마이너스라면 위치 안 재고 설정한 초 마다 위치 파악
            // - : 몇초마다 또는 몇 미터마다 움직였을때 어떤 코드를 사용할까?
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, -1, locationListener);

        }
    }
}