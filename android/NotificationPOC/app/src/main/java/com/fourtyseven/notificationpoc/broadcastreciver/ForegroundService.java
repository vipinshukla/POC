package com.fourtyseven.notificationpoc.broadcastreciver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by dell on 9/27/2016.
 */
public class ForegroundService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
