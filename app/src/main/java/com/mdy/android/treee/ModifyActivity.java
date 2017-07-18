package com.mdy.android.treee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;
import com.mdy.android.treee.util.PreferenceUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextContent1, editTextContent2, editTextContent3;
    TextView txtDate, txtImage;
    ImageView imageViewGallery, imageViewCamera, imageViewSave;
    ImageView imageViewTime;
    ImageView imageView;

    int position = 0;

    // 데이터베이스
    FirebaseDatabase database;
    DatabaseReference memoRef, userRef;

    // 스토리지
    private StorageReference mStorageRef;

    // 파이어베이스 인증
    FirebaseAuth auth;

    // 프로그래서 다이얼로그
    ProgressDialog dialog;

    String updateImage = "";
    String tempMemoKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        setViews();

        // 다이얼로그
        dialog = new ProgressDialog(this);
        dialog.setTitle("Upload Data");
        dialog.setMessage("Uploading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 데이터베이스 레퍼런스
        database = FirebaseDatabase.getInstance();
        memoRef = database.getReference("memo");

        // 스토리지 레퍼런스
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        // 인증
        auth = FirebaseAuth.getInstance();

        setData();
    }

    public void uploadFile(String filePath){
        // 스마트폰에 있는 파일의 경로
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        // 파이어베이스에 있는 파일 경로
        String fileName = file.getName(); // + 시간값 or UUID 추가해서 만들어준다.
        // 데이터베이스의 키가 값과 동일한 구조 ( 키 = 값 )
        StorageReference fileRef = mStorageRef.child(fileName);

        fileRef.putFile(uri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // 파이어베이스 스토리지에 방금 업로드한 파일의 경로
                    @SuppressWarnings("VisibleForTests")
                    Uri uploadedUri = taskSnapshot.getDownloadUrl();
//                    Glide.with(getBaseContext()).load(uploadedUri).into(imageViewGallery);
                    afterUploadFile(uploadedUri);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.i("FBStorage", "Upload Fail : " + exception.getMessage());
                }
            });
    }

    public void afterUploadFile(Uri imageUri){

        String content1 = editTextContent1.getText().toString();
        String content2 = editTextContent2.getText().toString();
        String content3 = editTextContent3.getText().toString();

        // 파이어베이스 데이터베이스에 데이터 넣기
        // 1. 데이터 객체 생성
        Memo memo = new Memo(content1, content2, content3);
        memo.date = inputCurrentDate();
        memo.userUid = auth.getCurrentUser().getUid();
        memo.userEmail = auth.getCurrentUser().getEmail();

        if(imageUri != null){
            memo.fileUriString =imageUri.toString();
        } else {
            memo.fileUriString = null;
        }

        // 2. 입력할 데이터의 키 생성
        memo.memoKeyName = tempMemoKey; // 기존에 저장된 메모키를 가져온다.
        Log.i("memo.memoKeyName", "================" + memo.memoKeyName);


        memoRef.child(memo.memoKeyName).setValue(memo);


        String userUid = PreferenceUtil.getUid(this);
        userRef = database.getReference("user").child(userUid).child("memo");

        userRef.child(memo.memoKeyName).setValue(memo);

        // TODO 1 - (4)   ((FeedActivity)(FeedActivity.mContext)).onResume();

        // 3. 생성된 키를 레퍼런스로 데이터를 입력
        //   insert와 update, delete 는 동일하게 동작
        // memoRef.child(memo.memoKeyName).setValue(memo);

        // 데이터 입력 후 창 닫기
        dialog.dismiss();

        finish();
    }

    public String inputCurrentDate(){
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일, hh:mm a");
        return sdf.format(currentTime);
    }



    // startActivityForResult()가 끝나면 호출되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                // 나. 이미지 선택창에서 선택된 이미지의 경로 추출
                case 100:
                    Uri imageUri = data.getData();

                    String filePath = getPathFromUri(this, imageUri);
                    Log.i("Gallery","imageUri========================="+imageUri);
                    Log.i("filePath", "=============================== " + filePath);
                    updateImage = filePath;
                    Glide.with(getBaseContext()).load(filePath).centerCrop().into(imageViewGallery);
                    Toast.makeText(this, "사진이 등록되었습니다.", Toast.LENGTH_SHORT).show();
//                    imageView.setImageURI(imageUri);
                    break;
            }
        }

    }

    // Uri에서 실제 경로 꺼내는 함수
    public static String getPathFromUri(Context context, Uri uri){
        String realPath = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if( cursor.moveToNext() ){
            realPath = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();

        return realPath;
    }

    public void setData() {
        // 목록에서 넘어온 position 값을 이용해 상세보기 데이터를 결정
        Intent intent = getIntent();
        // null값 확인
        position = intent.getIntExtra("LIST_POSITION", -1);

        // 값이 있으면
        if (position > -1) {
            Memo memo = Data.list.get(position);

            // 이미지 세팅
            if (memo.fileUriString != null && !"".equals(memo.fileUriString)) {
                Glide.with(this).load(memo.fileUriString).centerCrop().into(imageViewGallery);
            }

            // 값 세팅
            editTextContent1.setText(memo.content1);
            editTextContent2.setText(memo.content2);
            editTextContent3.setText(memo.content3);

            tempMemoKey = memo.memoKeyName;


            txtImage.setText(memo.fileUriString);

//            txtDate.setText(memo.date);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewSave:
                dialog.show();
                // 이미지가 있으면 이미지 경로를 받아서 저장해야 되기 때문에
                // 이미지를 먼저 업로드 한다.
                if( updateImage != null && !"".equals(updateImage)){
                    uploadFile(updateImage);
                } else {
                    afterUploadFile(null);
                }
                break;
            case R.id.imageViewCamera :
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // EXTERNAL_CONTENT_URI 에 여러가지가 있는데 그 중에서 이미지들을 가져올 수 있게 해준다.
                // 가. 이미지 선택창 호출
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱이 여러개일 경우 선택하게끔 해준다.
        }
    }


    public void setViews(){
        editTextContent1 = (EditText) findViewById(R.id.editTextContent1);
        editTextContent2 = (EditText) findViewById(R.id.editTextContent2);
        editTextContent3 = (EditText) findViewById(R.id.editTextContent3);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtImage = (TextView) findViewById(R.id.txtImage);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewTime = (ImageView) findViewById(R.id.imageViewTime);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);

        imageViewCamera.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
    }
}
