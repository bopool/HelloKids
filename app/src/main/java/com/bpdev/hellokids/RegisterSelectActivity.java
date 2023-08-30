package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterSelectActivity extends AppCompatActivity {

    CardView cvTeacher;
    CardView cvPrincipal;
    CardView cvParent;

    int n; // 선생님인지 원장선생님인지 학부모인지 구별하기 위한 변수




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_select);

        cvTeacher = findViewById(R.id.cvTeacher);
        cvPrincipal = findViewById(R.id.cvPrincipal);
        cvParent = findViewById(R.id.cvParent);



        // '학부모'를 선택
        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity1.class);
                startActivity(intent);

            }
        });


        // '선생님'을 선택
        cvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });


        // '원장님'을 선택
        cvPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });


    }
}