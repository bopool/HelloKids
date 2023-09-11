package com.bpdev.hellokids;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.PhotoAlbumApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ClassList;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.PhotoAlbumId;
import com.bpdev.hellokids.model.Result;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bpdev.hellokids.adapter.PhotoAddAdapter;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.snackbar.Snackbar;


public class PhotoalbumAddActivity extends AppCompatActivity {


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
    Button btnRekog;
    EditText textInputTitle;
    EditText textInputContents;
    ImageView imgPhotoAdd;
    Button btnPhotoAdd;
    Button btnTextAdd;


    // 리사이클러뷰
    RecyclerView photoRecyclerView;
    // 리사이클러뷰에 적용시킬 어댑터
    PhotoAddAdapter photoAddAdapter;


    // 작성일 선택
    DatePickerDialog datePickerDialog;
    String date1;


    // 앨범에서 사진 선택, 레트로핏 이미지 담기
    File photoUrl;
    File photoFile;
    int classIdTemp;

    // 글 아이디 생성 위한 세팅
    int totalAlbumNum = 0;


    // 스피너, 반 이름
    Spinner spinnerClass;
    ArrayList<String> classNameArrayList = new ArrayList<>(); // 스피너에 넣어줄 반 목록
    ArrayList<NurseryClass> classArrayList = new ArrayList<>(); // api에 쓸 것
    ArrayAdapter<String> arrayAdapter;
    HashMap<String, Integer> map = new HashMap<>(); // 스피너에 들어가있는 반이름을 클릭하면 그 반이름을 가진 반 데이터의 id를 반환할 때 사용
    int classId1;


    // 스피너(드롭다운목록 띄우기)
    //    String[] facilityList = {
    //            "새싹반", "꽃잎반", "열매반" // 사용하고있는 선생님이 속한 어린이집의 반들이 나와야한다
    //    };
    //



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum_add);


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
        textInputContents = findViewById(R.id.textInputContents);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnRekog = findViewById(R.id.btnRekog);
        imgPhotoAdd = findViewById(R.id.imgPhotoAdd);
        btnPhotoAdd = findViewById(R.id.btnPhotoAdd);
        btnTextAdd = findViewById(R.id.btnTextAdd);

        // 리사이클러 뷰
        photoRecyclerView = findViewById(R.id.photoRecyclerView);

        // 스피너 연결
        spinnerClass = findViewById(R.id.spinnerClass);





        // -- -- -- 메인 파트 동작 -- -- -- //

        // 스피너
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classNameArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 반 이름 가져오기
        Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
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

        spinnerClass.setSelection(0);



        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();
                spinnerClass.setSelection(i);
                Toast.makeText(getApplicationContext(), spinnerValue+"이 선택되었습니다.", Toast.LENGTH_SHORT).show();

                classId1 = map.get(spinnerValue);

                //String.valueOf(classId1);


                Log.i("classId", classId1 + "");


