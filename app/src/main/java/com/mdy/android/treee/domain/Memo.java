package com.mdy.android.treee.domain;

/**
 * Created by MDY on 2017-07-11.
 */

public class Memo {
    public String content1;
    public String content2;
    public String content3;

    public String fileUriString;
    public String date;

    public String userUid;
    public String userEmail;

    public String memoKeyName;

    public boolean check_flag = false;

    public Memo(){

    }

    public Memo(String content1, String content2, String content3){
        this.content1 = content1;
        this.content2 = content2;
        this.content3 = content3;
    }
}
