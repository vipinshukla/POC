package com.fourtyseven.notificationpoc.broadcastreciver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fourtyseven.notificationpoc.App;
import com.fourtyseven.notificationpoc.NotificationId;
import com.fourtyseven.notificationpoc.notificationpayload.Action;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationAction;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationImageGroup;
import com.fourtyseven.notificationpoc.notificationpayload.NotificationPayload;
import com.fourtyseven.notificationpoc.notificationtype.CarouselNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 9/26/2016.
 */
public class FireBaseNotificationService extends FirebaseMessagingService {


    NotificationPayload payload;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Message Recived", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("Size", "Message data payload: " + remoteMessage.getData());
            Map data = remoteMessage.getData();
            Set<Map.Entry<String, String>> set = data.entrySet();
            payload = new NotificationPayload();
            ArrayList<NotificationImageGroup> notificationImageGroups = null;
            ArrayList<Action> actionArrayList = null;
            for (Map.Entry<String, String> me : set) {
                switch (me.getKey()){
                    case "images":{
                        try {
                            notificationImageGroups = new ArrayList<NotificationImageGroup>();
                            ArrayList<NotificationAction> notificationActionArrayList = null;
                            JSONArray jsonArray = new JSONArray(me.getValue());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                NotificationImageGroup notificationImageGroup = new NotificationImageGroup();
                                JSONObject obj = jsonArray.getJSONObject(i);
                                notificationImageGroup.setTitle(obj.getString("title"));
                                notificationImageGroup.setImageUrl(obj.getString("imageUrl"));
                                notificationImageGroup.setText(obj.getString("text"));
                                notificationImageGroup.setDeeplink(obj.getString("deeplink"));
                                JSONArray actionArray = new JSONArray(obj.getString("action"));
                                for (int j = 0; j < actionArray.length(); j++) {
                                    notificationActionArrayList = new ArrayList<NotificationAction>();
                                    NotificationAction notificationAction = new NotificationAction();
                                    JSONObject actionObject = actionArray.getJSONObject(j);
                                    notificationAction.setColor(actionObject.getString("color"));
                                    notificationAction.setText(actionObject.getString("text"));
                                    notificationAction.setTitle(actionObject.getString("title"));
                                    notificationAction.setDeeplink(actionObject.getString("deeplink"));
                                    notificationActionArrayList.add(notificationAction);
                                }
                                notificationImageGroup.setActions(notificationActionArrayList);
                                notificationImageGroups.add(notificationImageGroup);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    }
                    case "campaignName":{
                        payload.setCampaignName(me.getValue());
                        break;
                    }
                    case "campaignType":{
                        payload.setCampaignType(me.getValue());
                        break;
                    }
                    case "iconUrl":{
                       // payload.setImageUrl(me.getValue());
                        break;
                    }
                    case "imageUrl":{
                        payload.setImageUrl(me.getValue());
                        break;
                    }
                    case "deepLink":{
                        payload.setDeepLink(me.getValue());
                        break;
                    }
                    case "displayType":{
                        payload.setDisplayType(me.getValue());
                        break;
                    }
                    case "layout":{
                        payload.setLayoutType(me.getValue());
                        break;
                    }
                    case "title":{
                        payload.setTitle(me.getValue());
                        break;
                    }
                    case "message":
                        payload.setMessage(me.getValue());
                        break;
                    case "action":
                        actionArrayList = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(me.getValue());
                            for (int i = 0;i<jsonArray.length();i++){
                                Action action = new Action();
                                JSONObject obj = jsonArray.getJSONObject(i);
                                action.setColor(obj.getString("color"));
                                action.setText(obj.getString("text"));
                                action.setTitle(obj.getString("title"));
                                action.setDeeplink(obj.getString("deeplink"));
                                actionArrayList.add(action);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                }
            }

            int id = NotificationId.getID();
            payload.setNotificationId(id);
            payload.setActions(actionArrayList);
            payload.setImages(notificationImageGroups);

            File directory = getApplicationContext().getDir(id+"_noti", Context.MODE_PRIVATE);
            if(!directory.exists()){
                directory.mkdir();
            }

            if(payload.getDisplayType().equalsIgnoreCase("banner")|| payload.getDisplayType().equalsIgnoreCase("basic")){
                if(payload.getImageUrl()!=null) {

                    if (!payload.getImageUrl().isEmpty()) {
                        FileOutputStream fos = null;
                        try {

                            File myPath = new File(directory, payload.getNotificationId()+ ".png");
                            fos = new FileOutputStream(myPath);
                            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(payload.getImageUrl()).getContent());
                            App.addBitmapToMemoryCache(payload.getImageUrl(),bitmap);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
            else {

                for (int i = 0; i < notificationImageGroups.size(); i++) {
                    if (notificationImageGroups != null) {
                        FileOutputStream fos = null;
                        try {
                            String s = payload.getNotificationId()+"_"+i;
                            File myPath = new File(directory, payload.getNotificationId()+"_"+i);
                            fos = new FileOutputStream(myPath);
                            NotificationImageGroup group = notificationImageGroups.get(i);
                            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(group.getImageUrl()).getContent());
                            App.addBitmapToMemoryCache(payload.getNotificationId()+"_"+i,bitmap);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CarouselNotification notification = new CarouselNotification(getApplicationContext(),payload);
                notification.customProgressAnimationNotification();
            }
        });



    }


}
