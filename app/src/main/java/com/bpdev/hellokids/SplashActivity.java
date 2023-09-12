package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView children_bus_animation_c = (ImageView) findViewById(R.id.children_bus_animation_c);
        Glide.with(this).load(R.drawable.children_bus_animation_c).into(children_bus_animation_c);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent main = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(main);
                finish();
            }
        }, 3000);
    }}