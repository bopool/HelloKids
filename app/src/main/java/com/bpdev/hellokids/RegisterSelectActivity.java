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

        cvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        cvPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSelectActivity.this,RegisterActivity1.class);
                startActivity(intent);

            }
        });
    }
}