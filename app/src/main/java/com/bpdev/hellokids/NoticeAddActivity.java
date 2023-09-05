package com.bpdev.hellokids;

import static android.content.ContentValues.TAG;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.UserRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeAddActivity extends AppCompatActivity {


    String[] classNameList = {"꽃잎반","씨앗반"}; // 일단 에러나지말라고 {} 써줌
    Spinner spinnerSelect;


    // api호출 시 들어갈 데이터
    private int classId = 5; // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서)

    private String date = "2023-09-30";  // 스피너 구현 아직 안했으니 디폴트값 넣어줌 (테스트위해서) 근데 데이터베이스에 0000-00-00으로 들어감!!!
    public int selectIcon = 0; // 아이콘 선택



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
        spinnerSelect = findViewById(R.id.spinnerSelect);
        noticeContent = findViewById(R.id.editInputContents);
        noticeTitle = findViewById(R.id.editInputTitle);
        btnPreCreate = findViewById(R.id.btnPreCreate);
        btnCreate = findViewById(R.id.btnCreate);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        photoRecyclerView = findViewById(R.id.photoRecyclerView);






        // 스피너
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>(NoticeAddActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                classNameList);

        spinnerSelect.setAdapter(classArrayAdapter);
        spinnerSelect.setSelection(0);

        spinnerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),classNameList[i]+"이 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
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

                for (int i=0; i< uriList.size(); i++){
                    String url = String.valueOf(uriList.get(i));
                    noticePhotoUrl = url.split(", ");
                    }

                // API호출
                Retrofit retrofit = NetworkClient.getRetrofitClient(NoticeAddActivity.this);
                NoticeApi api = retrofit.create(NoticeApi.class);
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN,"");
                Notice notice = new Notice(title, contents, noticePhotoUrl, isPublish);
                Call<Result> call = api.noticeAdd("Bearer "+token, notice);
//
//                return call.enqueue(new Callback<UserRes>() {
//                    @Override
//                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
//                        if (response.isSuccessful()) {
//                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sp.edit();
//                            UserRes res = response.body();
//                            editor.putString(Config.ACCESS_TOKEN, res.getAccess_token());
//                            editor.apply();
//                            Intent intent = new Intent(NoticeAddActivity.this, NoticeListActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else if (response.code() == 400) {
//
//                        } else if (response.code() == 401) {
//
//                        } else if (response.code() == 404) {
//
//                        } else if (response.code() == 500) {
//
//                        } else {
//                            // 200OK 아닌경우
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserRes> call, Throwable t) {
//                        return;
//                    }
//                };
            };
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




}