//                // 반별 일정표 리스트 조회  ------>>> 여기서부터 반 목록 가져오는걸로 바꾸면 된다.
//                Retrofit retrofit1 = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
//
//                ScheduleApi api1 = retrofit1.create(ScheduleApi.class);
//
//                SharedPreferences sp1 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//                String token1 = sp1.getString(Config.ACCESS_TOKEN, "");
//
//                Log.i("token1", token1);
//
//                Call<ScheduleList> call1 = api1.scheduleClassList(classId, "Bearer " + token1);
//                call1.enqueue(new Callback<ScheduleList>() {
//                    @Override
//                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                        if (response.isSuccessful()) {
//                            ScheduleList scheduleList1 = response.body();
//
//                            scheduleArrayList.addAll(scheduleList1.getItems());
//
//                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                            adapter = new ScheduleAdapter(PhotoalbumAddActivity.this, scheduleArrayList);
//                            recyclerView.setAdapter(adapter);
//                            scheduleArrayList = new ArrayList<>(); // 중복 방지 위한 초기화
//
//
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//                    }
//                });

            }



            public void onNothingSelected(AdapterView<?> adapterView) { // 아무것도 선택하지 않았을 때 실행되는건데 자동으로 선택이 되기때문에 이 코드가 실행되지 않는다

//                Retrofit retrofit2 = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
//
//                ScheduleApi api2 = retrofit2.create(ScheduleApi.class);
//
//                SharedPreferences sp2 = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
//                String token2 = sp2.getString(Config.ACCESS_TOKEN, "");
//
//                Log.i("token2", token2);
//
//                Call<ScheduleList> call2 = api2.scheduleList("Bearer " + token2);
//                call2.enqueue(new Callback<ScheduleList>() {
//                    @Override
//                    public void onResponse(Call<ScheduleList> call, Response<ScheduleList> response) {
//                        if (response.isSuccessful()) {
//                            ScheduleList scheduleList2 = response.body();
//
//                            Log.i("aaa2", scheduleList2.getResult());
//
//                            scheduleArrayList.addAll(scheduleList2.getItems());
//
//                            //Adapter를 이용해서 postInfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
//                            adapter = new ScheduleAdapter(PhotoalbumAddActivity.this, scheduleArrayList);
//
//                            recyclerView.setAdapter(adapter);
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ScheduleList> call, Throwable t) {
//
//                    }
//                });

            }
        });








        // 등록하기 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PhotoalbumAddActivity.this, PhotoalbumListActivity.class);
                startActivity(intent);
            }
        });






        // 작성일 선택 버튼
        // - 달력 띄워서 선택 날짜 가져오기
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 달력 띄우기
                Calendar calendar = Calendar.getInstance();
                int year1 = calendar.get(Calendar.YEAR);
                int month1 = calendar.get(Calendar.MONTH);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(PhotoalbumAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                                // 1월부터 시작하는데 시작이 0이므로 +1 해준다
                                month1 = month1 +1;

                                // 10 이하의 날짜가 03 이런식으로 나오게 표시 방법 바꾸기
                                String month;
                                if ( month1 < 10 ){
                                    month = "0" + month1;
                                }else{
                                    month = "" + month1; // 문자열로 만들기
                                }

                                String day;
                                if ( day1 < 10 ){
                                    day = "0" + day1;
                                }else{
                                    day = "" + day1; // 문자열로 만들기
                                }

                                date1 = ""+ year1 + "-" + month + "-" + day;

                                btnSelectDate.setText(date1);
                            }
                        },
                        year1, month1, day1);
                datePickerDialog.show();

            }
        });
        
        


        
        
        // 제목과 내용 입력 완료 버튼 -> 클릭시 글 아이디 생성
        btnTextAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                // 레트로핏으로 포토앨범 테이블에 한 줄 추가
                Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
                PhotoAlbumApi photoAlbumApi = retrofit.create(PhotoAlbumApi.class);

                // 인증 토큰 가져오기
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                // 클래스 만들기
                PhotoAlbumId photoAlbumId = new PhotoAlbumId( totalAlbumNum );

                Call<Result> call = photoAlbumApi.photoAlbumAddId("Bearer " + token, photoAlbumId);

                // 다이얼로그 띄우기
                showProgress();

                //
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {

                        // 다이얼로그 없애기
                        dismissProgress();

                        //
                        if(response.isSuccessful()){

                        }else {
                            Snackbar.make(btnTextAdd,
                                    "서버에 문제가 있습니다.",
                                    Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        // 다이얼로그 없애기
                        dismissProgress();

                        Snackbar.make(btnTextAdd,
                                "서버와 통신이 되지 않습니다.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });


            }
        });







        // 파일 선택하기 버튼
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 방법 1.)
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 2222);

                //setResult(1111, intent);

                // 방법 2.)
                // launcher.launch(intent);


                // 방법 3.) 함수 호출
                showDialog();
            }
        });





        // 추가 버튼
        btnPhotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 스피너에서 선택한 반 이름 가져오기
                // String SelectClass = btnSelectClass.getSelectedItem().toString();
                // 임시 클래스 아이디
                classIdTemp = 4;

                // 날짜 가져오기 --> date 변수에 저장됨.

                // 제목, 내용 가져오기
                String title1 = textInputTitle.getText().toString().trim();
                Log.i("title : ", ""+title1);
                String contents1 = textInputContents.getText().toString().trim();
                Log.i("contents : ", ""+contents1);


                // 네트워크 들어가기 전에
                // 다이얼로그 삽입
                showProgress();

                //
                Retrofit retrofit = NetworkClient.getRetrofitClient(PhotoalbumAddActivity.this);
                // 포스트 생성 API 만들고 돌아오기 --> PostApi 이동
                PhotoAlbumApi photoAlbumApi = retrofit.create(PhotoAlbumApi.class);

                // -- 인증토큰 (헤더에 세팅할 토큰 가져오기)
                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                // -- 보낼 파일
                // 사진 파일 가져오기
                // 파라미터 설명 : content 보낼꺼야, (파일을 보낼꺼냐 텍스트를 보낼꺼냐) 파일 중 jpg로 보낼꺼다
                RequestBody fileBody = RequestBody.create(photoFile, MediaType.parse("image/jpg"));

                // MultipartBody.Part.createFormData("photo"--> API명세서 바디에있는 폼데이터내용 중 키 값을 넣은것,)
                // 용량이 큰 파일은 잘게 쪼개서 보내는 작업
                MultipartBody.Part photoUrl = MultipartBody.Part.createFormData("photoUrl", photoFile.getName(), fileBody);

                // -- 보낼 텍스트
                // 파라미터 설명 : content 보낼꺼야, (파일을 보낼꺼냐 텍스트를 보낼꺼냐) 텍스트보낼꺼야
                RequestBody classId = RequestBody.create(String.valueOf(classId1), MediaType.parse("text/plain"));
                RequestBody date = RequestBody.create(date1, MediaType.parse("text/plain"));
                RequestBody title = RequestBody.create(title1, MediaType.parse("text/plain"));
                RequestBody contents = RequestBody.create(contents1, MediaType.parse("text/plain"));

                
                //
                Call<Result> call = photoAlbumApi.photoAlbumAdd("Bearer "+token, classId, date, title, contents, photoUrl);


                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {

                        // 26.
                        // 다이얼로그 없애기
                        dismissProgress();

                        if(response.isSuccessful()){

                            // 포스팅성공시 애드액티비티 종료, 메인액티비티 숨어있는거 불러오기
                            Log.i("Hello Success" , "success");

                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                        Log.i("Hello Fail" , ""+t);

                        // 26.
                        // 다이얼로그 없애기
                        dismissProgress();

                    }
                });

            }
        });


        // 원아별 폴더 생성 및 자동분류 버튼
        btnRekog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PhotoalbumAddActivity.this, PhotoalbumRekogActivity.class);
                startActivity(intent);
            }
        });






        // -- -- -- -- -- 공통 버튼 -- -- -- -- -- //
        // 최상단 헤더 버튼
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




        // 하단 바로가기 메뉴 버튼
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
                // Intent intent = new Intent(MainActivity.this, SchoolbusParentListActivity.class);
                // startActivity(intent);
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


        // -- -- -- -- -- 공통 버튼  끝 -- -- -- -- -- //


    } // - 온크리에이트 닫음





    // 방법 1.) 선택시 실행해야 하는 코드
    // 파일 선택 후 앨범에서 액티비티로 돌아온 후 실행되는 메서드
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i("data : ", String.valueOf(data));
//
//        if(requestCode == 2222){
//            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
//                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//            }
//            else{   // 이미지를 하나라도 선택한 경우
//                if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
//                    Log.e("single choice: ", String.valueOf(data.getData()));
//                    Uri imageUri = data.getData();
//                    uriList.add(imageUri);
//
//                    photoAddAdapter = new PhotoAddAdapter(uriList, getApplicationContext());
//                    photoRecyclerView.setAdapter(photoAddAdapter);
//                    photoRecyclerView.setLayoutManager(new LinearLayoutManager(PhotoalbumAddActivity.this, LinearLayoutManager.HORIZONTAL, true));
//                }
//                else{      // 이미지를 여러장 선택한 경우
//                    ClipData clipData = data.getClipData();
//                    Log.e("clipData", String.valueOf(clipData.getItemCount()));
//
//                    if(clipData.getItemCount() > 20){   // 선택한 이미지가 21장 이상인 경우
//                        Toast.makeText(getApplicationContext(), "사진은 20장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
//                    }
//                    else{   // 선택한 이미지가 1장 이상 20장 이하인 경우
//                        Log.e(TAG, "multiple choice");
//
//                        for (int i = 0; i < clipData.getItemCount(); i++){
//                            Uri imageUri = clipData.getItemAt(i).getUri();
//                            Log.i(TAG, "msg : "+clipData);
//                            Log.i(TAG, "msg : "+imageUri);
//
//                            // 선택한 이미지들의 uri를 가져온다.
//                            try {
//                                uriList.add(imageUri);  //uri를 list에 담는다.
//                                Log.i("Hello imageUri : ", ""+imageUri);
//                                Log.i("Hello uriList  : ", ""+uriList);
//
//                            } catch (Exception e) {
//                                Log.e(TAG, "File select error", e);
//                            }
//                        }
//
//                        photoAddAdapter = new PhotoAddAdapter(uriList, getApplicationContext());
//                        photoRecyclerView.setAdapter(photoAddAdapter); // 리사이클러뷰에 어댑터 세팅
//                        photoRecyclerView.setLayoutManager(new LinearLayoutManager(PhotoalbumAddActivity.this, LinearLayoutManager.HORIZONTAL, true));     // 리사이클러뷰 수평 스크롤 적용
//                    }
//                }
//            }
//        }
//    }


    // 사진의 경로를 파악하기 위해 알아봤던 코드 --> 연결 되는 코드 없음.
