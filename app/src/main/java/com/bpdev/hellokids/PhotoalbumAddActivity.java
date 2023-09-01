package com.bpdev.hellokids;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.adapter.PhotoAddAdapter;

import java.util.ArrayList;

public class PhotoalbumAddActivity extends AppCompatActivity {

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
    Button btnAdd;
    Spinner btnSelectClass;
    Button btnSeleckClass;
    Button btnSelectDate;
    Button btnSelectPhoto;
    Button btnRekog;
    EditText textInputTitle;
    EditText TextInpitContents;


    // 이미지의 uri를 담을 ArrayList 객체
    ArrayList<Uri> uriList = new ArrayList<>();


    // 리사이클러뷰
    RecyclerView recyclerView;
    // 리사이클러뷰에 적용시킬 어댑터
    PhotoAddAdapter adapter;


    // 스피너(드롭다운목록 띄우기)
    String[] facilityList = {
            "새싹반", "꽃잎반", "열매반" // 사용하고있는 선생님이 속한 어린이집의 반들이 나와야한다
    };




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
//                            Log.i("Photo ADD ClassName", "getResultCode()");
//
//
//                            if(  result.getResultCode() == 1111){
//
//                                Log.i("Photo ADD ClassName", "getResultCode()");
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
        setContentView(R.layout.activity_photoalbum_add);

        // -- -- -- 스피너 -- -- -- //
        Spinner spFacilityType = (Spinner)findViewById(R.id.btnSelectClass);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                facilityList);
        spFacilityType.setAdapter(adapter);
        spFacilityType.setSelection(0);


        // -- -- -- 화면 연결 -- -- -- //

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
        btnAdd = findViewById(R.id.btnAdd);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        textInputTitle = findViewById(R.id.textInputTitle);
        TextInpitContents = findViewById(R.id.textInputContents);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnRekog = findViewById(R.id.btnRekog);







        // -- -- 최상단 헤더 버튼 -- -- //
        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumAddActivity.this,RegisterSelectActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumAddActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // 번역 버튼







        // -- -- 하단 바로가기 메뉴 버튼 -- -- //
        // 홈 바로가기
        btnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 공지사항 바로가기
        btnBottomNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumAddActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });


        // 알림장 바로가기
        btnBottomDailyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoalbumAddActivity.this, DailynoteListActivity.class);
                startActivity(intent);
            }
        });


        // 안심등하원 바로가기
        btnBottomSchoolbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 선생님화면
                Intent intent = new Intent(PhotoalbumAddActivity.this, SchoolbusListActivity.class);
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

                Intent intent = new Intent(PhotoalbumAddActivity.this, SettingListActivity.class);
                startActivity(intent);
            }
        });
        
        
        
        
        // -- -- 메인 파트 버튼 -- -- //
        
        // 등록하기 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 스피어에서 선택한 반 이름 가져오기
                //String SelectClass = btnSelectClass.getSelectedItem().toString();
                
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
                //setResult(1111, intent);

                //launcher.launch(intent);
            }
        });
    }




    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
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

                    adapter = new PhotoAddAdapter(uriList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(PhotoalbumAddActivity.this, LinearLayoutManager.HORIZONTAL, true));
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

                        adapter = new PhotoAddAdapter(uriList, getApplicationContext());
                        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 세팅
                        recyclerView.setLayoutManager(new LinearLayoutManager(PhotoalbumAddActivity.this, LinearLayoutManager.HORIZONTAL, true));     // 리사이클러뷰 수평 스크롤 적용
                    }
                }
            }
        }

}
}