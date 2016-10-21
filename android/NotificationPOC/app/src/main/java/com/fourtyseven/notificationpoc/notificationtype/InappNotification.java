package com.fourtyseven.notificationpoc.notificationtype;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;


import com.fourtyseven.notificationpoc.R;

import java.util.ArrayList;

/**
 * Created by dell on 9/21/2016.
 */
public class InappNotification  {
    private String title;
    private String message;
    private int NOTIFICAION_ID;
    private int isCancle;
    private Handler handler = null;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
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

    public InappNotification(String title, String message){
        this.title = title;
        this.message = message;

        // this.isCancle = isCancle;
        //  this.context = context;
        // this.imageList = imageList;
    }


    public void customProgressAnimationNotification(Context context, int NOTIFICAION_ID ) {
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
         notificationManager.notify(NOTIFICAION_ID, createNotification(context));
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        if (useWhiteIcon) {
            notificationBuilder.setColor(0xff0000);
        }
        return useWhiteIcon ? R.drawable.pinofi_notification : R.drawable.pinofi_logo;
    }

   private Notification createNotification(Context context) {
       notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder
                .setSmallIcon(getNotificationIcon())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Sample Notification")
                .setAutoCancel(true)
                .setContentText("This is a normal notification.");
        if (true) {
            Intent push = new Intent(context, InappActivity.class);
            push.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                    push, PendingIntent.FLAG_UPDATE_CURRENT);
            context.startActivity(push);
            notificationBuilder =  new NotificationCompat.Builder(context)
                    .setContentText("Heads-Up Notification on Android L or above.")
                    .setFullScreenIntent(fullScreenPendingIntent, true);
        }
        return notificationBuilder.build();
    }

}
