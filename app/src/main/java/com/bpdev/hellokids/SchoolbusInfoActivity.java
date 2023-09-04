package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

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

import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.Location;
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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

public class SchoolbusInfoActivity extends AppCompatActivity {

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
    LocationManager locationManager;
    LocationListener locationListener;

    double myLng, myLat;

    Button btnStart;
    Button btnEnd;

    Location location1 = new Location(0,0);






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_info);

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

        String strId = getIntent().getStringExtra("strId");
        int id = Integer.parseInt(strId);

        // 1. 레트로핏 변수 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusInfoActivity.this);

        // 2. api 패키지의 인터페이스 생성.
        //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
        //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

        BusApi api = retrofit.create(BusApi.class);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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