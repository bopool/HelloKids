package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView btnSignup;

    Button button10;

    // 혜리 - 테스트용 페이지 연결 : 테스트하려고 만든 액티비티 연결 버튼
    TextView textPageTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.btnSignup);
        button10 = findViewById(R.id.button10);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SchoolBusInfoActivity.class);
                startActivity(intent);
            }
        });

        // 사진첩 테스트용 버튼 - 나중에 지우기
        textPageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoalbumViewActivity.class);
                startActivity(intent);
            }
        });



    }
}