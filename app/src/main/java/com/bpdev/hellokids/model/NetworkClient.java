package com.bpdev.hellokids.model;

import android.content.Context;

import com.bpdev.hellokids.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// 회원가입 화면에서 넘어옴
// 4-1.
// 매뉴얼에 나와 있는 코드

public class NetworkClient {

    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient(Context context){
        if(retrofit == null){
            // 네트워크 통신한 로그를 확인할 때 필요한 코드.
            // 로그를 찍어 준다.
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 네트워크 연결 시키는 코드
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES) //연결할때 1분까지 기다리겠다
                    .readTimeout(2, TimeUnit.MINUTES) //데이터 가져올때 1분까지 기다리겠다
                    .writeTimeout(2, TimeUnit.MINUTES) // 데이터 보낼때 1분까지 기다리겠다
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.HOST) //컨피그에 주소 넣기 ( api-네트워크클라이언트 - 4-2. 이동)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
