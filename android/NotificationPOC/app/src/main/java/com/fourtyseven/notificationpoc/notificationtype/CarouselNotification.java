package com.fourtyseven.notificationpoc.notificationtype;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.fourtyseven.notificationpoc.App;
import com.fourtyseven.notificationpoc.Apputils;
import com.fourtyseven.notificationpoc.ComplexPreferences;
import com.fourtyseven.notificationpoc.R;
import com.fourtyseven.notificationpoc.broadcastreciver.AnimatedBannerBroadcastReceiver;
import com.fourtyseven.notificationpoc.notificationpayload.Action;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationImageGroup;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationPayload;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dell on 9/21/2016.
 */
public class CarouselNotification {
    private Context context;
    NotificationPayload payload;
    private boolean b = true;

    public CarouselNotification(Context context, NotificationPayload payLoad) {
        this.context = context;
        this.payload = payLoad;
    }

    public void customProgressAnimationNotification() {

        ComplexPreferences complexPrefenreces = App.getComplexPreference();
        if (complexPrefenreces != null) {
            complexPrefenreces.putObject(payload.getNotificationId()+"", payload);
            complexPrefenreces.commit();
            startDisplayNotification(context, payload.getNotificationId(), true, 0, payload);
        } else {
            android.util.Log.e("Notification", "Preference is null");
        }
    }

