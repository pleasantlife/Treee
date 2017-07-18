package com.mdy.android.treee;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotiService extends Service {

    private NotificationManager notificationManager;
    private PendingIntent servicePendingIntent;

    public NotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Context context = this.getApplicationContext();


        Intent mIntent = new Intent(this, LoginActivity.class);
        servicePendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("하루에 감사한 일 세 가지. Treee");
        builder.setContentText("오늘 하루도 감사한 일이 참 많습니다.");
//        builder.setSmallIcon(R.mipmap.ic_launcher_app);
        builder.setSmallIcon(R.mipmap.ic_launcher_app_round);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_app_round);
        builder.setLargeIcon(largeIcon);
//        builder.setLargeIcon(R.);
        builder.setContentIntent(servicePendingIntent);

        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(600, builder.build());


        return super.onStartCommand(intent, flags, startId);
    }
}