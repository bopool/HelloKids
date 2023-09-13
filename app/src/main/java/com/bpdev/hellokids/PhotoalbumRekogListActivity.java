package com.bpdev.hellokids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.adapter.PhotoAlbumAdapter;
import com.bpdev.hellokids.adapter.PhotoAlbumRecogAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.PhotoAlbumApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.PhotoAlbumAll;
import com.bpdev.hellokids.model.PhotoAlbumAllList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoalbumRekogListActivity extends AppCompatActivity {

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
    Button btnSelectDate;




    // 사진 관련 버튼
    Button btnCreate;

    // 작성일 선택
    DatePickerDialog datePickerDialog;
    String date1;

    // 리사이클러 뷰
    RecyclerView recyclerView;

    // 스피너, 반 이름
    Spinner spinnerClass2;
    ArrayList<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<NurseryClass> classArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;
    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는 반이름을 클릭하면 그 반이름을 가진 반 데이터의 id를 반환할 때 사용
    int classId1;

    // 사진첩 목록 조회 api에 쓸 것
    ArrayList<PhotoAlbumAll> photoAlbumArrayList = new ArrayList<>(); // api에 쓸 것
    PhotoAlbumRecogAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum_rekog_list);


        // -- -- -- 화면 연결 -- -- -- //

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

        // 메인 파트 버튼 연결
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnCreate = findViewById(R.id.btnCreate);


        // 리사이클러 뷰
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 스피너
        spinnerClass2 = findViewById(R.id.spinnerClass2);



        // -- -- 메인 파트 -- -- //

        // 자동으로 사진첩 목록 불러오기
        //

        Retrofit retrofit1 = NetworkClient.getRetrofitClient(PhotoalbumRekogListActivity.this);

        PhotoAlbumApi api1 = retrofit1.create(PhotoAlbumApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Log.i("token1", token1);

        Call<PhotoAlbumAllList> call1 = api1.photoAlbumRecogList("Bearer " + token1);
        call1.enqueue(new Callback<PhotoAlbumAllList>() {
            @Override
            public void onResponse(Call<PhotoAlbumAllList> call, Response<PhotoAlbumAllList> response) {
                if (response.isSuccessful()) {
                    PhotoAlbumAllList photoAlbumAllList = response.body();

                    photoAlbumArrayList.addAll(photoAlbumAllList.getItems());

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new PhotoAlbumRecogAdapter(PhotoalbumRekogListActivity.this, photoAlbumArrayList);
                    recyclerView.setAdapter(adapter);
                    // scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                } else {

                }
            }

            @Override
            public void onFailure(Call<PhotoAlbumAllList> call, Throwable t) {

            }
        });


        // 스피너
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 반 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumRekogListActivity.this);

        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ClassList> call = api.classListView("Bearer " + token);
        call.enqueue(new Callback<ClassList>() {
            @Override
            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
                if (response.isSuccessful()) {
                    ClassList classList = response.body();
                    classArrayList.addAll(classList.getItems());

                    for (int i = 0; i < classArrayList.size(); i++) {
                        classNameArrayList.add(classArrayList.get(i).getClassName());
                        map.put(classArrayList.get(i).getClassName(), classArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ClassList> call, Throwable t) {
            }
        });

        spinnerClass2.setAdapter(arrayAdapter);

        spinnerClass2.setSelection(0);



        spinnerClass2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                spinnerClass2.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                classId1 = map.get(spinnerValue);

                //String.valueOf(classId1);


                Log.i("classId", classId1 + "");


//                // 반별 일정표 리스트 조회  ------>>> 여기서부터 반 목록 가져오는걸로 바꾸면 된다.
//                Retrofit retrofit1 = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
//
//                ScheduleApi api1 = retrofit1.create(ScheduleApi.class);
//
//                SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//                String token1 = sp1.getString(Config.ACCESS_TOKEN, "");
//
//                Log.i("token1", token1);
//
//                Call<ScheduleList> call1 = api1.scheduleClassList(classId, "Bearer " + token1);
//                call1.enqueue(new Callback<ScheduleList>() {
//                    @Override
//                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                        if (response.isSuccessful()) {
//                            ScheduleList scheduleList1 = response.body();
//
//                            scheduleArrayList.addAll(scheduleList1.getItems());
//
//                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                            adapter = new ScheduleAdapter(PhotoalbumAddActivity.this, scheduleArrayList);
//                            recyclerView.setAdapter(adapter);
//                            scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화
//
//
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//                    }
//                });

            }



            public void onNothingSelected(AdapterView<?> adapterView) { // 아무것도 선택하지 않았을 때 실행되는건데 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다

//                Retrofit retrofit2 = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
//
//                ScheduleApi api2 = retrofit2.create(ScheduleApi.class);
//
//                SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//                String token2 = sp2.getString(Config.ACCESS_TOKEN, "");
//
//                Log.i("token2", token2);
//
//                Call<ScheduleList> call2 = api2.scheduleList("Bearer " + token2);
//                call2.enqueue(new Callback<ScheduleList>() {
//                    @Override
//                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                        if (response.isSuccessful()) {
//                            ScheduleList scheduleList2 = response.body();
//
//                            Log.i("aaa2", scheduleList2.getResult());
//
//                            scheduleArrayList.addAll(scheduleList2.getItems());
//
//                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                            adapter = new ScheduleAdapter(PhotoalbumAddActivity.this, scheduleArrayList);
//
//                            recyclerView.setAdapter(adapter);
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//                    }
//                });

            }
        });





        // 날짜 선택하기
        // - 달력 띄워서 선택 날짜 가져오기
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 달력 띄우기
                Calendar calendar = Calendar.getInstance();
                int year1 = calendar.get(Calendar.YEAR);
                int month1 = calendar.get(Calendar.MONTH);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(PhotoalbumRekogListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                                // 1월부터 시작하는데 시작이 0이므로 +1 해준다
                                month1 = month1 +1;

                                // 10 이하의 날짜가 03 이런식으로 나오게 표시 방법 바꾸기
                                String month;
                                if ( month1 < 10 ){
                                    month = "0" + month1;
                                }else{
                                    month = "" + month1; // 문자열로 만들기
                                }

                                String day;
                                if ( day1 < 10 ){
                                    day = "0" + day1;
                                }else{
                                    day = "" + day1; // 문자열로 만들기
                                }

                                date1 = ""+ year1 + "-" + month + "-" + day;

                                btnSelectDate.setText(date1);
                            }
                        },
                        year1, month1, day1);
                datePickerDialog.show();

            }
        });



        // 원아별 사진첩 만들기 버튼
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this, PhotoalbumRekogActivity.class);
                startActivity(intent);
            }
        });
        











        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRekogListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(PhotoalbumRekogListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(PhotoalbumRekogListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });
    }





}