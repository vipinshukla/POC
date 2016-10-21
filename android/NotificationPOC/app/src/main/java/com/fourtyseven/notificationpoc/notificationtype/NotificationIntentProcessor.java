/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.NotificationManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 *//*

package com.fourtyseven.notificationpoc.notificationtype;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.quantumgraph.sdk.GcmNotificationIntentService;
import com.quantumgraph.sdk.QG;
import com.quantumgraph.sdk.a;
import com.quantumgraph.sdk.ab;
import com.quantumgraph.sdk.c;
import com.quantumgraph.sdk.s;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationIntentProcessor
extends BroadcastReceiver {
    protected static final String a = NotificationIntentProcessor.class.getSimpleName();
    private Context c;
    protected static QG b;

    private static void a(Context context, int n2) {
        GcmNotificationIntentService.b();
        ((NotificationManager)context.getSystemService("notification")).cancel(n2);
    }

    private void a(Bundle bundle) {
        ab.a(s.a, a, "start of processInternalGif");
        String string = bundle.getString("type");
        GcmNotificationIntentService.b();
        JSONObject jSONObject = new JSONObject(bundle.getString("message"));
        jSONObject.put("firstRun", false);
        Bundle bundle2 = new Bundle();
        if (string.equalsIgnoreCase("gif")) {
            bundle2.putString("type", "internalGif");
            b.logEvent("qg_gif_played");
            jSONObject.put("type", (Object)"internalGif");
            jSONObject.put("internalGif", (Object)jSONObject.optJSONObject(string));
        } else {
            bundle2.putString("type", "gif");
            b.logEvent("qg_gif_paused");
            jSONObject.put("type", (Object)"gif");
            jSONObject.put("gif", (Object)jSONObject.optJSONObject(string));
        }
        bundle2.putString("message", jSONObject.toString());
        Intent intent = new Intent(this.c, (Class)GcmNotificationIntentService.class);
        intent.setAction(a.l);
        intent.putExtras(bundle2);
        this.c.startService(intent);
        ab.a(s.a, a, "end of processInternalGif");
    }

    public void onReceive(Context context, Intent intent) {
        this.c = context;
        try {
            b = QG.getInstance(context);
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey("type")) {
                this.a(bundle);
                return;
            }
            String string = intent.getAction();
            if (!this.a(string)) {
                return;
            }
            if ("notification_deleted".equals(string)) {
                int n2 = intent.getIntExtra(a.m, 0);
                GcmNotificationIntentService.b();
                NotificationIntentProcessor.a(context, n2);
                c.a(context);
                return;
            }
            ab.a(s.a, a, "deepLink::: " + intent.getStringExtra(a.o));
            if (intent.hasExtra(a.o)) {
                Uri uri = Uri.parse((String)intent.getStringExtra(a.o));
                ab.a(s.a, a, uri.getScheme() + " ::scheme " + uri.getScheme().equals("notify"));
                if (uri.getScheme().equals("notify")) {
                    this.b(intent);
                    return;
                }
            }
            this.a(intent);
        }
        catch (Exception var3_4) {
            ab.a(s.a, a, "Exception - %s", var3_4);
        }
    }

    private void a(Intent intent) {
        NotificationIntentProcessor.a(this.c, 281739);
        String string = intent.getAction();
        JSONObject jSONObject = new JSONObject();
        try {
            int n2 = intent.getIntExtra(a.m, 0);
            jSONObject.put(a.m, n2);
            b.setNotificationAndTime(a.w, n2);
            if (intent.hasExtra("actionId")) {
                jSONObject.put("actionId", (Object)intent.getStringExtra("actionId"));
            }
            b.logEvent(string, jSONObject);
            boolean bl = intent.getBooleanExtra("poll", false);
            if (intent.hasExtra("actionId") && bl) {
                return;
            }
        }
        catch (JSONException var4_5) {
            ab.a(s.a, a, "JSONException - %s ", new Object[]{var4_5});
        }
        String string2 = null;
        Bundle bundle = null;
        if (intent.hasExtra(a.o)) {
            string2 = intent.getStringExtra(a.o);
        }
        if (intent.hasExtra(a.n)) {
            bundle = intent.getBundleExtra(a.n);
        }
        this.a(string2, bundle);
    }

    private void a(String string, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (string != null) {
            ab.a(s.a, a, "%s - %s", a.o, string);
            Uri uri = Uri.parse((String)string);
            intent.setData(uri);
            if ("qg".equalsIgnoreCase(uri.getScheme()) && "quantumgraph.sdk".equalsIgnoreCase(uri.getAuthority()) && "/qgactivity".equalsIgnoreCase(uri.getPath()) && Build.VERSION.SDK_INT >= 11) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                for (String string2 : uri.getQueryParameterNames()) {
                    bundle.putString(string2, uri.getQueryParameter(string2));
                }
                ab.a(s.a, a.e, "Deeplink is : " + string);
                ab.a(s.a, a.e, "PackageName is : " + this.c.getPackageName());
                intent.setPackage(this.c.getPackageName());
            }
        } else {
            intent = this.c.getPackageManager().getLaunchIntentForPackage(this.c.getApplicationContext().getPackageName());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(131072);
        intent.addFlags(268435456);
        this.c.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        this.c.startActivity(intent);
    }

    private boolean a(String string) {
        ab.a(s.a, a, "Action - %s ", string);
        if (ab.a(string, "notification_browsed", "notification_clicked", "notification_deleted", "actionClicked")) {
            return true;
        }
        return false;
    }

    private void a(Uri uri) {
        int n2 = Integer.parseInt(uri.getQueryParameter("pos"));
        Intent intent = new Intent(this.c, (Class)GcmNotificationIntentService.class);
        intent.setAction(a.i);
        intent.putExtra("pos", n2);
        this.c.startService(intent);
    }

    @TargetApi(value=15)
    private void b(Intent intent) {
        Uri uri = Uri.parse((String)intent.getStringExtra(a.o));
        String string = uri.getHost();
        JSONObject jSONObject = new JSONObject();
        try {
            int n2 = intent.getIntExtra(a.m, 0);
            jSONObject.put(a.m, n2);
            ab.a(s.a, a, "deepLink::" + uri.toString());
            if ("click".equals(string)) {
                b.setNotificationAndTime(a.w, n2);
                boolean bl = uri.getBooleanQueryParameter("head", false);
                int n3 = Integer.parseInt(uri.getQueryParameter("pos"));
                jSONObject.put("actionId", n3);
                if (bl) {
                    jSONObject.put("actionId", 100);
                }
                b.logEvent("notification_clicked", jSONObject);
                String string2 = uri.getQueryParameter(a.o);
                if ("home".equals(string2)) {
                    string2 = null;
                }
                Bundle bundle = null;
                if (intent.hasExtra(a.n)) {
                    bundle = intent.getBundleExtra(a.n);
                }
                this.a(string2, bundle);
                ab.a(s.a, a, "%s - %s", a.o, string2);
            } else if ("next".equals(string) || "prev".equals(string)) {
                b.logEvent("notification_browsed", jSONObject);
                this.a(uri);
                ab.a(s.a, a, "%s - %s", new Object[]{a.o, uri});
            }
        }
        catch (JSONException var5_6) {
            ab.a(s.a, a, "JSONException - %s ", new Object[]{var5_6});
        }
    }
}

*/