//    /* 이미지 파일을 복사한 후, 그 파일의 절대 경로 반환하는 메소드 */
//    public String createCopyAndReturnRealPath(Uri uri, String fileName) {
//        final ContentResolver contentResolver = getContentResolver();
//        if (contentResolver == null)
//            return null;
//        // 내부 저장소 안에 위치하도록 파일 생성
//        String filePath = getApplicationInfo().dataDir + File.separator + System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".")+1);
//        File file = new File(filePath);
//        // 서버에 이미지 업로드 후 삭제하기 위해 경로를 저장해둠
//        tempFileList.add(filePath);
//
//        try {
//            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 가져온다.
//            InputStream inputStream = contentResolver.openInputStream(uri);
//            if (inputStream == null)
//                return null;
//            // 가져온 이미지 데이터를 아까 생성한 파일에 저장한다.
//            OutputStream outputStream = new FileOutputStream(file);
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = inputStream.read(buf)) > 0)
//                outputStream.write(buf, 0, len);
//            outputStream.close();
//            inputStream.close();
//        } catch (IOException ignore) {
//            return null;
//        }
//        return file.getAbsolutePath(); // 생성한 파일의 절대경로 반환
//    }



    // 사진의 경로를 파악하기 위해 알아봤던 코드 --> 연결 되는 코드 없음.
