package com.fourtyseven.notificationpoc.notificationpayload;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell on 9/28/2016.
 */
public class NotificationImageGroup{
    private String title;

    private String text;

    private String imageUrl;

    private String deeplink;

    private ArrayList<NotificationAction> actions;

    public NotificationImageGroup(){

    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDeeplink ()
    {
        return deeplink;
    }

    public void setDeeplink (String deeplink)
    {
        this.deeplink = deeplink;
    }

    public ArrayList<NotificationAction> getActions ()
    {
        return actions;
    }

    public void setActions (ArrayList<NotificationAction> actions)
    {
        this.actions = actions;
    }


}
