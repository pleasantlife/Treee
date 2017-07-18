package com.mdy.android.treee.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MDY on 2017-07-12.
 */

public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE); // MODE_PRIVATE - 다른 앱이 접근하지 못하게
    }

    private static void setString(Context context, String key, String value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void setInt(Context context, String key, int value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getString(key, "해당 데이터 없음");
    }

    private static int getInt(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getInt(key, -1);
    }


    // Uid 세팅
    public static void setUid(Context context, String value){
        setString(context,"userUid",value);
    }
    public static String getUid(Context context) {
        return getString(context, "userUid");
    }

    // Profile 사진 세팅
    public static void setProfileImageUri(Context context, String value){
        setString(context, "userProfileImage", value);
    }
    public static String getProfileImageUri(Context context){
        return getString(context, "userProfileImage");
    }

    // Noti Alarm 시간(Hour) 설정
    public static void setNotiAlarmHour(Context context, int valueOfAlarmHour){
        setInt(context, "alarmHour", valueOfAlarmHour);
    }
    public static int getNotiAlarmHour(Context context){
        return getInt(context, "alarmHour");
    }

    // Noti Alarm 분(Minute) 설정
    public static void setNotiAlarmMinute(Context context, int valueOfAlarmMinute){
        setInt(context, "alarmMinute", valueOfAlarmMinute);
    }
    public static int getNotiAlarmMinute(Context context){
        return getInt(context, "alarmMinute");
    }
}