/*
import android.view.View;

* Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Color
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.support.v4.app.NotificationCompat
 *  android.support.v4.app.NotificationCompat$BigPictureStyle
 *  android.support.v4.app.NotificationCompat$BigTextStyle
 *  android.support.v4.app.NotificationCompat$Builder
 *  android.support.v4.app.NotificationCompat$Style
 *  android.support.v4.app.NotificationManagerCompat
 *  android.widget.RemoteViews
 *  org.json.JSONArray
 *  org.json.JSONObject


package com.fourtyseven.notificationpoc.notificationtype;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import com.quantumgraph.sdk.NotificationIntentProcessor;
import com.quantumgraph.sdk.QG;
import com.quantumgraph.sdk.a;
import com.quantumgraph.sdk.ab;
import com.quantumgraph.sdk.c;
import com.quantumgraph.sdk.s;
import org.json.JSONArray;
import org.json.JSONObject;

public class t
extends NotificationCompat.Builder {
    private final Context a;
    private boolean b = true;
    private String c;
    private RemoteViews d;
    private int e;
    private QG f;

    protected t(Context context, String string) {
        super(context);
        this.a = context;
        this.c = string;
        this.e = context.getApplicationInfo().icon;
    }

    protected void a(JSONObject jSONObject) {
        boolean bl;
        String string = jSONObject.getString("title");
        String string2 = jSONObject.getString("message");
        this.a(string, string2);
        String string3 = jSONObject.optString("bgColor");
        String string4 = jSONObject.optString("textColor");
        String string5 = jSONObject.optString("imageUrl");
        boolean bl2 = jSONObject.optBoolean("q10CF", false);
        this.b = jSONObject.optBoolean(a.p, true);
        boolean bl3 = true;
        if (bl2 && ab.a(this.c, "carousel", "slider", "basic")) {
            ab.a(s.a, a.e, "calling setQ10CollapsedFormat");
            this.a(jSONObject, string, string2, string3, string4);
            bl3 = false;
        }
        if (!string3.isEmpty() && !string4.isEmpty() && bl3) {
            this.a(string, string2, string3, string4, string5);
        }
        if ((bl = jSONObject.optBoolean("showTextOnly", false)) && !jSONObject.optBoolean("isCompatibleNetworkType", true)) {
            Bitmap bitmap = BitmapFactory.decodeResource((Resources)this.a.getResources(), (int)this.e);
            this.setLargeIcon(bitmap);
            this.b(jSONObject);
        } else {
            this.a(string5);
            if ("basic".equalsIgnoreCase(this.c)) {
                this.b(string, string2);
                this.c(jSONObject.optString("bigImageUrl"), string2);
                this.b(jSONObject);
            } else if ("banner".equalsIgnoreCase(this.c)) {
                this.b(jSONObject.getString("contentImageUrl"));
                this.b(jSONObject.getString("contentImageUrl"));
                this.b(jSONObject);
            } else if (this.c.equalsIgnoreCase("gif") || this.c.equalsIgnoreCase("internalGif")) {
                this.c(jSONObject);
            } else if ("animation".equalsIgnoreCase(this.c)) {
                this.b(jSONObject.getString("contentImageUrl"));
            } else if ("carousel".equalsIgnoreCase(this.c) || "slider".equalsIgnoreCase(this.c)) {
                this.d(jSONObject);
            }
        }
        int n2 = jSONObject.getInt(a.m);
        String string6 = jSONObject.optString(a.o);
        Bundle bundle = null;
        if (!jSONObject.isNull(a.n)) {
            bundle = ab.a(jSONObject.getJSONObject(a.n));
        }
        JSONArray jSONArray = jSONObject.optJSONArray("actions");
        boolean bl4 = jSONObject.optBoolean("poll", false);
        this.a(n2, bl4, string6, bundle, jSONArray);
    }

    private void a(String string, String string2, String string3, String string4, String string5) {
        try {
            RemoteViews remoteViews = new RemoteViews(this.a.getPackageName(), this.d("custom_collapsed_notification", "layout"));
            remoteViews.setViewVisibility(this.e("bg_and_text_color"), 0);
            remoteViews.setViewVisibility(this.e("q10_style"), 8);
            int n2 = this.e("icon");
            if (!string5.equals("")) {
                Bitmap bitmap = c.e(this.a, string5);
                if (this.b) {
                    bitmap = c.a(bitmap, this.a);
                }
                remoteViews.setImageViewBitmap(n2, bitmap);
            } else {
                remoteViews.setImageViewBitmap(n2, BitmapFactory.decodeResource((Resources)this.a.getResources(), (int)this.e));
            }
            int n3 = this.e("title");
            remoteViews.setTextViewText(n3, (CharSequence)string);
            remoteViews.setTextColor(n3, Color.parseColor((String)string4));
            int n4 = this.e("message");
            remoteViews.setTextViewText(n4, (CharSequence)string2);
            remoteViews.setTextColor(n4, Color.parseColor((String)string4));
            remoteViews.setInt(this.e("icon"), "setBackgroundColor", Color.parseColor((String)string3));
            remoteViews.setInt(this.e("title_and_message"), "setBackgroundColor", Color.parseColor((String)string3));
            this.setContent(remoteViews);
        }
        catch (Exception var6_7) {
            ab.a(s.a, a.e, "Exception while setting custom color for title and text: %s", var6_7);
        }
    }

    private Bitmap a(String string, float f2) {
        return this.b ? c.a(this.a, string, Float.valueOf(f2)) : c.e(this.a, string);
    }

    private void a(JSONObject jSONObject, String string, String string2, String string3, String string4) {
        try {
            RemoteViews remoteViews = new RemoteViews(this.a.getPackageName(), this.d("custom_collapsed_notification", "layout"));
            remoteViews.setViewVisibility(this.e("bg_and_text_color"), 8);
            remoteViews.setViewVisibility(this.e("q10_style"), 0);
            remoteViews.setTextViewText(this.e("q10_title"), (CharSequence)string);
            remoteViews.setTextViewText(this.e("q10_message"), (CharSequence)string2);
            if ("slider".equalsIgnoreCase(this.c)) {
                remoteViews.setViewVisibility(this.e("q10_multiple_images"), 8);
                remoteViews.setViewVisibility(this.e("q10_single_image"), 0);
                JSONArray jSONArray = jSONObject.getJSONArray(this.c);
                JSONObject jSONObject2 = jSONArray.getJSONObject(0);
                remoteViews.setImageViewBitmap(this.e("q10_single_image"), this.a(jSONObject2.optString("image"), 2.0f));
            } else if ("basic".equalsIgnoreCase(this.c)) {
                remoteViews.setViewVisibility(this.e("q10_multiple_images"), 8);
                remoteViews.setViewVisibility(this.e("q10_single_image"), 0);
                remoteViews.setImageViewBitmap(this.e("q10_single_image"), this.a(jSONObject.optString("bigImageUrl"), 2.0f));
            } else if ("carousel".equalsIgnoreCase(this.c)) {
                remoteViews.setViewVisibility(this.e("q10_multiple_images"), 0);
                remoteViews.setViewVisibility(this.e("q10_single_image"), 8);
                JSONArray jSONArray = jSONObject.getJSONArray(this.c);
                for (int i2 = 0; i2 < 2; ++i2) {
                    remoteViews.setImageViewBitmap(this.e("q10_image_" + i2), this.a(jSONArray.getJSONObject(i2).optString("image"), 1.0f));
                }
            }
            if (!string3.isEmpty() && !string4.isEmpty()) {
                remoteViews.setInt(this.e("q10_style"), "setBackgroundColor", Color.parseColor((String)string3));
                remoteViews.setTextColor(this.e("q10_title"), Color.parseColor((String)string4));
                remoteViews.setTextColor(this.e("q10_message"), Color.parseColor((String)string4));
            } else {
                remoteViews.setInt(this.e("q10_style"), "setBackgroundColor", -1);
            }
            this.setContent(remoteViews);
        }
        catch (Exception var6_7) {
            ab.a(s.a, a.e, "Exception while setting custom color for title and text: %s", var6_7);
        }
    }

    protected void b(JSONObject jSONObject) {
        if (jSONObject.has("soundUrl")) {
            try {
                c.c(this.a, jSONObject.getString("soundUrl"));
                this.setSound(Uri.parse((String)c.d(this.a, jSONObject.optString("soundUrl"))));
                this.setDefaults(6);
            }
            catch (Exception var2_2) {
                this.f = QG.getInstance(this.a, true);
                this.f.logException(ab.a(new Exception("Error in downloading notification sound")));
                this.setDefaults(-1);
            }
        } else {
            this.setDefaults(-1);
        }
    }

    private void a(String string, String string2) {
        this.setContentTitle((CharSequence)string);
        this.setContentText((CharSequence)string2);
        this.setAutoCancel(false);
        if (this.c("ic_notification") != 0) {
            this.setSmallIcon(this.c("ic_notification"));
        } else if (this.d("ic_notification") != 0) {
            this.setSmallIcon(this.d("ic_notification"));
        } else if (this.e != 0) {
            this.setSmallIcon(this.e);
            ab.a(s.a, a.e, "setting appIcon as small notification icon");
        } else {
            ab.a(s.c, a.e, "Error in drawing notification, add a ic_notifcation.png file inside mipmap or drawable folder");
        }
    }

    private void b(String string, String string2) {
        this.setStyle((NotificationCompat.Style)new NotificationCompat.BigTextStyle().bigText((CharSequence)string2).setBigContentTitle((CharSequence)string));
    }

    private void a(String string) {
        if (!string.equals("")) {
            Bitmap bitmap = c.e(this.a, string);
            if (this.b) {
                bitmap = c.a(bitmap, this.a);
            }
            this.setLargeIcon(bitmap);
            return;
        }
        if (this.e == 0) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource((Resources)this.a.getResources(), (int)this.e);
        this.setLargeIcon(bitmap);
    }

    private void b(String string) {
        if (string.equals("")) {
            return;
        }
        RemoteViews remoteViews = new RemoteViews(this.a.getPackageName(), this.d("qg_full_content_view", "layout"));
        Bitmap bitmap = c.e(this.a, string);
        int n2 = this.e("qg_full_content_view_animation_image");
        remoteViews.setImageViewBitmap(n2, bitmap);
        this.setContent(remoteViews);
    }

    private void c(JSONObject jSONObject) {
        JSONObject jSONObject2;
        String string;
        Bitmap bitmap;
        int n2;
        String string2 = jSONObject.optString("type");
        this.d = new RemoteViews(this.a.getPackageName(), this.d("qg_full_content_view", "layout"));
        int n3 = this.e("qg_full_content_view_animation_image");
        this.d.setViewVisibility(n3, 8);
        JSONObject jSONObject3 = jSONObject.optJSONObject(string2);
        JSONArray jSONArray = jSONObject3.optJSONArray("images");
        ab.a(s.a, "firstRun ", String.valueOf(jSONObject.optBoolean("firstRun", true)));
        for (int i2 = 0; i2 < jSONArray.length() && jSONObject.optBoolean("firstRun", true); ++i2) {
            c.e(this.a, jSONArray.optString(i2));
        }
        Bundle bundle = null;
        if (!jSONObject.isNull(a.n)) {
            bundle = ab.a(jSONObject.getJSONObject(a.n));
        }
        JSONArray jSONArray2 = jSONObject.optJSONArray("buttons");
        int n4 = this.e("qg_app_icon");
        this.d.setImageViewResource(n4, this.e);
        int n5 = this.e("qg_full_content_view_title_message");
        int n6 = this.e("qg_full_content_view_button");
        int n7 = this.e("qg_full_content_view_button1_only");
        this.d.setViewVisibility(n5, 8);
        this.d.setViewVisibility(n6, 8);
        this.d.setViewVisibility(n7, 8);
        if (jSONArray2 != null) {
            if (jSONArray2.length() == 1) {
                this.d.setViewVisibility(n7, 0);
                n2 = this.e("qg_button");
                JSONObject jSONObject4 = jSONArray2.getJSONObject(0);
                this.d.setTextViewText(n2, (CharSequence)jSONObject4.getString("text"));
                this.d.setOnClickPendingIntent(n2, this.a(jSONObject4.optString(a.o), 1, bundle, jSONObject.getInt(a.m)));
                this.d.setOnClickPendingIntent(n4, this.a("", 3, bundle, jSONObject.getInt(a.m)));
            } else {
                this.d.setViewVisibility(n6, 0);
                String string3 = "qg_button";
                for (int i3 = 0; i3 < jSONArray2.length() && i3 < 2; ++i3) {
                    int n8 = this.e(string3 + (i3 + 1));
                    jSONObject2 = jSONArray2.getJSONObject(i3);
                    this.d.setTextViewText(n8, (CharSequence)jSONObject2.getString("text"));
                    this.d.setOnClickPendingIntent(n8, this.a(jSONObject2.optString(a.o), i3 + 1, bundle, jSONObject.getInt(a.m)));
                }
                this.d.setOnClickPendingIntent(n4, this.a("", 3, bundle, jSONObject.getInt(a.m)));
            }
        } else {
            this.d.setViewVisibility(n5, 0);
            int n9 = this.e("qg_title");
            this.d.setTextViewText(n9, (CharSequence)jSONObject.getString("title"));
            int n10 = this.e("qg_message");
            this.d.setTextViewText(n10, (CharSequence)jSONObject.getString("message"));
            this.d.setOnClickPendingIntent(n4, this.a(jSONObject.optString(a.o, ""), 3, bundle, jSONObject.getInt(a.m)));
            this.d.setOnClickPendingIntent(n5, this.a(jSONObject.optString(a.o, ""), 4, bundle, jSONObject.getInt(a.m)));
        }
        n2 = this.e("qg_full_content_view_gif_image");
        int n11 = this.e("qg_full_content_view_gif_start_image");
        this.d.setViewVisibility(n2, 0);
        Bitmap bitmap2 = c.e(this.a, jSONObject.optString("gifPlayButton"));
        jSONObject2 = c.e(this.a, jSONArray.optString(0));
        if (string2.equalsIgnoreCase("gif")) {
            this.d.setViewVisibility(n11, 0);
            this.d.setImageViewBitmap(n11, bitmap2);
            this.d.setImageViewBitmap(n2, (Bitmap)jSONObject2);
        } else {
            this.d.setViewVisibility(n11, 8);
            string = jSONObject.getString("contentImageUrl");
            if (string.equals("")) {
                return;
            }
            bitmap = c.e(this.a, string);
            this.d.setImageViewBitmap(n2, bitmap);
        }
        string = new Intent(this.a, (Class)NotificationIntentProcessor.class);
        bitmap = new Bundle();
        bitmap.putString("message", jSONObject.toString());
        bitmap.putString("type", string2);
        string.putExtras((Bundle)bitmap);
        PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)this.a, (int)1234, (Intent)string, (int)134217728);
        if (string2.equalsIgnoreCase("internalGif")) {
            this.d.setOnClickPendingIntent(n2, pendingIntent);
        } else {
            this.d.setOnClickPendingIntent(n11, pendingIntent);
        }
    }

    private PendingIntent a(String string, int n2, Bundle bundle, int n3) {
        String string2 = "notify://click?deepLink=%s&pos=%s";
        if (string.equals("")) {
            string = "home";
        }
        string2 = String.format(string2, string, n2);
        Bundle bundle2 = new Bundle();
        bundle2.putString(a.o, string2);
        if (bundle != null) {
            bundle2.putBundle(a.n, bundle);
        }
        return this.a("actionClicked", n3, n2, bundle2);
    }

    private void c(String string, String string2) {
        if (string.equals("")) {
            return;
        }
        Bitmap bitmap = this.b ? c.a(this.a, string, Float.valueOf(ab.f(this.a))) : c.e(this.a, string);
        this.setStyle((NotificationCompat.Style)new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText((CharSequence)string2));
    }

    private void a(int n2, boolean bl, String string, Bundle bundle, JSONArray jSONArray) {
        Bundle bundle2 = new Bundle();
        if (!string.equals("")) {
            bundle2.putString(a.o, string);
        }
        if (bundle != null) {
            bundle2.putBundle(a.n, bundle);
        }
        PendingIntent pendingIntent = this.a("notification_clicked", n2, 0, bundle2);
        this.setContentIntent(pendingIntent);
        PendingIntent pendingIntent2 = this.a("notification_deleted", n2, 0, bundle2);
        this.setDeleteIntent(pendingIntent2);
        if (jSONArray == null) {
            return;
        }
        for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
            int n3 = 0;
            JSONObject jSONObject = jSONArray.getJSONObject(i2);
            if (!jSONObject.isNull("icon")) {
                n3 = this.c(jSONObject.getString("icon"));
            }
            Bundle bundle3 = new Bundle();
            if (bundle != null) {
                bundle3.putBundle(a.n, bundle);
            }
            bundle3.putBoolean("poll", bl);
            PendingIntent pendingIntent3 = this.a(jSONObject, n2, i2, bundle3);
            this.addAction(n3, (CharSequence)jSONObject.getString("text"), pendingIntent3);
        }
    }

    private PendingIntent a(JSONObject jSONObject, int n2, int n3, Bundle bundle) {
        int n4 = n3 + 2;
        String string = jSONObject.optString(a.o);
        String string2 = jSONObject.optString("id");
        bundle.putString("actionId", string2);
        if (!string.equals("")) {
            bundle.putString(a.o, string);
        }
        return this.a("actionClicked", n2, n4, bundle);
    }

    PendingIntent a(String string, int n2, int n3, Bundle bundle) {
        bundle.putString("packageName", this.a.getPackageName());
        bundle.putInt(a.m, n2);
        Intent intent = new Intent(this.a, (Class)NotificationIntentProcessor.class);
        intent.setAction(string).putExtras(bundle);
        return PendingIntent.getBroadcast((Context)this.a, (int)n3, (Intent)intent, (int)134217728);
    }

    protected void a() {
        Notification notification = this.build();
        if (ab.a(this.c, "carousel", "slider", "internalGif", "gif") && Build.VERSION.SDK_INT > 15) {
            notification.bigContentView = this.d;
        }
        NotificationManagerCompat.from((Context)this.a).notify(281739, notification);
    }

    private int c(String string) {
        return this.a.getResources().getIdentifier(string, "drawable", this.a.getPackageName());
    }

    private int d(String string) {
        return this.a.getResources().getIdentifier(string, "mipmap", this.a.getPackageName());
    }

    private int d(String string, String string2) {
        int n2 = this.a.getResources().getIdentifier(string, string2, this.a.getPackageName());
        if (n2 == 0) {
            throw new Exception("Identifier not found for " + string);
        }
        return n2;
    }

    private void d(JSONObject jSONObject) {
        int n2;
        int n3;
        int[] arrn;
        String string = jSONObject.optString("type");
        boolean bl = jSONObject.optBoolean("isCarouselV2");
        int n4 = -1;
        int n5 = -1;
        if (string.equalsIgnoreCase("carousel") && bl) {
            this.d = new RemoteViews(this.a.getPackageName(), this.d("qg_carousel_v2", "layout"));
            n3 = this.e("qg_carousel_v2_app_icon");
        } else {
            this.d = new RemoteViews(this.a.getPackageName(), this.d("qg_slider_or_carousel", "layout"));
            this.d.setViewVisibility(this.e("qg_slider"), 8);
            this.d.setViewVisibility(this.e("qg_carousel"), 8);
            this.d.setViewVisibility(this.e("qg_carousel_center_croped"), 8);
            if (string.equalsIgnoreCase("slider")) {
                this.d.setViewVisibility(this.e("qg_slider"), 0);
            } else {
                this.d.setViewVisibility(this.e("qg_carousel"), 0);
                this.d.setViewVisibility(this.e("qg_carousel_center_croped"), 0);
            }
            n4 = this.e("qg_title");
            n5 = this.e("qg_message");
            n3 = this.e("qg_slider_or_carousel_app_icon");
            this.d.setTextViewText(n4, (CharSequence)jSONObject.getString("title"));
            this.d.setTextViewText(n5, (CharSequence)jSONObject.getString("message"));
            if (Build.VERSION.SDK_INT < 21) {
                this.d.setTextColor(n4, -1);
                this.d.setTextColor(n5, -1);
            } else {
                int n6 = this.e("qg_notification_image_full_content_view");
                this.d.setInt(n6, "setBackgroundColor", -1);
            }
            this.d.setImageViewResource(n3, this.e);
        }
        if (bl && jSONObject.has("iconImage")) {
            this.d.setViewVisibility(n3, 0);
            this.d.setImageViewBitmap(n3, c.e(this.a, jSONObject.getString("iconImage")));
        } else if (bl) {
            this.d.setViewVisibility(n3, 8);
        }
        if (string.equalsIgnoreCase("slider")) {
            arrn = new int[]{this.e("qg_slider")};
        } else {
            arrn = new int[2];
            if (!this.b) {
                arrn[0] = this.e("qg_carousel_0_center_croped");
                arrn[1] = this.e("qg_carousel_1_center_croped");
                this.d.setViewVisibility(this.e("qg_carousel_0"), 8);
                this.d.setViewVisibility(this.e("qg_carousel_1"), 8);
                this.d.setViewVisibility(this.e("qg_carousel_0_center_croped"), 0);
                this.d.setViewVisibility(this.e("qg_carousel_1_center_croped"), 0);
            } else {
                arrn[0] = this.e("qg_carousel_0");
                arrn[1] = this.e("qg_carousel_1");
                this.d.setViewVisibility(this.e("qg_carousel_0_center_croped"), 8);
                this.d.setViewVisibility(this.e("qg_carousel_1_center_croped"), 8);
                this.d.setViewVisibility(this.e("qg_carousel_0"), 0);
                this.d.setViewVisibility(this.e("qg_carousel_1"), 0);
            }
        }
        Bundle bundle = null;
        if (!jSONObject.isNull(a.n)) {
            bundle = ab.a(jSONObject.getJSONObject(a.n));
        }
        JSONArray jSONArray = jSONObject.getJSONArray(string);
        for (n2 = 0; n2 < arrn.length; ++n2) {
            String string2;
            JSONObject jSONObject2 = jSONArray.getJSONObject(n2);
            Bitmap bitmap = string.equalsIgnoreCase("slider") && this.b ? c.a(this.a, jSONObject2.optString("image"), Float.valueOf(ab.f(this.a))) : c.b(this.a, jSONObject2.optString("image"));
            String string3 = jSONObject2.optString(a.o, "home");
            string3 = Uri.encode((String)string3);
            int n7 = jSONObject2.optInt("pos", n2);
            PendingIntent pendingIntent = this.b(string3, n7, bundle, jSONObject.getInt(a.m));
            this.d.setImageViewBitmap(arrn[n2], bitmap);
            this.d.setOnClickPendingIntent(arrn[n2], pendingIntent);
            if (n4 != -1 && n5 != -1 && "slider".equalsIgnoreCase(string)) {
                string2 = jSONObject2.optString("title", "");
                String string4 = jSONObject2.optString("message", "");
                if (!string2.isEmpty() && !string4.isEmpty()) {
                    this.d.setTextViewText(n4, (CharSequence)string2);
                    this.d.setTextViewText(n5, (CharSequence)string4);
                }
            }
            string2 = "qg_line";
            if (!bl) continue;
            this.d.setViewVisibility(this.e(string2 + n2 + "_with_title_only"), 8);
            this.d.setViewVisibility(this.e(string2 + n2 + "_with_message_only"), 8);
            this.d.setViewVisibility(this.e(string2 + n2), 8);
            if (jSONObject2.has("title") && jSONObject2.has("message")) {
                this.d.setViewVisibility(this.e(string2 + n2), 0);
                this.d.setTextViewText(this.e("qg_title" + n2), (CharSequence)jSONObject2.optString("title"));
                this.d.setTextViewText(this.e("qg_message" + n2), (CharSequence)jSONObject2.optString("message"));
                continue;
            }
            if (jSONObject2.has("title")) {
                this.d.setViewVisibility(this.e(string2 + n2 + "_with_title_only"), 0);
                this.d.setTextViewText(this.e("qg_title" + n2 + "_only"), (CharSequence)jSONObject2.optString("title"));
                continue;
            }
            if (!jSONObject2.has("message")) continue;
            this.d.setViewVisibility(this.e(string2 + n2 + "_with_message_only"), 0);
            this.d.setTextViewText(this.e("qg_message" + n2 + "_only"), (CharSequence)jSONObject2.optString("message"));
        }
        n2 = jSONObject.getInt("pos");
        this.a(jSONObject, "next", n2 + 1);
        this.a(jSONObject, "prev", n2 - 1);
    }

    private int e(String string) {
        return this.d(string, "id");
    }

    private void a(JSONObject jSONObject, String string, int n2) {
        String string2 = String.format("notify://%s?pos=%s", string, n2);
        PendingIntent pendingIntent = this.b(jSONObject, string2, n2);
        this.a(jSONObject, pendingIntent, String.format("qg_%s_button", string));
    }

    private void a(JSONObject jSONObject, PendingIntent pendingIntent, String string) {
        int n2 = this.e(string);
        Bitmap bitmap = c.a(this.a, jSONObject.optString(string), Float.valueOf(1.0f), "qgsdkres");
        this.d.setImageViewBitmap(n2, bitmap);
        this.d.setOnClickPendingIntent(n2, pendingIntent);
    }

    private PendingIntent b(JSONObject jSONObject, String string, int n2) {
        int n3 = jSONObject.optInt(a.m);
        Bundle bundle = new Bundle();
        string = String.format(string, n2);
        bundle.putString(a.o, string);
        int n4 = n2 + 100;
        return this.a("actionClicked", n3, n4, bundle);
    }

    private PendingIntent b(String string, int n2, Bundle bundle, int n3) {
        String string2 = "notify://click?deepLink=%s&pos=%s";
        if (string.equals("")) {
            string = "home";
        }
        string2 = String.format(string2, string, n2);
        Bundle bundle2 = new Bundle();
        bundle2.putString(a.o, string2);
        if (bundle != null) {
            bundle2.putBundle(a.n, bundle);
        }
        return this.a("actionClicked", n3, n2, bundle2);
    }
}

*/
