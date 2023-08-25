package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.config.UserApi;
import com.bpdev.hellokids.model.User;
import com.bpdev.hellokids.model.UserRes;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(email).matches() == false){
                    Snackbar.make(loginBtn,
                            "이메일 형식을 바르게 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 4 || password.length() > 12 ){
                    Snackbar.make(loginBtn,
                            "비밀번호 길이가 잘못되었습니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }


                Retrofit retrofit = NetworkClient.getRetrofitClient(LoginActivity.this);

                UserApi api = retrofit.create(UserApi.class);

                User user = new User(email, password);

                Call<UserRes> call = api.login(user);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {



                        if( response.isSuccessful() ){
                            // 200 OK 일때의 코드 작성.
                            UserRes res = response.body();

                            String accessToken = res.getAccess_token();

                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, accessToken);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();

                        } else if (response.code() == 400){
                            Snackbar.make(loginBtn,
                                    "비밀번호가 맞지 않습니다.",
                                    Snackbar.LENGTH_SHORT).show();
                            return;
                        } else {
                            Snackbar.make(loginBtn,
                                    "서버에 문제가 있습니다.",
                                    Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });


            }
        });


    }
}