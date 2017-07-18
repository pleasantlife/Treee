package com.mdy.android.treee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;
import com.mdy.android.treee.util.PreferenceUtil;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    ImageView btnListIcon, btnProfile;
    Button btnMinus;
    Button btnDelete;
    ImageView imageViewTopTree, imageViewBottomTree, imageViewList;
    RecyclerView recyclerList;
    ListAdapter listAdapter;

    int clickCount = 1;

    //플로팅 버튼을 스크롤 하는 정도에 따라 나타나게 하기 위한 전역변수 준비 시작
    FloatingActionButton fabList;
    CardView cardviewList;
    NestedScrollView nestedList;
    int firstListcardHeight;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference userRef, memoRef;

    // 파이어베이스 인증
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();
        setFabList();
        setNestedList();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        memoRef = database.getReference("memo");
        String userUid = PreferenceUtil.getUid(this);
        userRef = database.getReference("user").child(userUid).child("memo");

        loadListData();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //onCreate에서 생성이 완료된 맨 위의 카드뷰(클릭하면 쓰기 액티비티로 이동하는 카드뷰)의
        //크기값을 int 값인 firstCardHeight에 받아서 넘겨줍니다.
        //(firstCardHeight도 미리 전역변수로 설정해두었습니다.)
        //또한, 카드뷰도 cardviewFeed로 전역변수로 미리 선언하였습니다.
        cardviewList = (CardView) findViewById(R.id.cardviewList);
        firstListcardHeight = cardviewList.getHeight();
    }

    //플로팅 액션 버튼 생성과 클릭 시 발생 이벤트를 정의한 함수입니다.
    public void setFabList(){
        //FloatingActionButton fabList로 전역변수 설정하였습니다.
        fabList = (FloatingActionButton) findViewById(R.id.fabList);
        fabList.setVisibility(View.GONE);
        fabList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
    }

    //맨 처음의 카드뷰 높이 만큼 스크롤 되었을 때(화면에서 첫번째 카드뷰가 사라지면) 플로팅 액션 버튼이 뜨도록 하기 위한 함수입니다.
    //NestedScrollView에 onScrollChangeListener를 달아서 y축의 스크롤 변화를 감지하였습니다.
    public void setNestedList(){
        nestedList = (NestedScrollView) findViewById(R.id.nestedList);
        nestedList.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //scrollY값이 현재 스크롤된 값입니다. 로그로 찍어보니 dp로 찍히는 듯 한데 확실하지는 않습니다.
                if(scrollY >= firstListcardHeight){
                    fabList.setVisibility(View.VISIBLE);
                } else {
                    fabList.setVisibility(View.GONE);
                }
            }
        });
    }

    public void loadListData(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Data.list.clear();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Memo 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Memo memo = item.getValue(Memo.class);
                        if(memo.userUid.equals(auth.getCurrentUser().getUid()) ) {
                            Data.list.add(memo);
                        }
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshList(Data.list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void refreshList(List<Memo> data){
        listAdapter.setListData(data);
        listAdapter.notifyDataSetChanged();
    }

    public void setViews(){
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnListIcon = (ImageView) findViewById(R.id.btnListIcon);
        btnListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnProfile = (ImageView) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewBottomTree = (ImageView) findViewById(R.id.imageViewBottomTree);
        imageViewList = (ImageView) findViewById(R.id.imageViewList);
        imageViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        listAdapter = new ListAdapter(this);
        recyclerList.setAdapter(listAdapter);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));



        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCount %2 == 0) {
                    btnMinus.setBackgroundResource(R.drawable.minus);
                    listAdapter.postListStatus(clickCount);
                    listAdapter.notifyDataSetChanged();
                } else if (clickCount %2 == 1) {
                    btnMinus.setBackgroundResource(R.drawable.listcheckboxon);
                    listAdapter.postListStatus(clickCount);
                    listAdapter.notifyDataSetChanged();
                }
                clickCount++;
                Log.w("clickCount", "====================" + clickCount);
            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setAlertDialogDeleteListData(ListActivity.this);
            }
        });
    }


    // 체크된 데이터 삭제
    private void deleteItems(){
        for(Memo memoItem : Data.list){
            if(memoItem.check_flag == true){
                memoRef.child(memoItem.memoKeyName).setValue(null);
                userRef.child(memoItem.memoKeyName).setValue(null);
            }
        }
        listAdapter.notifyDataSetChanged();
    }



    // AlertDialog - ListActivity에서 체크된 데이터 삭제시
    public void setAlertDialogDeleteListData(Context context){
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
                                deleteItems();
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
}