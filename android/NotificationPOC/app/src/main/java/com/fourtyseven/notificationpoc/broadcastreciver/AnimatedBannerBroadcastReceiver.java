package com.fourtyseven.notificationpoc.broadcastreciver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.fourtyseven.notificationpoc.App;
import com.fourtyseven.notificationpoc.ComplexPreferences;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationPayload;
import com.fourtyseven.notificationpoc.notificationtype.AnimatedBannerNotification;
import com.fourtyseven.notificationpoc.notificationtype.CarouselNotification;

import java.io.File;


/**
 * Created by dell on 9/13/2016.
 */
public class AnimatedBannerBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getExtras().getString("delete")!=null){
            cancleNotification(context, intent.getExtras().getInt("notificationId"));
        }
        if(intent.getExtras().getString("displayText")!=null){
            String displayText = intent.getExtras().getString("displayText");
        }



        if( intent.getExtras().getString("button")!=null) {
            ComplexPreferences prefrence;
            boolean isNew;
            prefrence = App.getComplexPreference();
            int noti_id = intent.getExtras().getInt("notification_id");
            int index = intent.getExtras().getInt("index");
            String position = intent.getExtras().getString("button");
            NotificationPayload payload = prefrence.getObject(noti_id+"", NotificationPayload.class);
            CarouselNotification carouselNotification = new CarouselNotification(context, prefrence.getObject(noti_id + "", NotificationPayload.class));

            int sizeOfImages = payload.getImages().size();
            if (position.equalsIgnoreCase("next")) {
                if (index > sizeOfImages - 1) {
                    index = 0;
                }
            }
            if (position.equalsIgnoreCase("prev")) {
                if (Math.abs(index) > sizeOfImages - 1) {
                    index = 0;
                }
            }
            carouselNotification.startDisplayNotification(context, noti_id, false, index, payload);// carouselNotification.changeRemoteViewImage(1,0,noti_id);
            Log.d("/////Image_Index ", index + "");
        }
        if(intent.getExtras().getString("deeplink")!=null){
            String deeplink = intent.getExtras().getString("deeplink");
            Intent launch= new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launch);
            cancleNotification(context, intent.getExtras().getInt("notificationId"));
        }
    }

      /*
        int id = intent.getExtras().getInt("notificationId");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
        File directory = context.getDir(id + "_noti", Context.MODE_PRIVATE);
        if(directory.isDirectory()){
            for (File child : directory.listFiles()){
                child.delete();
            }
            directory.delete();
        }
        directory.delete();
        Log.d("Removed_notif_ID", "ID " + id);
        App.getComplexPreference().removeObject(id+"");
    }*/

    private void cancleNotification(Context context,int notificationId){
        File directory = context.getDir(notificationId+ "_noti", Context.MODE_PRIVATE);
        if(directory.isDirectory()){
            for (File child : directory.listFiles()){
                child.delete();
            }
            directory.delete();
        }
        directory.delete();
        // Log.d("Removed_notif_ID", "ID " + id);
        NotificationManagerCompat.from(context).cancel(notificationId);
        App.getComplexPreference().removeObject(notificationId+"");
    }
}