    private void setStyle(NotificationCompat.Builder builder, String imageUrl, String message) {
        if (imageUrl.equals("")) {
            return;
        }
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromString(context, payload.getNotificationId())).setSummaryText(message));
    }

    private void setNotificationStyle(NotificationCompat.Builder builder, String string, String string2) {
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(string2).setBigContentTitle(string));
    }

    public void startDisplayNotification(Context context, int noti_id, boolean isNewNotification, int index, NotificationPayload payload) {
        if(payload==null){
            return;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        setTitleAndMessage(builder, payload.getTitle(), payload.getMessage());
        setDeleteIntent(noti_id, builder);


        if (payload.getDisplayType().equalsIgnoreCase("animation")) {
            setDataInRemoteView(builder, payload.getlayoutType(), payload);
            setSliderOrCarouse(builder, payload, index);
        }

        else {
            if (payload.getDisplayType().equalsIgnoreCase("basic")) {
                setDataInRemoteView(builder, payload);
                setNotificationStyle(builder, payload.getTitle(), payload.getMessage());
                setStyle(builder, payload.getImageUrl(), payload.getMessage());
                if(payload.getActions()!=null){
                    setAction(payload,builder);
                }
                notificationManager.notify(noti_id, builder.build());
            }
        }
    }

    public Intent setDeepLink(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    private int getNotificationIcon(NotificationCompat.Builder builder) {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        if (useWhiteIcon) {
            builder.setColor(0xff0000);
        }
        return useWhiteIcon ? R.drawable.pinofi_notification : R.drawable.pinofi_logo;
    }

    public Bitmap getBitmapFromString(Context context, NotificationPayload payload, int index) {
        Bitmap bitmap;
        bitmap =  App.getBitmapFromMemCache(payload.getNotificationId()+"_"+Math.abs(index));
        if(bitmap==null){
            File directory = context.getDir(payload.getNotificationId() + "_noti", Context.MODE_PRIVATE);
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            File[] files = directory.listFiles();
            File file = files[getImageArrayPosition(index,files)];
            String name = file.getName();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            if(bitmap!=null) {
                App.addBitmapToMemoryCache(name, bitmap);
            }
        }
        return bitmap;
    }

    private int getImageArrayPosition(int index, File[] files) {

        int position = 0;
        if(index<files.length){
            position = Math.abs(index);
        }
        if(index==files.length){
            position=0;
        }
        return position;
    }

    public Bitmap getBitmapFromString(Context context, int id) {
        File directory = context.getDir(id + "_noti", Context.MODE_PRIVATE);
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
        File[] files = directory.listFiles();
        File file = files[0];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    private void setDataInRemoteView(NotificationCompat.Builder builder, NotificationPayload payload) {
        try {
            RemoteViews remoteViews = null;
            try {
                remoteViews = new RemoteViews(this.context.getPackageName(), getResorceID("custom_collapsed_notification", "layout"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            remoteViews.setViewVisibility(getViewId("bg_and_text_color"), View.VISIBLE);
            remoteViews.setViewVisibility(getViewId("q10_style"), View.GONE);
            int n2 = this.getViewId("icon");
            if (!payload.getImageUrl().equals("")) {
                remoteViews.setImageViewResource(n2, R.drawable.pinofi_logo);
                 /* Bitmap bitmap = BitmapCreator.e(this.context, string5);
                if (this.resizeImage) {
                bitmap = BitmapCreator.a(bitmap, this.context);*/
                // remoteViews.setImageViewBitmap(n2, bitmap);
            } else {
                remoteViews.setImageViewResource(n2, R.drawable.pinofi_logo);
            }

            if (payload.getDeepLink() != null) {
                setDeepLink(payload.getDeepLink());

            }
            int titleID = this.getViewId("title");
            remoteViews.setTextViewText(titleID, payload.getTitle());
            remoteViews.setTextColor(titleID, Color.parseColor("White"));
            int messageID = this.getViewId("message");
            remoteViews.setTextViewText(messageID, payload.getMessage());
            remoteViews.setTextColor(messageID, Color.parseColor("White"));
            remoteViews.setInt(this.getViewId("icon"), "setBackgroundColor", Color.parseColor("BLUE"));
            remoteViews.setInt(this.getViewId("title_and_message"), "setBackgroundColor", Color.parseColor("BLUE"));
            builder.setContent(remoteViews);
        } catch (Exception var6_7) {
            // ab.a(s.a, a.e, "Exception while setting custom color for title and text: %s", var6_7);
        }
    }

    private void setDataInRemoteView(NotificationCompat.Builder builder, String string, NotificationPayload payload) {
        try {
            RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), getResorceID("custom_collapsed_notification", "layout"));
            remoteViews.setViewVisibility(getViewId("bg_and_text_color"), View.GONE);
            remoteViews.setViewVisibility(getViewId("q10_style"), View.VISIBLE);
            remoteViews.setTextViewText(getViewId("q10_title"), payload.getTitle());
            remoteViews.setTextViewText(getViewId("q10_message"), payload.getMessage());
            if ("slider".equalsIgnoreCase(string)) {
                remoteViews.setViewVisibility(getViewId("q10_multiple_images"), View.GONE);
                remoteViews.setViewVisibility(getViewId("q10_single_image"), View.VISIBLE);
                ArrayList<NotificationImageGroup> list = payload.getImages();
                for (int i = 0; i < 1; i++) {
                   remoteViews.setImageViewResource(getViewId("q10_single_image"), R.drawable.pinofi_logo);
                   // remoteViews.setImageViewBitmap(getViewId("q10_single_image"), App.getBitmapFromMemCache(payload.getNotificationId()+"iconUrl"));
                }
            } else if ("basic".equalsIgnoreCase(string)) {
                remoteViews.setViewVisibility(getViewId("q10_multiple_images"), View.GONE);
                remoteViews.setViewVisibility(getViewId("q10_single_image"), View.VISIBLE);
                remoteViews.setImageViewBitmap(getViewId("q10_single_image"), App.getBitmapFromMemCache(payload.getNotificationId()+"iconUrl"));
            } else if ("carousel".equalsIgnoreCase(string)) {
                remoteViews.setViewVisibility(getViewId("q10_multiple_images"), View.VISIBLE);
                remoteViews.setViewVisibility(getViewId("q10_single_image"), View.GONE);
                ArrayList<NotificationImageGroup> list = payload.getImages();
                for (int i = 0; i < 2; i++) {
                    remoteViews.setImageViewBitmap(getViewId("q10_image_" + i), App.getBitmapFromMemCache(list.get(i).getImageUrl()));
                }
            }
           if (!payload.getBackgroundColor().isEmpty() && !payload.getTextColor().isEmpty()) {
                remoteViews.setInt(getViewId("q10_style"), "setBackgroundColor", Color.parseColor(payload.getBackgroundColor()));
                remoteViews.setTextColor(getViewId("q10_title"), Color.parseColor(payload.getTextColor()));
                remoteViews.setTextColor(getViewId("q10_message"), Color.parseColor(payload.getTextColor()));
            } else {
                remoteViews.setInt(getViewId("q10_style"), "setBackgroundColor", Color.WHITE);
            }
            builder.setContent(remoteViews);
        } catch (Exception var6_7) {
            // ab.a(s.a, a.e, "Exception while setting custom color for title and text: %s", var6_7);
        }
    }

    private void setSliderOrCarouse(NotificationCompat.Builder builder, NotificationPayload payload, int index) {
        int i;
        int appiconViewID;
        int[] arrn;
        RemoteViews remoteViews = null;
        String string = payload.getlayoutType();
        boolean bl = false;/*jSONObject.optBoolean("isCarouselV2");*/
        int titleViewId = 0;
        int messageViewID = 0;
        int iconViewID = getViewId("qg_slider_or_carousel_app_icon");
        if (string.equalsIgnoreCase("carousel") && bl) {
            try {
                remoteViews = new RemoteViews(this.context.getPackageName(), getResorceID("qg_carousel_v2", "layout"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            appiconViewID = getViewId("qg_carousel_v2_app_icon");
        } else {
            try {
                remoteViews = new RemoteViews(this.context.getPackageName(), getResorceID("qg_slider_or_carousel", "layout"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            remoteViews.setViewVisibility(getViewId("qg_slider"), View.GONE);
            remoteViews.setViewVisibility(getViewId("qg_carousel"), View.GONE);
            remoteViews.setViewVisibility(getViewId("qg_carousel_center_croped"), View.GONE);
            if (string.equalsIgnoreCase("slider")) {
                remoteViews.setViewVisibility(getViewId("qg_slider"), View.VISIBLE);
            } else {
                remoteViews.setViewVisibility(getViewId("qg_carousel"), View.VISIBLE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_center_croped"), View.VISIBLE);
            }
            titleViewId = getViewId("qg_title");
            messageViewID = getViewId("qg_message");
            appiconViewID = getViewId("qg_slider_or_carousel_app_icon");
            remoteViews.setTextViewText(titleViewId, payload.getTitle());
            remoteViews.setTextViewText(messageViewID, payload.getMessage());
            if (Build.VERSION.SDK_INT < 21) {
                remoteViews.setTextColor(titleViewId, Color.WHITE);
                remoteViews.setTextColor(messageViewID, Color.WHITE);
                int Headerview = getViewId("qg_notification_image_full_content_view");
                remoteViews.setInt(Headerview, "setBackgroundColor", Color.BLUE);
            } else {
                int Headerview = getViewId("qg_notification_image_full_content_view");
                remoteViews.setInt(Headerview, "setBackgroundColor", Color.WHITE);
            }
            remoteViews.setImageViewResource(appiconViewID, R.drawable.pinofi_logo);
        }
        if (!bl && (payload.getIconUrl()!=null)) {
            remoteViews.setViewVisibility(iconViewID, View.VISIBLE);
            remoteViews.setImageViewResource(iconViewID, R.drawable.pinofi_logo);
        } else if (bl) {
            remoteViews.setViewVisibility(iconViewID, View.GONE);
        }
        if (string.equalsIgnoreCase("slider")) {
            arrn = new int[]{getViewId("qg_slider")};
        } else {
            arrn = new int[2];
            if (b) {
                arrn[0] = getViewId("qg_carousel_0_center_croped");
                arrn[1] = getViewId("qg_carousel_1_center_croped");
                remoteViews.setViewVisibility(getViewId("qg_carousel_0"), View.GONE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_1"), View.GONE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_0_center_croped"), View.VISIBLE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_1_center_croped"), View.VISIBLE);
            } else {
                arrn[0] = getViewId("qg_carousel_0");
                arrn[1] = getViewId("qg_carousel_1");
                remoteViews.setViewVisibility(getViewId("qg_carousel_0_center_croped"), View.GONE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_1_center_croped"), View.GONE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_0"), View.VISIBLE);
                remoteViews.setViewVisibility(getViewId("qg_carousel_1"), View.VISIBLE);
            }
        }

        Bundle bundle = null;
        if (payload.getImages() != null) {
            bundle = new Bundle();
        }
        ArrayList<NotificationImageGroup> groupArrayList = payload.getImages();
        NotificationImageGroup notificationImageGroup = null;
        for (i = 0; i < arrn.length; i++) {
            String title;
            if (i == 1) {
                index++;
            }
            notificationImageGroup = groupArrayList.get(getIndex(index,groupArrayList));
            Bitmap bitmap = string.equalsIgnoreCase("slider") && this.b ? getBitmapFromString(context,payload,index): getBitmapFromString(context, payload, index);
            //Bitmap bitmap = string.equalsIgnoreCase("slider") && this.b ? App.getBitmapFromMemCache(notificationImageGroup.getImageUrl()) : App.getBitmapFromMemCache(notificationImageGroup.getImageUrl());
            String deeplink = notificationImageGroup.getDeeplink();
            PendingIntent pendingIntent = getPendingIntent(deeplink,bundle, payload.getNotificationId());
            remoteViews.setImageViewBitmap(arrn[i], bitmap);
            remoteViews.setOnClickPendingIntent(arrn[i], pendingIntent);
            if (titleViewId != 0 && messageViewID != 0 && "slider".equalsIgnoreCase(string)) {
                title = payload.getTitle();
                String message = payload.getMessage();
                if (!title.isEmpty() && !message.isEmpty()) {
                    remoteViews.setTextViewText(titleViewId, title);
                    remoteViews.setTextViewText(messageViewID, message);
                }
            }
            String string2 = "qg_line";
            if (bl) continue;
            remoteViews.setViewVisibility(getViewId(string2 + i + "_with_title_only"), View.GONE);
            remoteViews.setViewVisibility(getViewId(string2 + i + "_with_message_only"), View.GONE);
            remoteViews.setViewVisibility(getViewId(string2 + i), View.GONE);


            /*if (jSONObject2.has("title") && jSONObject2.has("message")) {
                remoteViews.setViewVisibility(getViewId(string2 + n2), View.VISIBLE);
                remoteViews.setTextViewText(getViewId("qg_title" + n2), (CharSequence) jSONObject2.optString("title"));
                remoteViews.setTextViewText(getViewId("qg_message" + n2), (CharSequence) jSONObject2.optString("message"));
                continue;
            }
            if (jSONObject2.has("title")) {
                remoteViews.setViewVisibility(getViewId(string2 + n2 + "_with_title_only"), View.VISIBLE);
                remoteViews.setTextViewText(getViewId("qg_title" + n2 + "_only"), (CharSequence) jSONObject2.optString("title"));
                continue;
            }
            if (!jSONObject2.has("message")) continue;
            remoteViews.setViewVisibility(getViewId(string2 + n2 + "_with_message_only"), View.VISIBLE);
            remoteViews.setTextViewText(getViewId("qg_message" + n2 + "_only"), (CharSequence) jSONObject2.optString("message"));*/
            if (i == 1) {
                index--;
            }
        }
        i = index;
        this.setButtonClick(remoteViews, payload, "next", i + 1);
        this.setButtonClick(remoteViews, payload, "prev", i - 1);
        showNotification(builder, remoteViews, payload);
    }

    private int getIndex(int index, ArrayList<NotificationImageGroup> groupArrayList) {
        int position = 0;
        if(index<groupArrayList.size()){
            position = Math.abs(index);
        }
        if(index==groupArrayList.size()){
            position=0;
        }
        return position;
    }

    //next previous button click event create and add to notification ////////////////////////////////////////////////////<
    private void setButtonClick(RemoteViews remoteViews, NotificationPayload payload, String nextOrPrev, int index) {
        PendingIntent pendingIntent = getPIforButton(payload.getNotificationId(), nextOrPrev, index);
        setClickonremoteview(remoteViews, pendingIntent, String.format("qg_%s_button", nextOrPrev));
    }

    private PendingIntent getPIforButton(int notificationid, String nextOrPrev, int index) {
        Bundle bundle = new Bundle();
        return createPendingIntentForRightLeft(notificationid, bundle, nextOrPrev, index);
    }

    private PendingIntent createPendingIntentForRightLeft(int notificationid, Bundle bundle, String nextOrPrev, int index) {
        Intent intent = new Intent(context, AnimatedBannerBroadcastReceiver.class);
        bundle.putInt("notification_id", notificationid);
        bundle.putInt("index", index);
        bundle.putString("button", nextOrPrev);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context, notificationid+index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setClickonremoteview(RemoteViews remoteViews,PendingIntent pendingIntent, String string) {
        int viewID = getViewId(string);
        remoteViews.setImageViewResource(viewID, R.drawable.pinofi_logo);
        remoteViews.setOnClickPendingIntent(viewID, pendingIntent);
    }

//next prev button click end///////////////////////////////////////////////////////////////////////////////>


// notification delete intent

    private void setDeleteIntent(int notificationId,NotificationCompat.Builder builder){
        Intent intent = new Intent(context, AnimatedBannerBroadcastReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("notificationId",notificationId);
        bundle.putString("deleteNotification","delete");
        intent.putExtras(bundle);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, notificationId+2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setDeleteIntent(pendingIntent);
    }



// Add deeplink of image on notification
    private PendingIntent getPendingIntent(String deeplink, Bundle bundle, int notificationId) {
        if (deeplink.equals("")) {
            deeplink = "home";
        }
        bundle.putString("deeplink", deeplink);
        bundle.putInt("notificationId",notificationId);
        return this.createPendingIntentForDeepLink(notificationId, bundle);
    }

    private PendingIntent createPendingIntentForDeepLink(int notificationId, Bundle bundle) {
        Intent intent = new Intent(context, AnimatedBannerBroadcastReceiver.class);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context, notificationId+3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private int getResorceID(String string, String string2) throws Exception {
        int id = this.context.getResources().getIdentifier(string, string2, this.context.getPackageName());
        if (id == 0) {
            throw new Exception("Identifier not found for " + string);
        }
        return id;
    }

    private int getViewId(String string) {
        int id = 0;
        try {
            id = this.getResorceID(string, "id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private void setTitleAndMessage(NotificationCompat.Builder builder, String title, String message) {
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setAutoCancel(false);
        builder.setSmallIcon(getNotificationIcon(builder));
       /* if (this.c("ic_notification") != 0) {
            this.setSmallIcon(this.c("ic_notification"));
        } else if (this.d("ic_notification") != 0) {
            this.setSmallIcon(this.d("ic_notification"));
        } else if (this.e != 0) {
            this.setSmallIcon(this.e);
            ab.a(s.a, a.e, "setting appIcon as small notification icon");
        } else {
            ab.a(s.c, a.e, "Error in drawing notification, add a ic_notifcation.png file inside mipmap or drawable folder");
        }*/
    }

    private void showNotification(NotificationCompat.Builder builder, RemoteViews rv, NotificationPayload payload) {
        Notification notification = builder.build();
        if (Apputils.isThetypeOfThem(payload.getDisplayType(), "carousel", "slider", "internalGif", "animation") && Build.VERSION.SDK_INT > 15) {
            notification.bigContentView = rv;
        }
        NotificationManagerCompat.from(context).notify(payload.getNotificationId(), notification);
    }
    ////////////////////////////Action

    private void setAction(NotificationPayload payload,NotificationCompat.Builder builder) {

        for (int i = 0; i < payload.getActions().size(); i++) {
            int n3 = 0;
            Action action = payload.getActions().get(i);
            if (!action.getText().isEmpty()) {
                Bundle bundle3 = new Bundle();
             //   bundle3.putBoolean("poll", bl);
                PendingIntent pendingIntent3 = actionPendingIntent(payload, action, bundle3,i);
                builder.addAction(n3, action.getText(), pendingIntent3);
            }

        }
    }


    private PendingIntent actionPendingIntent(NotificationPayload payload,Action action , Bundle bundle,int id) {
        String displayactionText = action.getText();
        String deeplink = action.getDeeplink();
        if (!displayactionText.equals("")) {
            bundle.putString("displayText",displayactionText);
            bundle.putString("deeplink",deeplink);
            bundle.putInt("notificationId", payload.getNotificationId());
        }
        return getPendingIntent("actionClicked", payload.getNotificationId(), bundle,id);
    }

    PendingIntent getPendingIntent(String string, int notificationId, Bundle bundle,int id) {

        Intent intent = new Intent(context, AnimatedBannerBroadcastReceiver.class);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context, id,intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



}


