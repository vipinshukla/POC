package com.fourtyseven.notificationpoc.broadcastreciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fourtyseven.notificationpoc.notificationtype.StaticBannerNotification;


/**
 * Created by dell on 9/21/2016.
 */
public class StaticBannerBroadcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getExtras().getInt("notificationId");
     /*   AnimatedBannerNotification bannerNotification = intent.getExtras().getParcelable("anim");
        int i = bannerNotification.getIsCancle();
        bannerNotification.setIsCancle(0);*/

        Log.d("Removed_notif_ID", "ID " + id);
        StaticBannerNotification.CANCLE_ID = id;
    }
}
