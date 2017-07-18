package com.mdy.android.treee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;

public class EnlargeActivity extends AppCompatActivity {

    ImageView imageViewEnlarge;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);
        setViews();


    }

    public void setViews() {
        imageViewEnlarge = (ImageView) findViewById(R.id.imageViewEnlarge);
        // 목록에서 넘어온 position 값을 이용해 상세보기 데이터를 결정
        Intent intent = getIntent();
        // null값 확인
        position = intent.getIntExtra("LIST_POSITION", -1);

        // 값이 있으면
        if (position > -1) {
            Memo memo = Data.list.get(position);

            // 이미지 세팅
            if (memo.fileUriString != null && !"".equals(memo.fileUriString)) {
                Glide.with(this).load(memo.fileUriString).into(imageViewEnlarge);
            }
        }
    }
}
