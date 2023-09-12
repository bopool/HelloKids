package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.PhotoViewAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.PhotoAlbumApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.PhotoAlbumAll;
import com.bpdev.hellokids.model.PhotoAlbumAllList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoalbumRecogViewActivity extends AppCompatActivity {

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
    TextView textDate;
    TextView textTitle;
    TextView textContents;
    RecyclerView recyclerView;

    int id; //사진첩 id

    // 사진첩 목록 조회 api에 쓸 것
    ArrayList<PhotoAlbumAll> photoAlbumArrayList = new ArrayList<>(); // api에 쓸 것
    PhotoViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum_recog_view);

        id = getIntent().getIntExtra("id", 0);

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
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Retrofit retrofit1 = NetworkClient.getRetrofitClient(PhotoalbumRecogViewActivity.this);

        PhotoAlbumApi api1 = retrofit1.create(PhotoAlbumApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Log.i("token1", token1);

        Call<PhotoAlbumAllList> call1 = api1.photoAlbumRecogView(id,"Bearer " + token1);
        call1.enqueue(new Callback<PhotoAlbumAllList>() {
            @Override
            public void onResponse(Call<PhotoAlbumAllList> call, Response<PhotoAlbumAllList> response) {
                if (response.isSuccessful()) {
                    PhotoAlbumAllList photoAlbumAllList = response.body();

                    photoAlbumArrayList.addAll(photoAlbumAllList.getItems());
                    textDate.setText( photoAlbumArrayList.get(0).getDate());
                    textTitle.setText( photoAlbumArrayList.get(0).getTitle());
                    textContents.setText( photoAlbumArrayList.get(0).getContents());
                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new PhotoViewAdapter(PhotoalbumRecogViewActivity.this, photoAlbumArrayList);
                    recyclerView.setAdapter(adapter);
                    // scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화


                } else {

                }
            }

            @Override
            public void onFailure(Call<PhotoAlbumAllList> call, Throwable t) {

            }
        });






        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼










        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(PhotoalbumRecogViewActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(PhotoalbumRecogViewActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });





        // -- -- -- 메인 파트 동작 -- -- -- //




    }
}