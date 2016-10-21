package com.fourtyseven.notificationpoc.notificationtype;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;


import com.fourtyseven.notificationpoc.R;
import com.fourtyseven.notificationpoc.broadcastreciver.AnimatedBannerBroadcastReceiver;

import java.util.ArrayList;

/**
 * Created by dell on 9/21/2016.
 */
public class BigImageNotificationWithbutton  {
    private String title;
    private String message;
    private int NOTIFICAION_ID;
    private int isCancle;
    private Handler handler = null;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private ArrayList<Integer> imageList;
    public static int CANCLE_ID;
    public int image_update_value=0;

    public int[] image_loader={R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fifth,
            R.drawable.sixth,
            R.drawable.seventh};

    public int getIsCancle() {
        return isCancle;
    }

    public void setIsCancle(int isCancle) {
        this.isCancle = isCancle;
    }

    public BigImageNotificationWithbutton(String title, String message, int NOTIFICAION_ID){
        this.title = title;
        this.message = message;
        this.NOTIFICAION_ID = NOTIFICAION_ID;
        // this.isCancle = isCancle;
        //  this.context = context;
        // this.imageList = imageList;
    }


    public void customProgressAnimationNotification(Context context) {
        handler=new Handler();
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);

        Intent yesReceive = new Intent(context,AnimatedBannerBroadcastReceiver.class);
        Bundle yesBundle = new Bundle();
        yesBundle.putInt("userAnswer", 1);//This is the value I want to pass
        yesReceive.putExtras(yesBundle);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(0, "View", pendingIntentYes);

        Intent noReceive = new Intent(context,AnimatedBannerBroadcastReceiver.class);
        Bundle noBundle = new Bundle();
        noBundle.putInt("userAnswer", 2);//This is the value I want to pass
        noReceive.putExtras(noBundle);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(context, 12345, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(0, "Buy", pendingIntentNo);



        Intent notificationIntent = new Intent(context,AnimatedBannerBroadcastReceiver.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, NOTIFICAION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.customnotification);
        contentView.setImageViewResource(R.id.image, R.drawable.first);
        //  contentView.setTextViewText(R.id.progress_anim_title_text, "Download in progress...");
        //contentView.setProgressBar(R.id.anim_progressBar1, 100, progress, false);
        //  contentView.setTextViewText(R.id.progress_anim_percentage, "0 / 100");

        Intent intent = new Intent(context, AnimatedBannerBroadcastReceiver.class);
        intent.putExtra("notificationId", NOTIFICAION_ID);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, NOTIFICAION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setContentText("Notification With Action Button")
                .setSubText("This is notification")
                .setSmallIcon(getNotificationIcon())
                .setDeleteIntent(pendingIntent);

        //get the bitmap to show in notification bar
        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_big_picture);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText("Summary text appears on expanding the notification");
        builder.setStyle(s);
        notificationManager.notify(NOTIFICAION_ID, builder.build());

    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        if (useWhiteIcon) {
            builder.setColor(0xff0000);
        }
        return useWhiteIcon ? R.drawable.pinofi_notification : R.drawable.pinofi_logo;
    }

}
