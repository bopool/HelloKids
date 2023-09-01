package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.BusList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    BusAdapter adapter;

    ArrayList<BusDailyRecord> busArrayList = new ArrayList<>();

    Button btnAdd;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_list);

        btnAdd = findViewById(R.id.btnAdd);
        btnInfo = findViewById(R.id.btnInfo);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this,  SchoolbusAddActivity.class);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolbusListActivity.this, SchoolbusInfoActivity.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusListActivity.this);

        BusApi api = retrofit.create(BusApi.class);

        Call<BusDailyRecordList> call = api.busList();
        call.enqueue(new Callback<BusDailyRecordList>() {
            @Override
            public void onResponse(Call<BusDailyRecordList> call, Response<BusDailyRecordList> response) {
                if(response.isSuccessful()){
                    BusDailyRecordList busList = response.body();

                    busArrayList.addAll( busList.getItems() );

                    //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                    adapter = new BusAdapter(SchoolbusListActivity.this, busArrayList);

                    recyclerView.setAdapter(adapter);
                }

                else{

                }
            }

            @Override
            public void onFailure(Call<BusDailyRecordList> call, Throwable t) {

            }
        });

    }
}