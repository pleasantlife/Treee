package com.mdy.android.treee;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.UserProfile;
import com.mdy.android.treee.util.PreferenceUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageViewTopTree, imageViewProfileShadow;
    ImageView imageProfile;
    TextView txtName, txtEmail;
    TextView txtTreeCountName, txtTotalCount;
    ImageView btnLogout;
    ImageView btnDeleteUser;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference userRef;

    // 파이어베이스 스토리지
    private StorageReference mStorageRef;

    // 파이어베이스 인증
    private FirebaseAuth mAuth;

    // 파이어베이스 사용자 삭제
    private FirebaseUser deleteUser;

    // SharedPreference
    SharedPreferences sharedPreferences = null;


    //스위치 기능을 위한 스위치 선언
    Switch switchNoti;
    ConstraintLayout layoutAlarmDetail;
    TextView textViewSetAlarmTime;

    //알람 시간 측정 및 저장
    TimePickerDialog timePickerDialog;
    int userAlarmHour;
    int userAlarmMinute;
    Calendar calendar;
    AlarmManager mAlarmManager;
    int initCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        mStorageRef = FirebaseStorage.getInstance().getReference("profiles");

        mAuth = FirebaseAuth.getInstance();

        // 계정 삭제 관련
        deleteUser = mAuth.getInstance().getCurrentUser();

        txtName.setText(mAuth.getCurrentUser().getDisplayName());
        txtEmail.setText(mAuth.getCurrentUser().getEmail());


        txtTotalCount.setText(Data.list.size()+"");


        // 프로필
        switchNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    layoutAlarmDetail.setVisibility(View.VISIBLE);

                    layoutAlarmDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initCount = 12345;
                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                            SimpleDateFormat sdfMinute = new SimpleDateFormat("mm");
                            String hour = sdfHour.format(date);
                            String minute = sdfMinute.format(date);

                            //타임피커가 현재 시간을 표시하기 위해서 intHour, intMinute 설정.
                            int intHour = Integer.parseInt(hour);
                            int intMinute = Integer.parseInt(minute);

                            timePickerDialog = new TimePickerDialog(ProfileActivity.this, TimePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, timePickerlistener, intHour, intMinute, true);
                            timePickerDialog.show();
                        }
                    });

                } else {
                    initCount = 678910;
                    layoutAlarmDetail.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // 알람 시간 사용자 설정 (TimePicker)
    private TimePickerDialog.OnTimeSetListener timePickerlistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정 버튼을 눌렀을 때
            Toast.makeText(getApplicationContext(), "알림 시간이 " + hourOfDay + "시" + minute + "분 으로 설정되었습니다.", Toast.LENGTH_SHORT).show();

            // SharedPreference에 알람 시간(Hour), 분(Minute) 저장
            PreferenceUtil.setNotiAlarmHour(ProfileActivity.this, hourOfDay);
            userAlarmHour = hourOfDay;

            PreferenceUtil.setNotiAlarmMinute(ProfileActivity.this, minute);
            userAlarmMinute = minute;

            // 데이터베이스 Profile에 시간과 분 저장
            UserProfile userProfile = new UserProfile();

            userProfile.alarmHour = hourOfDay;
            userProfile.alarmMinute = minute;

            userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
            userRef.child("alarmHour").setValue(userProfile.alarmHour);
            userRef.child("alarmMinute").setValue(userProfile.alarmMinute);

            textViewSetAlarmTime.setText(userAlarmHour + "시 " + userAlarmMinute + "분");

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, userAlarmHour);
            calendar.set(Calendar.MINUTE, userAlarmMinute);
            calendar.set(Calendar.SECOND, 0);
            Log.d("Calendar", String.valueOf(calendar));

            if(initCount == 12345) {
                mAlarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);
                Intent alarmIntent = new Intent(ProfileActivity.this, NotiReceiver.class);

                calendar = Calendar.getInstance();
                long startTime = calendar.getTimeInMillis();
                long allDayMill = 86400000;

                PendingIntent mPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, allDayMill, mPendingIntent);

            } if(initCount == 678910){
                Intent cancelIntent = new Intent();
                PendingIntent delAlarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, cancelIntent, 0);
                mAlarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
                mAlarmManager.cancel(delAlarmIntent);
            }
        }
    };


    public void setViews(){
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtTreeCountName = (TextView) findViewById(R.id.txtTreeCountName);
        txtTotalCount = (TextView) findViewById(R.id.txtTotalCount);
        textViewSetAlarmTime = (TextView) findViewById(R.id.textViewSetAlarmTime);
        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewProfileShadow = (ImageView) findViewById(R.id.imageViewProfileShadow);

        switchNoti = (Switch) findViewById(R.id.switchNoti);

        layoutAlarmDetail = (ConstraintLayout) findViewById(R.id.layoutAlarmDetail);

        int tempHour = PreferenceUtil.getNotiAlarmHour(this);
        int tempMinute = PreferenceUtil.getNotiAlarmMinute(this);

        Log.w("tempHour", "=================="+tempHour);
        Log.w("tempMinute", "=================="+tempMinute);
        if(tempHour == 0 && tempMinute == 0){
            // 설정 switchbar가 꺼져있음
            switchNoti.setChecked(false);
            layoutAlarmDetail.setVisibility(View.GONE);
        } else {
            // 설정 switchbar가 켜져있고
            switchNoti.setChecked(true);
            // 알람 설정값을 불러옴.
            textViewSetAlarmTime.setText(tempHour + "시 " + tempMinute + "분");
        }

        imageProfile = (ImageView) findViewById(R.id.imageProfile);

        String userProfileImageUri = PreferenceUtil.getProfileImageUri(this);
        Log.w("userProfileImageUri", userProfileImageUri);
        if(!userProfileImageUri.equals("")) {
            Glide.with(this).load(userProfileImageUri).bitmapTransform(new CropCircleTransformation(this)).into(imageProfile);
        } else {
            imageProfile.setImageResource(0);
        }

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱들 중 선택
            }
        });


        btnLogout = (ImageView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogLogoutUserAccount(v.getContext());
            }
        });



        btnDeleteUser = (ImageView) findViewById(R.id.btnDeleteUser);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogDeleteUserAccount(v.getContext());
            }
        });

    }


    // AlertDialog - ProfileActivity에서 사용자 계정 삭제시
    public void setAlertDialogDeleteUserAccount(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 제목 setting
        alertDialogBuilder.setTitle("계정 삭제");

        // AlertDialog setting
        alertDialogBuilder
                .setMessage("정말 계정을 삭제하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 사용자 계정을 삭제한다.
                                deleteUserAccount();
                            }
                        })
                .setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 사용자 계정을 삭제하지 않는다.
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }


    // 사용자 계정 삭제
    private void deleteUserAccount(){
        deleteUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            // 계정 삭제에 성공하면
                            finish();
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ProfileActivity.this, "계정이 삭제되었습니다. \n\n이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    // AlertDialog - ProfileActivity에서 사용자 계정 로그아웃
    public void setAlertDialogLogoutUserAccount(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 제목 setting
        alertDialogBuilder.setTitle("로그아웃");

        // AlertDialog setting
        alertDialogBuilder
                .setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 로그아웃을 한다.
                                logoutUserAccount();
                            }
                        })
                .setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 로그아웃을 하지 않는다.
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }


    // 사용자 계정 로그아웃
    private void logoutUserAccount(){
        mAuth.signOut();
        LoginManager.getInstance().logOut();    // 페이스북 로그아웃 코드 (페이스북이 없으면 안써도 된다.)
        finish();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 100){
                Uri imageUri = data.getData();
                String filePath = getPathFromUri(this, imageUri);
//                Glide.with(this).load(filePath).into(imageProfile);
                Glide.with(this).load(filePath).bitmapTransform(new CropCircleTransformation(this)).into(imageProfile);
//                imageProfile.setImageURI(imageUri);
                Toast.makeText(this, "사진이 등록되었습니다,", Toast.LENGTH_SHORT).show();
                uploadFile(filePath);
            }
        }
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
                    Log.w("============uploadedUri===========", String.valueOf(uploadedUri));
                    PreferenceUtil.setProfileImageUri(ProfileActivity.this, uploadedUri.toString());
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

        UserProfile userProfile = new UserProfile();

        if(imageUri != null){
            userProfile.profileFileUriString = imageUri.toString();
        }

        String userUid = PreferenceUtil.getUid(this);
        userRef = database.getReference("user").child(userUid).child("profile");
        userRef.child("profileFileUriString").setValue(userProfile.profileFileUriString);
//        PreferenceUtil.setProfileImageUri(this, UserProfile.profileFileUriString);
    }

    // Uri에서 실제 경로 꺼내는 함수
    public String getPathFromUri(Context context, Uri uri){
        String realPath = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if( cursor.moveToNext() ){
            realPath = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();

        return realPath;
    }




}
