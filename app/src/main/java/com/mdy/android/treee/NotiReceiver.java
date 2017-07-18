package com.mdy.android.treee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent receiverIntent = new Intent(context, NotiService.class);
        context.startService(receiverIntent);
    }
}