package com.mdy.android.treee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;
import com.mdy.android.treee.util.PreferenceUtil;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtContent1, txtContent2, txtContent3;
    ImageView imageViewModify;
    TextView txtDate;
    ImageView imageView;
    ImageView imageViewGallery;
    ImageView imageViewDelete;

    int position = 0;

    // 데이터베이스
    FirebaseDatabase database;
    DatabaseReference memoRef, userRef;

    // 스토리지
    StorageReference mStorageRef;

    public String tempMemoKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        setViews();

        // 데이터베이스 레퍼런스
        database = FirebaseDatabase.getInstance();
        memoRef = database.getReference("memo");

        // 스토리지 레퍼런스
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        setData();

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

            txtContent1.setText(memo.content1);
            txtContent2.setText(memo.content2);
            txtContent3.setText(memo.content3);

            tempMemoKey = memo.memoKeyName;


        }
    }

    private void setViews() {
        txtContent1 = (TextView) findViewById(R.id.txtContent1);
        txtContent2 = (TextView) findViewById(R.id.txtContent2);
        txtContent3 = (TextView) findViewById(R.id.txtContent3);
        imageViewModify = (ImageView) findViewById(R.id.imageViewModify);
        txtDate = (TextView) findViewById(R.id.txtDate);
        imageViewDelete = (ImageView) findViewById(R.id.imageViewDelete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialog(ReadActivity.this);

            }
        });


        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EnlargeActivity.class);
                intent.putExtra("LIST_POSITION", position);
                v.getContext().startActivity(intent);
            }
        });

        imageView = (ImageView) findViewById(R.id.imageViewLogo);

        imageViewModify.setOnClickListener(this);


    }

    // AlertDialog - ReadActivity에서 개별 데이터 삭제시
    public void setAlertDialog(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 제목 setting
        alertDialogBuilder.setTitle("Treee 삭제");

        // AlertDialog setting
        alertDialogBuilder
                .setMessage("Treee를 삭제하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Treee를 삭제한다.
                                memoRef.child(tempMemoKey).setValue(null);

                                String userUid = PreferenceUtil.getUid(ReadActivity.this);
                                userRef = database.getReference("user").child(userUid).child("memo");

                                userRef.child(tempMemoKey).setValue(null);
                                ReadActivity.this.finish();
                            }
                        })
                .setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Treee를 삭제하지 않는다.
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ModifyActivity.class);
        intent.putExtra("LIST_POSITION", position);
        v.getContext().startActivity(intent);
        finish();
    }
}