package com.fourtyseven.notificationpoc.notificationpayload;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell on 9/28/2016.
 */
public class NotificationPayload{
    private String message;

    private int NotificationId;

    private String basePayload;

    private String campaignName;
    private int position;

    private String millisecondsToRefresh;

    private String title;

    private String campaignType;

    private String imageUrl;

    private String displayType;

    private String deepLink;
    private ArrayList<Action> action;
    private String layoutType;
    private String iconUrl;
    private String backgroundColor;
    private String textColor;

    public ArrayList<Action> getActions ()
    {
        return action;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void setActions (ArrayList<Action> action)
    {
        this.action = action;
    }


    private ArrayList<String> arrayListImageBitmap;

    private ArrayList<NotificationImageGroup> images;


    public ArrayList<String> getArrayListImageBitmap() {
        return arrayListImageBitmap;
    }

    public void setArrayListImageBitmap(ArrayList<String> arrayListImageBitmap) {
        this.arrayListImageBitmap = arrayListImageBitmap;
    }
    public NotificationPayload(){

    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCampaignName ()
    {
        return campaignName;
    }

    public void setCampaignName (String campaignName)
    {
        this.campaignName = campaignName;
    }

    public String getMillisecondsToRefresh ()
    {
        return millisecondsToRefresh;
    }

    public void setMillisecondsToRefresh (String millisecondsToRefresh)
    {
        this.millisecondsToRefresh = millisecondsToRefresh;
    }

    public String getBasePayload() {
        return basePayload;
    }

    public void setBasePayload(String basePayload) {
        this.basePayload = basePayload;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCampaignType ()
    {
        return campaignType;
    }

    public void setCampaignType (String campaignType)
    {
        this.campaignType = campaignType;
    }

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDisplayType ()
    {
        return displayType;
    }

    public void setDisplayType (String displayType)
    {
        this.displayType = displayType;
    }

    public String getDeepLink ()
    {
        return deepLink;
    }

    public void setDeepLink (String deepLink)
    {
        this.deepLink = deepLink;
    }

    public ArrayList<NotificationImageGroup> getImages ()
    {
        return images;
    }

    public void setImages (ArrayList<NotificationImageGroup> images)
    {
        this.images = images;
    }

    public String getlayoutType() {
        return layoutType;
    }
    public void setLayoutType(String layoutType){
        this.layoutType = layoutType;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public String getIconUrl(){
        return iconUrl;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public String getBackgroundColor(){
        return backgroundColor;
    }
    public void setTextColor(String textColor){
        this.textColor = textColor;
    }

    public String getTextColor() {
        return textColor;
    }
}
