package com.example.wwez.Imooc_download.com.download.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.wwez.Imooc_download.MainActivity;
import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.services.DownLoadService;
import com.example.wwez.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class NotificationUtils {
    private NotificationManager mNotificationManager;

    private Map<Integer, Notification> mNotifications;

    private Context mContext;

    public NotificationUtils(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotifications = new HashMap<>();

        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(FileInfo fileInfo) {
        if(!mNotifications.containsKey(fileInfo.getId())) {

            NotificationChannel notificationChannel = new NotificationChannel(String.valueOf(fileInfo.getId()) ,
                    fileInfo.getFileName(),
                    NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setSound(null, null);
            mNotificationManager.createNotificationChannel(notificationChannel);


            Intent intent = new Intent(mContext, com.example.wwez.Imooc_download.MainActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.activity_download_notification);
            Intent intentStart = new Intent(mContext, DownLoadService.class);
            intentStart.setAction(DownLoadService.ACTION_START);
            intentStart.putExtra("fileInfo", fileInfo);
            PendingIntent piStart = PendingIntent.getService(mContext, 1, intentStart, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_start, piStart);

            Intent intentStop = new Intent(mContext, DownLoadService.class);
            intentStop.setAction(DownLoadService.ACTION_STOP);
            intentStop.putExtra("fileInfo", fileInfo);
            PendingIntent piStop = PendingIntent.getService(mContext, 2, intentStop, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_stop, piStop);

            remoteViews.setTextViewText(R.id.tv_fileName, fileInfo.getFileName());

            Notification notification = new Notification.Builder(mContext, String.valueOf(fileInfo.getId()))
                    .setTicker(fileInfo.getFileName() + "开始下载")
//                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setShowWhen(false)
                    .setAutoCancel(false)
                    .setCustomContentView(remoteViews)
                    .setChannelId(String.valueOf(fileInfo.getId()))
                    .setOngoing(true)
                    .setSound(null)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_NONE)
                    .setContentIntent(pIntent).build();


            mNotificationManager.notify(fileInfo.getId(), notification);

            mNotifications.put(fileInfo.getId(), notification);

        }
    }

    public void cancelNotification(int id) {
        mNotificationManager.cancel(id);
        mNotifications.remove(id);
    }

    public void updateNotification(int id, int progress) {
        Notification notification = mNotifications.get(id);
        if(notification != null) {
            notification.contentView.setProgressBar(R.id.pb_progress, 100, progress, false);
            mNotificationManager.notify(id, notification);
        }
    }

}
