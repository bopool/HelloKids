package com.bpdev.hellokids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.bpdev.hellokids.adapter.PhotoAlbumAdapter;
import com.bpdev.hellokids.adapter.PhotoRekogRecyclerAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.PhotoAlbumApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.ChildInfoList;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.PhotoAlbumAllList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoalbumRekogRecyclerActivity extends AppCompatActivity {

    // 페이징 처리
    int offset = 0;
    int limit = 10;
    int count = 0;
    String token;

    // 원아 목록 조회 API 이용 위한 변수
    ArrayList<ChildInfo> childInfoArrayList = new ArrayList<>();
    PhotoRekogRecyclerAdapter photoRekogRecyclerAdapter;
    RecyclerView recyclerView;
    int childId1;
    int classId2;


    // 메인 파트
    TextView textChildId1;
    TextView textPageTitle3;
    TextView textChildId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum_rekog_recycler);

        Intent intent = getIntent();
        childId1 = intent.getIntExtra("childId1", 0);
        classId2 = intent.getIntExtra("classId2", 4 );

        // 메인 파트 화면 연결
        textChildId = findViewById(R.id.textChildId);
        textChildId1 = findViewById(R.id.textChildId1);
        textPageTitle3 = findViewById(R.id.textPageTitle3);

        // 리사이클러 뷰 만들고 화면 연결
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //textPageTitle4.setText(childId1);


        // 자동으로 원아 목록 불러 오기
        Retrofit retrofit1 = NetworkClient.getRetrofitClient(PhotoalbumRekogRecyclerActivity.this);

        SettingApi api1 = retrofit1.create(SettingApi.class);

        SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token1 = sp1.getString(Config.ACCESS_TOKEN, "");

        Log.i("token1", token1);

        Call<ChildInfoList> call1 = api1.classChildListView(classId2, "Bearer " + token1);
        call1.enqueue(new Callback<ChildInfoList>() {
            @Override
            public void onResponse(Call<ChildInfoList> call, Response<ChildInfoList> response) {
                if (response.isSuccessful()) {

                    ChildInfoList childInfoList = response.body();

                    childInfoArrayList.addAll(childInfoList.getItems());

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    photoRekogRecyclerAdapter = new PhotoRekogRecyclerAdapter(PhotoalbumRekogRecyclerActivity.this, childInfoArrayList);
                    recyclerView.setAdapter(photoRekogRecyclerAdapter);
                    // scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화





                } else {

                }
            }

            @Override
            public void onFailure(Call<ChildInfoList> call, Throwable t) {

            }
        });













//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//                int totalCount = recyclerView.getAdapter().getItemCount();
//                if (lastPosition + 1 == totalCount) {
//
//                    // 데이터 추가 호출
//                    if (count == limit) {
//                        addNetworkData();
//                    }
//                }
//            }
//        });


    }



//    private void addNetworkData() {
////        progressBar.setVisibility(View.VISIBLE);
//        Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumRekogRecyclerActivity.this);
//        SettingApi settingApi = retrofit.create(SettingApi.class);
//
//        Call<ChildList> call = settingApi.childListView("Bearer " + token);
//        call.enqueue(new Callback<ChildList>() {
//            @Override
//            public void onResponse(Call<ChildList> call, Response<ChildList> response) {
////                progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//                    ChildList childList = response.body();
//                    // 페이징 위한 변수 처리
//                    // count = childList.getItems();
//                    // offset = offset + count;
//                    childArrayList.addAll(childList.getItems());
//                    photoRekogRecyclerAdapter.notifyDataSetChanged();
//
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ChildList> call, Throwable t) {
////                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void getNetworkData() {
////      progressBar.setVisibility(View.VISIBLE);
//        childArrayList.clear();
//
//        Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumRekogRecyclerActivity.this);
//        SettingApi settingApi = retrofit.create(SettingApi.class);
//        Log.i("리사이클러뷰 불러오기", "token : "+token);
//        Call<ChildList> childListCall = settingApi.childListView("Bearer " + token);
//        childListCall.enqueue(new Callback<ChildList>() {
//            @Override
//            public void onResponse(Call<ChildList> childListCall, Response<ChildList> childListResponse) {
////                progressBar.setVisibility(View.GONE);
//                if (childListResponse.isSuccessful()) {
//
//                    Log.i("공지사항 제대로 되나요 : ", "성공");
//                    ChildList childList = childListResponse.body();
//                    // count = childList.getItems();
//                    // offset = offset + count;
//                    childArrayList.addAll(childList.getItems());
//                    photoRekogRecyclerAdapter = new PhotoRekogRecyclerAdapter(PhotoalbumRekogRecyclerActivity.this, childArrayList);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(PhotoalbumRekogRecyclerActivity.this));
//                    recyclerView.setAdapter(photoRekogRecyclerAdapter);
//
//
//                    Log.i("공지사항 제대로 되나요 : ", "count: " + count + "offset: " + offset);
//
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ChildList> call, Throwable t) {
////                progressBar.setVisibility(View.GONE);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        childArrayList.clear();
//        offset = 0;
//        getNetworkData();
//
//    }

}