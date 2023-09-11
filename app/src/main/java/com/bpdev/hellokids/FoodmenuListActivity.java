package com.bpdev.hellokids;

import static android.app.PendingIntent.getActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.FoodMenuAdapter;
import com.bpdev.hellokids.api.FoodMenuApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuList;

import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodmenuListActivity extends AppCompatActivity {

    // 메인 기능
    DatePickerDialog datePickerDialog;
    String date1;
    Button btnSelectDate;

    RecyclerView recyclerView;
    FoodMenuAdapter foodMenuAdapter;
    ArrayList<FoodMenu> foodMenuArrayList = new ArrayList<>();


    // 최상단 헤더의 버튼
    TextView btnRegister1;
    TextView btnLogin;
    ImageButton btnTranslate;

    // 하단 바로가기 메뉴 버튼
    Button btnBottomHome;
    Button btnBottomNotice;
    Button btnBottomDailyNote;
    Button btnBottomSchoolbus;
    Button btnBottomSetting;

    // 화면 이동
    Button btnCreate;

    // 페이징 처리
    int offset = 0;
    int limit = 6;
    int count = 0;
    String token;


    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override // ActivityResult가 있다면 동작하라.
                        public void onActivityResult(ActivityResult result) {

                            // Add Activity로 부터 데이터를 받는 경우
                            if( result.getResultCode() == 1 ){
                                FoodMenu foodMenu = (FoodMenu) result.getData().getSerializableExtra("foodMenu"); // 보낸 데이터들 불러오기
                                foodMenuArrayList.add(0, foodMenu); // 목록에 추가
                                foodMenuAdapter.notifyDataSetChanged(); // 화면 갱신

                            } else if( result.getResultCode() == 2 ){
                                FoodMenu foodMenu = (FoodMenu) result.getData().getSerializableExtra("foodMenu"); // 보낸 데이터들 불러오기
                                int index = result.getData().getIntExtra("index", 0); // 보낸 인덱스 데이터도 불러오기
                                foodMenuArrayList.set(index, foodMenu); // 이 인덱스 데이터 업데이트 해주세요!
                                foodMenuAdapter.notifyDataSetChanged(); // 화면 갱신

                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu_list);

        // 억세스토큰이 있는지를 확인
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        token = sp.getString(Config.ACCESS_TOKEN, "");

        if (token.isEmpty()) {
            Intent intent = new Intent(FoodmenuListActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();
            return;
        }

        // 최상단 헤더 버튼 화면 연결
        btnRegister1 = findViewById(R.id.btnRegister1);
        btnLogin = findViewById(R.id.btnLogin);
        btnTranslate = findViewById(R.id.btnTranslate);

        // 하단 바로가기 메뉴 화면 연결
        btnBottomHome = findViewById(R.id.btnBottomHome);
        btnBottomNotice = findViewById(R.id.btnBottomNotice);
        btnBottomDailyNote = findViewById(R.id.btnBottomDailynote);
        btnBottomSchoolbus = findViewById(R.id.btnBottomSchoolbus);
        btnBottomSetting = findViewById(R.id.btnBottomSetting);
        btnSelectDate = findViewById(R.id.btnSelectDate);

        recyclerView = findViewById(R.id.recyclerView);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, FoodmenuAddActivity.class);
                startActivity(intent);
            }
        });

        // 달력
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year1 = calendar.get(Calendar.YEAR);
                int month1 = calendar.get(Calendar.MONTH);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FoodmenuListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                                // 1월부터 시작하는데 시작이 0이므로 +1 해준다
                                month1 = month1 + 1;

                                // 10 이하의 날짜가 03 이런식으로 나오게 표시 방법 바꾸기
                                String month;
                                if (month1 < 10) {
                                    month = "0" + month1;
                                } else {
                                    month = "" + month1; // 문자열로 만들기
                                }

                                String day;
                                if (day1 < 10) {
                                    day = "0" + day1;
                                } else {
                                    day = "" + day1; // 문자열로 만들기
                                }
                                date1 = "" + year1 + "-" + month + "-" + day;
                                btnSelectDate.setText(date1);
                            }
                        },
                        year1, month1, day1);
                datePickerDialog.show();

            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if (lastPosition + 1 == totalCount) {

                    // 데이터 추가 호출
                    if (count == limit) {
                        addNetworkData();
                    }
                }
            }
        });



        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuListActivity.this, FoodmenuListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(FoodmenuListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(FoodmenuListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addNetworkData() {
//        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuListActivity.this);
        FoodMenuApi foodMenuApi = retrofit.create(FoodMenuApi.class);
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<FoodMenuList> call = foodMenuApi.foodMenuListAll("Bearer " + token, offset, limit, count);
        call.enqueue(new Callback<FoodMenuList>() {
            @Override
            public void onResponse(Call<FoodMenuList> call, Response<FoodMenuList> response) {
//                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    FoodMenuList foodMenuList = response.body();
                    // 페이징 위한 변수 처리
                    count = foodMenuList.getCount();
                    offset = offset + count;
                    foodMenuArrayList.addAll(foodMenuList.getItems());
                    foodMenuAdapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<FoodMenuList> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getNetworkData() {
//      progressBar.setVisibility(View.VISIBLE);
        foodMenuArrayList.clear();
        Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuListActivity.this);
        FoodMenuApi foodMenuApi = retrofit.create(FoodMenuApi.class);

        Call<FoodMenuList> call = foodMenuApi.foodMenuListAll("Bearer " + token, offset, limit, count);
        call.enqueue(new Callback<FoodMenuList>() {
            @Override
            public void onResponse(Call<FoodMenuList> call, Response<FoodMenuList> response) {
//                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    Log.i("식단표 제대로 되나요 : ", "성공");
                    FoodMenuList foodMenuList = response.body();
                    count = foodMenuList.getCount();
                    offset = offset + count;
                    foodMenuArrayList.addAll(foodMenuList.getItems());
                    foodMenuAdapter = new FoodMenuAdapter(FoodmenuListActivity.this, foodMenuArrayList);
                    recyclerView.setLayoutManager(new GridLayoutManager(FoodmenuListActivity.this,3));
                    recyclerView.setAdapter(foodMenuAdapter);

                    Log.i("식단표 제대로 되나요 : ", "count: " + count + "offset: " + offset);

                } else {

                }
            }

            @Override
            public void onFailure(Call<FoodMenuList> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        foodMenuArrayList.clear();
        offset = 0;
        getNetworkData();

    }


}