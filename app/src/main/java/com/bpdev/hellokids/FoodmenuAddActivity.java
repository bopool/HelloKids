package com.bpdev.hellokids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bpdev.hellokids.api.FoodMenuApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.Result;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodmenuAddActivity extends AppCompatActivity {

    // 스피너, 반 이름
    ArrayList<NurseryClass> classArrayList = new ArrayList<>();
    String[] classNameList = {};
    Spinner spinnerSelectClass;

    // 최상단 헤더 버튼
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
    Button btnAdd;
    Button btnSelectDate;
    Button btnSelectPhoto;
    EditText textContents;
    EditText textCategory;


    // 이미지 uri를 담을 객체
    ImageView mealPhoto;
    String mealPhotoUrl;
    String photoUrl;
    String mealDate;
    String mealContent;
    String mealType;
    Uri imgUri;


    // startActivityForResult 대신할 런처
    //    ActivityResultLauncher<Intent> launcher =
    //            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    //                    new ActivityResultCallback<ActivityResult>() {
    //                        @Override
    //                        public void onActivityResult(ActivityResult result) {
    //                            // 데이터를 받아 오는 코드는 여기에 작성 하면 된다.
    //                            //Intent intent = new Intent(Intent.ACTION_PICK);
    //                            //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
    //                            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    //                            //intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //                            //setResult(1, intent);
    //                            Log.i("FoodMenu ADD ClassName", "getResultCode()");
    //
    //
    //                            if(  result.getResultCode() == 1111){
    //
    //                                Log.i("FoodMenu ADD ClassName", "getResultCode()");
    //
    //                                //SelectClass = result.getData().getIntExtra("data",0);
    //                                //btnSelectClass.setText(""+futureAge);
    //                            }
    //
    //                        }
    //                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu_add);


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
        btnAdd = findViewById(R.id.btnCreate);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        textContents = findViewById(R.id.textInputTitle);
        textCategory = findViewById(R.id.textInputCategory);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        mealPhoto = findViewById(R.id.mealPhoto);


        // 스피너에 반 이름 가져오기
        // Retrofit
        Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuAddActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY5MzgxNjQ0MywianRpIjoiODdlMDkwMWUtMzg4ZC00ZmNiLTkxOGMtMmE2MDM2NDNiNmM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6MSwibmJmIjoxNjkzODE2NDQzfQ.wjE72DyE4C-0BqL6LFr5CS-FEg-w5rN-Vvpq3wA2ZRg");

        Call<ClassList> call = api.classListView("Bearer " + token);
        call.enqueue(new Callback<ClassList>() {
            @Override
            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
                if (response.isSuccessful()) {
                    ClassList classLists = response.body();
                    classArrayList.addAll(classLists.getItems());
                    Log.i("classArrayList", classArrayList + "");

                    classNameList = new String[classArrayList.size()];
                    for (int i = 0; i < classArrayList.size(); i++) {
                        classNameList[i] = classArrayList.get(i).getClassName();
                    }
                    Log.i(" classNameList", classNameList + "");
                } else {
                }
            }

            @Override
            public void onFailure(Call<ClassList> call, Throwable t) {
            }
        });

        // 스피너
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<>(FoodmenuAddActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                classNameList);

        spinnerSelectClass = findViewById(R.id.spinnerSelectClass);
        spinnerSelectClass.setAdapter(classArrayAdapter);
        spinnerSelectClass.setSelection(0);
        spinnerSelectClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), classArrayList.get(i) + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        // 최상단 헤더 버튼
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼


        // 하단 바로가기 메뉴 버튼
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(FoodmenuAddActivity.this, SchoolbusListActivity.class);
                startActivity(intent);

                // 학부모화면
                // Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
                // startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodmenuAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });


        // -- -- -- 메인 파트 동작 -- -- -- //

        // 등록하기 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 스피너에서 선택한 반 이름 가져오기
                // String SelectClass = btnSelectClass.getSelectedItem().toString();

            }
        });


        // 파일 선택하기 버튼
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2222);
                // setResult(1111, intent);
                // launcher.launch(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealContent = textContents.getText().toString().trim();
                mealType = textCategory.getText().toString().trim();
                mealPhotoUrl = photoUrl.toString();
                mealDate = "2023-09-06";


                // API호출
                Retrofit retrofit = NetworkClient.getRetrofitClient(FoodmenuAddActivity.this);
                FoodMenuApi api = retrofit.create(FoodMenuApi.class);
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");
                FoodMenu foodMenu = new FoodMenu(mealDate, mealPhotoUrl, mealContent, mealType);
                Call<Result> call = api.foodMenuAdd("Bearer "+token, foodMenu);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(FoodmenuAddActivity.this, FoodmenuListActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (response.code() == 400) {

                        } else if (response.code() == 401) {

                        } else if (response.code() == 404) {

                        } else if (response.code() == 500) {

                        } else {
                            // 200OK 아닌경우
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        return;
                    }
                });
            }
        });

        // 파일 선택하기 버튼
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2222);
                // launcher.launch(intent);
            }
        });


    }

    // 파일 선택 후 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("data : ", String.valueOf(data));

        if (requestCode == 2222) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {
                Uri imageUri = data.getData();
                Glide.with(FoodmenuAddActivity.this)
                        .load(imageUri)
                        .into(mealPhoto);
                Log.e("single choice: ",""+data.getData());

//                File imgFile = new File(photoUrl);
//                if (imgFile.exists()) {
//                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    mealPhoto.setImageBitmap(myBitmap);
                }

            }


    }
}


