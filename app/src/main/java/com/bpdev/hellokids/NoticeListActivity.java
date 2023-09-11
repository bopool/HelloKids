package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.bpdev.hellokids.adapter.NoticeAdapter;
import com.bpdev.hellokids.api.NoticeApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.FoodMenuList;
import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.NoticeRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeListActivity extends AppCompatActivity {

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


    //
    Button btnCreate;
    RecyclerView recyclerView;
    // 페이징 처리
    int offset = 0;
    int limit = 10;
    int count = 0;
    String token;


    ArrayList<Notice> noticeArrayList = new ArrayList<>();
    NoticeAdapter noticeAdapter;


    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override // ActivityResult가 있다면 동작하라.
                        public void onActivityResult(ActivityResult result) {

                            // Add Activity로 부터 데이터를 받는 경우
                            if( result.getResultCode() == 1 ){
                                Notice notice = (Notice) result.getData().getSerializableExtra("notice"); // 보낸 데이터들 불러오기
                                noticeArrayList.add(0, notice); // 목록에 추가
                                noticeAdapter.notifyDataSetChanged(); // 화면 갱신

                            } else if( result.getResultCode() == 2 ){
                                Notice notice = (Notice) result.getData().getSerializableExtra("notice"); // 보낸 데이터들 불러오기
                                int index = result.getData().getIntExtra("index", 0); // 보낸 인덱스 데이터도 불러오기
                                noticeArrayList.set(index, notice); // 이 인덱스 데이터 업데이트 해주세요!
                                noticeAdapter.notifyDataSetChanged(); // 화면 갱신

                            }
                        }
                    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        token = sp.getString(Config.ACCESS_TOKEN, "");


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

        //
        btnCreate = findViewById(R.id.btnCreate);
        recyclerView = findViewById(R.id.recyclerView);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeAddActivity.class);
                startActivity(intent);
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼








        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeListActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(NoticeListActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(NoticeListActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });

    }


    private void addNetworkData() {
//        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeListActivity.this);
        NoticeApi noticeApi = retrofit.create(NoticeApi.class);

        Call<NoticeRes> call = noticeApi.noticeList("Bearer " + token, offset, limit, count);
        call.enqueue(new Callback<NoticeRes>() {
            @Override
            public void onResponse(Call<NoticeRes> call, Response<NoticeRes> response) {
//                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    NoticeRes noticeRes = response.body();
                    // 페이징 위한 변수 처리
                    count = noticeRes.getCount();
                    offset = offset + count;
                    noticeArrayList.addAll(noticeRes.getItems());
                    noticeAdapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<NoticeRes> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getNetworkData() {
//      progressBar.setVisibility(View.VISIBLE);
        noticeArrayList.clear();

        Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeListActivity.this);
        NoticeApi noticeApi = retrofit.create(NoticeApi.class);
        Log.i("리사이클러뷰 불러오기", "token : "+token + ", offset,limit,count"+ offset + limit + count);
        Call<NoticeRes> call = noticeApi.noticeList("Bearer " + token, offset, limit, count);
        call.enqueue(new Callback<NoticeRes>() {
            @Override
            public void onResponse(Call<NoticeRes> call, Response<NoticeRes> response) {
//                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    Log.i("공지사항 제대로 되나요 : ", "성공");
                    NoticeRes noticeRes = response.body();
                    count = noticeRes.getCount();
                    offset = offset + count;
                    noticeArrayList.addAll(noticeRes.getItems());
                    noticeAdapter = new NoticeAdapter(NoticeListActivity.this, noticeArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NoticeListActivity.this));
                    recyclerView.setAdapter(noticeAdapter);


                    Log.i("공지사항 제대로 되나요 : ", "count: " + count + "offset: " + offset);

                } else {

                }

            }

            @Override
            public void onFailure(Call<NoticeRes> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        noticeArrayList.clear();
        offset = 0;
        getNetworkData();

    }

}