package com.bpdev.hellokids;

import static android.content.ContentValues.TAG;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.adapter.PhotoAddAdapter;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.NoticeApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.utils.PathUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeAddActivity extends AppCompatActivity {



    // 최상단 헤더의 버튼
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
    Button btnCreate;
    Button btnPreCreate;

    EditText noticeTitle;
    EditText noticeContent;
    Button btnSelectPhoto;
    RecyclerView photoRecyclerView;
    ArrayList<Uri> uriList = new ArrayList<>();
    PhotoAddAdapter photoAddAdapter;
    String[] noticePhotoUrl;
    int isPublish = 0;


    Spinner spinnerClass;
    List<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<NurseryClass> classArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;

    int classId;

    HashMap<String, Integer> map = new HashMap<>();

    // api호출 시 들어갈 데이터
    private String date = "2023-09-30";
    Context mContext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_add);

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
        spinnerClass = findViewById(R.id.spinnerSelect);
        noticeContent = findViewById(R.id.editInputContents);
        noticeTitle = findViewById(R.id.editInputTitle);
        btnPreCreate = findViewById(R.id.btnPreCreate);
        btnCreate = findViewById(R.id.btnCreate);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        photoRecyclerView = findViewById(R.id.photoRecyclerView);



        // 스피너
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 반 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeAddActivity.this);
        SettingApi api = retrofit.create(SettingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString(Config.ACCESS_TOKEN, "");

        Call<ClassList> call = api.classListView("Bearer " + token);
        call.enqueue(new Callback<ClassList>() {
            @Override
            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
                if (response.isSuccessful()) {
                    ClassList classList = response.body();
                    classArrayList.addAll(classList.getItems());
                    for (int i = 0; i < classArrayList.size(); i++) {
                        classNameArrayList.add(classArrayList.get(i).getClassName());
                        map.put(classArrayList.get(i).getClassName(), classArrayList.get(i).getId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ClassList> call, Throwable t) {
            }
        });

        spinnerClass.setAdapter(arrayAdapter);
        spinnerClass.setSelection(0,false);

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String spinnerValue = adapterView.getItemAtPosition(i).toString();
               spinnerClass.setSelection(i);
               Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

               classId = map.get(spinnerValue);

               Log.i("classId", classId + "");

//               // 반별 리스트 조회
//               Retrofit retrofit1 = NetworkClient.getRetrofitClient(NoticeListActivity.this);
//               ScheduleApi api1 = retrofit1.create(ScheduleApi.class);
//               SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//               String token1 = sp1.getString(Config.ACCESS_TOKEN, "");
//               Log.i("token1", token1);
//               Call<ScheduleList> call1 = api1.scheduleClassList(classId, "Bearer " + token1);
//               call1.enqueue(new Callback<ScheduleList>() {
//                   @Override
//                   public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                       if (response.isSuccessful()) {
//                           NoticeList noticeList1 = response.body();
//
//                           noticeArrayList.addAll(noticeList1.getItems());
//
//                           //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                           adapter = new NoticeAdapter(NoticeListActivity.this, noticeArrayList);
//                           recyclerView.setAdapter(adapter);
//                           noticeArrayList = new ArrayList<>(); // 중복 방지 위한 초기화
//
//
//                       } else {
//
//                       }
                   }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = noticeTitle.getText().toString().trim();
                String contents = noticeContent.getText().toString();
                isPublish = 1;

                noticePhotoUrl = new String[uriList.size()];
                for (int i=0; i< uriList.size(); i++){
//                    String realUrl = uriList.get(i).toString();
//                    noticePhotoUrl[i] = realUrl;
                    String realUrl = getPathFromUri(uriList.get(i)).toString();
                    noticePhotoUrl[i] = realUrl;
                }
                Log.i("NoticeAddActivity PhotoUrlList", ""+noticePhotoUrl);

                // API호출
                Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeAddActivity.this);
                NoticeApi api = retrofit.create(NoticeApi.class);
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");
                Notice notice = new Notice(title, contents, noticePhotoUrl, isPublish);
                Call<Result> call = api.noticeAdd("Bearer "+token, notice);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            // SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            // SharedPreferences.Editor editor = sp.edit();
                            // Result result = response.body();
                            // editor.putString(Config.ACCESS_TOKEN, result.getAccess_token());
                            // editor.apply();
                            Intent intent = new Intent(NoticeAddActivity.this, NoticeListActivity.class);
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


        // -- -- 최상단 헤더 버튼 -- -- //

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼




        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(NoticeAddActivity.this, SchoolbusListActivity.class);
                startActivity(intent);

                // 학부모화면
//                Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
//                startActivity(intent);
            }
        });


        // 설정 바로가기
        btnBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoticeAddActivity.this, SettingListActivity.class);
                startActivity(intent);
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

    }

    // 파일 선택 후 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("data : ", String.valueOf(data));

        if(requestCode == 2222){
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            }
            else{   // 이미지를 하나라도 선택한 경우
                if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
                    Log.e("single choice: ", String.valueOf(data.getData()));
                    Uri imageUri = data.getData();

                    uriList.add(imageUri);
                    photoAddAdapter = new PhotoAddAdapter(uriList, getApplicationContext());
                    photoRecyclerView.setAdapter(photoAddAdapter);
                    photoRecyclerView.setLayoutManager(new LinearLayoutManager(NoticeAddActivity.this, LinearLayoutManager.HORIZONTAL, true));
                }
                else{      // 이미지를 여러장 선택한 경우
                    ClipData clipData = data.getClipData();
                    Log.e("clipData", String.valueOf(clipData.getItemCount()));

                    if(clipData.getItemCount() > 20){   // 선택한 이미지가 21장 이상인 경우
                        Toast.makeText(getApplicationContext(), "사진은 20장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    }
                    else{   // 선택한 이미지가 1장 이상 20장 이하인 경우
                        Log.e(TAG, "multiple choice");

                        for (int i = 0; i < clipData.getItemCount(); i++){
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            Log.i(TAG, "msg : "+clipData);
                            Log.i(TAG, "msg : "+imageUri);

                            // 선택한 이미지들의 uri를 가져온다.
                            try {
                                uriList.add(imageUri);  //uri를 list에 담는다.

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }

                        photoAddAdapter = new PhotoAddAdapter(uriList, getApplicationContext());
                        photoRecyclerView.setAdapter(photoAddAdapter); // 리사이클러뷰에 어댑터 세팅
                        photoRecyclerView.setLayoutManager(new LinearLayoutManager(NoticeAddActivity.this, LinearLayoutManager.HORIZONTAL, true));
                    }
                }
            }
        }

    }
    public String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }




}