package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.UserApi;
import com.bpdev.hellokids.model.Bus;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusAddActivity extends AppCompatActivity {

    EditText editBusName;
    EditText editBusNum;
    EditText editBusTime;
    EditText editDriverName;
    EditText editDriverNum;

    Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_add);

        editBusName = findViewById(R.id.editBusName);
        editBusNum = findViewById(R.id.editBusNum);
        editBusTime = findViewById(R.id.editBusTime);
        editDriverName = findViewById(R.id.editDriverName);
        editDriverNum = findViewById(R.id.editDriverNum);

        btnWrite = findViewById(R.id.btnWrite);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shuttleName = editBusName.getText().toString().trim();
                String shuttleNum = editBusNum.getText().toString().trim();
                String shuttleTime = editBusTime.getText().toString().trim();
                String shuttleDriver = editDriverName.getText().toString().trim();
                String shuttleDriverNum = editDriverNum.getText().toString().trim();


                // 1. 레트로핏 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusAddActivity.this);

                // 2. api 패키지의 인터페이스 생성.
                //    => api 폴더로 이동해서, api 인터페이스 작성해 준다!!!!
                //    => 인터페이스가 작성이 다 되었으면, API를 만들어준다.

                BusApi api = retrofit.create(BusApi.class);

                // 3. 보낼 데이터를 준비한다.
                Bus bus = new Bus(shuttleName, shuttleNum, shuttleTime, shuttleDriver, shuttleDriverNum);

                Call<BusRes> call = api.busAdd(bus);

                call.enqueue(new Callback<BusRes>() { // 받아왔을때 처리하는 코드
                    @Override
                    public void onResponse(Call<BusRes> call, Response<BusRes> response) {


                        // 서버로부터 응답을 받아서 처리하는 코드 작성.

                        // 200 OK 인지 확인
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(SchoolbusAddActivity.this, SchoolbusListActivity.class);
                            startActivity(intent);

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
            }
        });

    }
}