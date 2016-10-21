package com.fourtyseven.notificationpoc.notificationpayload;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dell on 9/28/2016.
 */
public class NotificationAction{
    private String title;

    private String text;

    private String color;

    private String deeplink;

    public NotificationAction(){

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

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getDeeplink ()
    {
        return deeplink;
    }

    public void setDeeplink (String deeplink)
    {
        this.deeplink = deeplink;
    }

}
