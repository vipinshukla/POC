package com.fourtyseven.notificationpoc.notificationtype;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import com.fourtyseven.notificationpoc.R;
import com.fourtyseven.notificationpoc.broadcastreciver.AnimatedBannerBroadcastReceiver;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationPayload;

import java.util.ArrayList;

/**
 * Created by dell on 9/21/2016.
 */
public class StaticBannerNotification {
    private final ArrayList<Bitmap> bitmapArrayList;
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

    public int[] image_loader={R.drawable.staticbanner,
            };


    public StaticBannerNotification(String title, String message, int NOTIFICAION_ID,ArrayList<Bitmap> bitmapArrayList,NotificationPayload payload){
        this.title = title;
        this.message = message;
        this.NOTIFICAION_ID = NOTIFICAION_ID;
        this.bitmapArrayList = bitmapArrayList;
        // this.isCancle = isCancle;
        //  this.context = context;
        // this.imageList = imageList;
    }


    public void customProgressAnimationNotification(Context context) {
        handler=new Handler();
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);

        Intent notificationIntent = new Intent();
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

        builder.setContent(contentView)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSmallIcon(getNotificationIcon())
                .setDeleteIntent(pendingIntent);


        // noti_anim.contentView = contentView;
        //noti_anim.contentIntent = contentIntent;

        // Adds the notification as Ongoing event
        // noti_anim.flags = Notification.FLAG_AUTO_CANCEL;

        // Can not clear the notification using the clear button.
        //noti_anim.flags = Notification.FLAG_NO_CLEAR;

        //noti_anim.icon = R.drawable.animation;


        //noti_anim.iconLevel = 0;

        Runnable anim_runnable=new Runnable(){


            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                for(int i=0;i<=1;i++){
                    if(NOTIFICAION_ID==CANCLE_ID){
                        i=1;
                    }
                    final int value=i;
                    Log.v("Test", "i Value=========>>>" + i);
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    handler.post(new Runnable(){


                        @Override
                        public void run()
                        {
                            // TODO Auto-generated method stub

                            if (image_update_value < 1)
                            {
                                // noti_anim.iconLevel++;
                              //  image_update_value++;
                            }
                            else
                            {
                                // noti_anim.iconLevel = 0;
                                image_update_value=0;
                            }

                            if(value==1)
                            {
                                Log.v("Test", "=========== Notification Cancelling ==============");
                                //  noti_anim.contentView.setTextViewText(R.id.progress_anim_title_text, "Downloading Completed");
                                //   notificationManager.notify(NOTIFICAION_ID, builder.build());
                              //  notificationManager.cancel(NOTIFICAION_ID);
                                CANCLE_ID=0;
                                //noti_anim.iconLevel = 0;
                                image_update_value=0;
                                // Cancel the Notification
                                //noti_anim_man.cancel(NOTIFICAION_ID);
                            }
                            else {
                                if(NOTIFICAION_ID==CANCLE_ID){
                                    return;
                                }
                                builder.build().contentView.setImageViewBitmap(R.id.image, bitmapArrayList.get(image_update_value));
                                notificationManager.notify(NOTIFICAION_ID, builder.build());
                            }

                        }
                    });
                }
            }
        };
        new Thread(anim_runnable).start();


    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        if (useWhiteIcon) {
            builder.setColor(0xff0000);
        }
        return useWhiteIcon ? R.drawable.pinofi_notification : R.drawable.pinofi_logo;
    }

}