//    /* 전체 갤러리 이미지 Uri, 파일명 등을 반환하는 메소드 (권한 있어야 함)*/
//    //ArrayList<PhotoAlbumUrlList> photoAlbumUrlList
//    public void getGalleryImage(ArrayList<PhotoAlbumUrlList> photoAlbumUrlList) {
//        Cursor cursor;
//        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
//        String sortOrder = MediaStore.Images.Media._ID + " DESC"; // 최신순으로 가져오기
//        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
//        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//        int displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//        while (cursor.moveToNext()) {
//            long id = cursor.getLong(idColumn);
//            String displayName = cursor.getString(displayNameColumn);
//            Uri contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
//
//            photoAlbumUrlList.add(new PhotoAlbumUrlList( contentUri.toString(), displayName));
//        }
//    }






    // 방법 3.) 선택시 실행되는 코드 ----------------------------------------------------- 시작
    // 알러트 다이얼로그
    // alert_title 부분은 리소스폴더 - 벨류 - 스트링스 xml에서 복사해온다
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoalbumAddActivity.this);
        builder.setTitle(R.string.alert_title);
        builder.setItems(R.array.alert_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0){
                    // 첫번째 항목을 눌렀을 때
                    // 카메라로 사진찍기 실행

                    // 6.
                    camera();

                }else if(i==1){
                    // 두번째 항목을 눌렀을 때
                    // 앨범 선택 했을 때

                    // 7.
                    album();

                }
            }
        });
        builder.show();

    }





    // 카메라 함수
    private void camera(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                PhotoalbumAddActivity.this, android.Manifest.permission.CAMERA);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PhotoalbumAddActivity.this,
                    new String[]{android.Manifest.permission.CAMERA} ,
                    1000);
            Toast.makeText(PhotoalbumAddActivity.this, "카메라 권한 필요합니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else { // 권한 허가했다면
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(PhotoalbumAddActivity.this.getPackageManager())  != null  ){

                // 사진의 파일명을 만들기
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = getPhotoFile(fileName);

                Uri fileProvider = FileProvider.getUriForFile(PhotoalbumAddActivity.this,
                        // todo : 메니페스트파일에서 안드로이드:어쏘리티즈(authorities) = '' 의 내용과 아래 "" 부분이 같아야 함. + 추가함(김하연)
                        "com.bpdev.hellokids.fileprovider", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                startActivityForResult(i, 100);

            } else{ //카메라가 없다면
                Toast.makeText(PhotoalbumAddActivity.this, "카메라 앱이 없습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }





    // 사진 가져오는 함수
    private File getPhotoFile(String fileName) {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try{
            return File.createTempFile(fileName, ".jpg", storageDirectory);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }







    // 앨범 선택했을때 실행
    // 체크 퍼미션 함수 가져오기
    private void album(){
        if(checkPermission()){
            displayFileChoose();
        }else{
            requestPermission();
        }
    }






    // 체크퍼미션 함수
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(PhotoalbumAddActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_DENIED){
            return false;
        }else{
            return true;
        }
    }





    // 리퀘스트 퍼미션
    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(PhotoalbumAddActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Log.i("DEBUGGING5", "true");
            Toast.makeText(PhotoalbumAddActivity.this, "권한 수락이 필요합니다.",
                    Toast.LENGTH_SHORT).show();
        }else{
            Log.i("DEBUGGING6", "false");
            ActivityCompat.requestPermissions(PhotoalbumAddActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }
    }





    private void displayFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 300);
    }







    // 권한 설정 알림창(권한 주겠습니까? 뜨는 창)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PhotoalbumAddActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhotoalbumAddActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 500: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PhotoalbumAddActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhotoalbumAddActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }

            }

        }
    }




    // 카메라앱을 실행했으면 카메라를, 또는 앨범을 선택했으면 앨범을 화면에 보여주기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK){

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(photoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            photo = rotateBitmap(photo, orientation);

            // 압축시킨다. 해상도 낮춰서
            OutputStream os;
            try {
                os = new FileOutputStream(photoFile);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            imgPhotoAdd.setImageBitmap(photo);
            imgPhotoAdd.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 네트워크로 데이터 보낸다. ( -> 필요하면 이 자리에 쓴다)



            // 앨범 선택하면 다음 else 코드 실행
        }else if(requestCode == 300 && resultCode == RESULT_OK && data != null &&
                data.getData() != null){

            Uri albumUri = data.getData( );
            String fileName = getFileName( albumUri );

            try {

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( albumUri, "r" );
                if ( parcelFileDescriptor == null ) return;

                FileInputStream inputStream = new FileInputStream( parcelFileDescriptor.getFileDescriptor( ) );

                photoFile = new File( this.getCacheDir( ), fileName );

                FileOutputStream outputStream = new FileOutputStream( photoFile );
                IOUtils.copyStream( inputStream, outputStream );



//                //임시파일 생성
//                File file = createImgCacheFile( );
//                String cacheFilePath = file.getAbsolutePath( );


                // 압축시킨다. 해상도 낮춰서
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                OutputStream os;
                try {
                    os = new FileOutputStream(photoFile);
                    photo.compress(Bitmap.CompressFormat.JPEG, 60, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                imgPhotoAdd.setImageBitmap(photo);
                imgPhotoAdd.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//                imageView.setImageBitmap( getBitmapAlbum( imageView, albumUri ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }

            // 네트워크로 보낸다.(-->네트워크로 보내는게 필요하다면 여기에 적으면 된다)



        }
        super.onActivityResult(requestCode, resultCode, data);
    }




     // 로테이트비트맵
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }






    //앨범에서 선택한 사진이름 가져오기
    public String getFileName( Uri uri ) {
        Cursor cursor = getContentResolver( ).query( uri, null, null, null, null );
        try {
            if ( cursor == null ) return null;
            cursor.moveToFirst( );
            @SuppressLint("Range") String fileName = cursor.getString( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) );
            cursor.close( );
            return fileName;

        } catch ( Exception e ) {
            e.printStackTrace( );
            cursor.close( );
            return null;
        }
    }




    // 다이얼로그
    // 멤버변수
    Dialog dialog;

     //다이얼로그 띄우기
     //- 데이터베이스에 집어 넣는 동안 다른 행동 못 하도록 다이얼로그 띄워서 터치 막기
    void showProgress(){
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


     //다이얼로그 없애기
    void dismissProgress(){
        dialog.dismiss();
    }

    // 방법 3.) 선택시 실행되는 코드 ----------------------------------------------------- 끝.

}