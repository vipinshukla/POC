/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.IntentService
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *//*

package com.fourtyseven.notificationpoc.notificationextra;

import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.quantumgraph.sdk.QG;
import com.quantumgraph.sdk.a;
import com.quantumgraph.sdk.aa;
import com.quantumgraph.sdk.ab;
import com.quantumgraph.sdk.b;
import com.quantumgraph.sdk.c;
import com.quantumgraph.sdk.d;
import com.quantumgraph.sdk.s;
import com.quantumgraph.sdk.t;
import com.quantumgraph.sdk.u;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GcmNotificationIntentService
extends IntentService {
    private static QG a;
    private static AsyncTask b;
    private static volatile boolean c;
    private static JSONArray d;
    private Context e;

    public GcmNotificationIntentService() {
        super("GcmNotificationIntentService");
    }

    public JSONArray a() {
        String string;
        if (d == null && (string = ab.b(this.e).getString(a.k, null)) != null) {
            d = new JSONArray(string);
        }
        return d;
    }

    public void a(JSONArray jSONArray) {
        String string = jSONArray.toString();
        ab.b(a.k, string, this.e);
        d = jSONArray;
    }

    public static boolean a(String string, Bundle bundle) {
        boolean bl = ab.a(string, a.g, a.i, a.j, a.R, a.l);
        if (!bl) {
            return true;
        }
        if (ab.a(string, a.l)) {
            if (!bundle.containsKey("message")) {
                ab.a(s.b, a.e, "Message processing halted: No field with key `message` in extras");
                return true;
            }
            String string2 = bundle.getString("message");
            JSONObject jSONObject = new JSONObject(string2);
            if (!jSONObject.optString("source").equalsIgnoreCase(a.l)) {
                ab.a(s.b, a.e, "Message processing halted: expected key `source` is missing");
                return true;
            }
        }
        return false;
    }

    protected void onHandleIntent(Intent intent) {
        this.g();
        String string = null;
        Bundle bundle = null;
        try {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            string = intent.getAction();
            if (GcmNotificationIntentService.a(string, bundle = intent.getExtras())) {
                return;
            }
            a = QG.getInstance(this.e, true);
        }
        catch (Exception var4_4) {
            ab.a(s.a, a.e, "Exception : %s", var4_4);
            return;
        }
        try {
            if (a.R.equalsIgnoreCase(string)) {
                this.e();
                return;
            }
            if (ab.b == null) {
                ab.a(s.a, a.e, "Inside GcmNotificationIntentService : scheduledFuture is null");
                ab.b = ab.d().scheduleAtFixedRate(GcmNotificationIntentService.a.batchedDataLogger, 0, 15, TimeUnit.SECONDS);
            }
            if (a.g.equalsIgnoreCase(string)) {
                this.d();
            } else if (a.i.equalsIgnoreCase(string)) {
                int n2 = bundle.getInt("pos");
                this.a(n2);
            } else if (a.j.equalsIgnoreCase(string)) {
                this.f();
            } else if (a.l.equalsIgnoreCase(string)) {
                this.a(bundle);
            }
        }
        catch (Exception var4_6) {
            ab.a(s.a, a.e, "Exception : %s", var4_6);
            a.logException(ab.a(var4_6));
        }
    }

    private boolean a(String string) {
        String string2 = aa.h(this.e);
        String[] arrstring = new String[]{null, "2g", "3g", "4g", "wifi", "unknown"};
        List<String> list = Arrays.asList(arrstring);
        if (string2 == null || string == null) {
            return true;
        }
        return list.indexOf(string2.toLowerCase()) >= list.indexOf(string.toLowerCase());
    }

    private void a(Bundle bundle) {
        String string;
        String string2 = bundle.getString("message");
        ab.a(s.b, a.e, "Message processing started");
        JSONObject jSONObject = new JSONObject(string2);
        if (!jSONObject.isNull("expiry") && ab.a(string = jSONObject.optString("type", ""), "basic", "banner", "carousel", "slider", "animation", "gif") && jSONObject.optBoolean("firstRun", true)) {
            u.a(this.e, jSONObject);
        }
        if ((string = jSONObject.optString("type")).equalsIgnoreCase(a.g)) {
            this.c(jSONObject);
        } else if (string.equalsIgnoreCase("settings")) {
            ab.a(a.Q, jSONObject.optBoolean(a.Q, true), this.e);
        } else if (!string.equalsIgnoreCase("inApp")) {
            this.a(jSONObject);
        }
        ab.a(s.b, a.e, "Message processing completed");
    }

    private void a(JSONObject jSONObject) {
        String string;
        GcmNotificationIntentService.b();
        this.k(jSONObject);
        String string2 = jSONObject.optString("networkType");
        boolean bl = this.a(string2);
        jSONObject.put("isCompatibleNetworkType", bl);
        if (!bl) {
            a.logException(ab.a(new Exception("Incompatible network type")));
            ab.a(s.b, a.e, "Incompatible network type");
            if (!jSONObject.optBoolean("showTextOnly", false)) {
                return;
            }
        }
        if ((string = jSONObject.optString("type")).equalsIgnoreCase("gif")) {
            this.d(jSONObject);
        } else if (string.equalsIgnoreCase("animation") || string.equalsIgnoreCase("internalGif")) {
            this.i(jSONObject);
        } else if (string.equalsIgnoreCase("slider") || string.equalsIgnoreCase("carousel")) {
            this.f(jSONObject);
        } else {
            this.e(jSONObject);
        }
    }

    private void d() {
        JSONObject jSONObject = ab.e(this.e);
        a.logUserDetails(jSONObject);
    }

    private void b(JSONObject jSONObject) {
        d d2;
        String string;
        JSONObject jSONObject2 = jSONObject.optJSONObject("inApp");
        if (jSONObject2.optBoolean("sendReceipt", false)) {
            d2 = new JSONObject().put(a.m, jSONObject2.optInt(a.m)).put("messageNo", jSONObject2.optInt("messageNo"));
            a.logEvent("qg_inapp_received", (JSONObject)d2);
        }
        d2 = d.a(this.getApplication());
        d2.a(jSONObject2);
        JSONObject jSONObject3 = jSONObject2.optJSONObject("whenCond");
        if (jSONObject3 != null && "app_launched".equalsIgnoreCase(string = jSONObject3.optString("eventName", ""))) {
            d2.a(string, null);
        }
    }

    */
/*
     * WARNING - Removed try catching itself - possible behaviour change.
     *//*

    private void e() {
        block8 : {
            long l2 = ab.a(this.getApplicationContext(), a.S, 0);
            String string = ab.b(this.e).getString(a.z, "");
            String string2 = Long.toString(ab.a(this.e));
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            try {
                URL uRL = new URL(String.format(a.T + "?versionNo=%s&appId=%s&userId=%s", l2, string, string2));
                httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection.connect();
                int n2 = httpURLConnection.getResponseCode();
                ab.a(s.a, a.e, "Response Code : " + httpURLConnection.getResponseCode());
                if (n2 == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String string3 = bufferedReader.readLine();
                    JSONObject jSONObject = new JSONObject(string3);
                    ab.a(a.S, jSONObject.optLong("versionNo", 0), this.getApplicationContext());
                    JSONArray jSONArray = jSONObject.optJSONArray("campaigns");
                    for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
                        this.b(jSONArray.getJSONObject(i2));
                    }
                    ab.a(s.a, a.e, "response is : %s", string3);
                    break block8;
                }
                ab.a(s.a, a.e, "InApp campaigns are not updated");
            }
            catch (Exception var7_7) {
                ab.a(s.a, a.e, "Exception in fetch/add inapp campaigns : %s", var7_7);
            }
            finally {
                ab.a(inputStream);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }
    }

    private void c(JSONObject jSONObject) {
        boolean bl;
        JSONObject jSONObject2 = ab.e(this.e);
        boolean bl2 = jSONObject.optBoolean("location", false);
        if (bl2) {
            try {
                JSONObject jSONObject3 = aa.c(this.e);
                if (jSONObject3 != null) {
                    jSONObject2.put("locn", (Object)jSONObject3);
                }
            }
            catch (JSONException var4_5) {
                ab.a(s.a, "Exception", "exception in fetching location details", new Object[]{var4_5});
            }
        }
        if (bl = jSONObject.optBoolean("installed_apps", false)) {
            JSONArray jSONArray = aa.a(this.e);
            ab.a(s.a, a.e, "Length of installed apps is %s", jSONArray.length());
            try {
                if (jSONArray.length() > 0) {
                    jSONObject2.put("installedApps", (Object)jSONArray);
                }
            }
            catch (JSONException var6_8) {
                ab.a(s.a, a.e, "Error parsing json : %s", new Object[]{var6_8});
            }
        }
        a.logUserDetails(jSONObject2);
    }

    private void f() {
        String string = ab.c(this.e);
        ab.b(this.e, string);
    }

    private void g() {
        this.e = this.getApplicationContext() == null ? this.getBaseContext() : this.getApplicationContext();
    }

    private void d(JSONObject jSONObject) {
        ab.a(s.a, a.e, "Start of setGifStart()");
        t t2 = new t(this.e, jSONObject.optString("type"));
        t2.a(jSONObject);
        if (jSONObject.optBoolean("firstRun", true)) {
            t2.b(jSONObject);
        }
        t2.a();
        ab.a(s.a, a.e, "End of setGifStart()");
    }

    private void e(JSONObject jSONObject) {
        ab.a(s.a, a.e, "Start of setBasicOrBanner()");
        t t2 = new t(this.e, jSONObject.optString("type"));
        t2.a(jSONObject);
        t2.a();
        ab.a(s.a, a.e, "End of setBasicOrBanner()");
    }

    private void f(JSONObject jSONObject) {
        this.a(this.g(jSONObject));
        ab.a(s.b, a.e, d.toString());
        t t2 = new t(this.e, jSONObject.optString("type"));
        t2.a(this.a().getJSONObject(0));
        t2.b(jSONObject);
        t2.a();
    }

    private JSONArray g(JSONObject jSONObject) {
        JSONArray jSONArray = new JSONArray();
        String string = jSONObject.optString("type");
        JSONArray jSONArray2 = jSONObject.optJSONArray(string);
        int n2 = string.equalsIgnoreCase("slider") ? 1 : 2;
        for (int i2 = 0; i2 < jSONArray2.length(); ++i2) {
            JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
            jSONObject2.put("pos", i2);
            String string2 = "notify://click?deepLink=%s&pos=%s&head=true";
            String string3 = jSONObject2.optString(a.o, "home");
            string3 = Uri.encode((String)string3);
            string2 = String.format(string2, string3, i2);
            jSONObject2.put(a.o, (Object)string2);
            JSONArray jSONArray3 = new JSONArray();
            for (int i3 = 0; i3 < n2; ++i3) {
                int n3 = i2 + i3;
                JSONObject jSONObject3 = jSONArray2.getJSONObject(n3 %= jSONArray2.length());
                String string4 = jSONObject3.optString("image");
                c.e(this.e, string4);
                jSONObject3.put("pos", n3);
                jSONArray3.put(i3, (Object)jSONObject3);
            }
            jSONObject2.put(string, (Object)jSONArray3);
            jSONObject2.put("isCarouselV2", this.h(jSONObject));
            if (jSONObject.has("iconImage")) {
                jSONObject2.put("iconImage", (Object)jSONObject.getString("iconImage"));
            }
            jSONArray.put((Object)jSONObject2);
        }
        ab.a(s.a, a.e, "JSONArray :%s ", jSONArray.toString());
        return jSONArray;
    }

    private boolean h(JSONObject jSONObject) {
        String string = jSONObject.optString("type");
        if (!string.equalsIgnoreCase("carousel")) {
            return false;
        }
        JSONArray jSONArray = jSONObject.optJSONArray("carousel");
        for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
            if (jSONObject2.has("title") || jSONObject2.has("message")) continue;
            return false;
        }
        return true;
    }

    protected void a(int n2) {
        JSONArray jSONArray = this.a();
        if (jSONArray == null) {
            ab.a(s.a, a.e, "notificationList is empty ");
            return;
        }
        n2 += jSONArray.length();
        JSONObject jSONObject = jSONArray.getJSONObject(n2 %= jSONArray.length());
        String string = jSONObject.optString("type");
        ab.a(s.a, a.e, jSONObject.toString());
        t t2 = new t(this.e, string);
        t2.a(jSONObject);
        t2.a();
    }

    private void i(JSONObject jSONObject) {
        String string = jSONObject.optString("type");
        if (!c) {
            b = new b(this, string);
            c = true;
            b.execute((Object[])new JSONObject[]{jSONObject});
        } else {
            ab.a(s.a, a.e, "thread is already running");
        }
    }

    protected static void b() {
        c = false;
    }

    private List j(JSONObject jSONObject) {
        String string = jSONObject.optString("type");
        JSONObject jSONObject2 = jSONObject.optJSONObject(string);
        JSONArray jSONArray = jSONObject2.optJSONArray("images");
        ArrayList<t> arrayList = new ArrayList<t>(jSONArray.length());
        for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
            t t2 = new t(this.e, string);
            jSONObject.put("contentImageUrl", jSONArray.get(i2));
            t2.a(jSONObject);
            arrayList.add(t2);
        }
        return arrayList;
    }

    private void k(JSONObject jSONObject) {
        boolean bl = jSONObject.optBoolean("sendReceipt", true);
        boolean bl2 = jSONObject.optBoolean("firstRun", true);
        int n2 = jSONObject.getInt(a.m);
        a.setNotificationAndTime(a.t, n2);
        if (bl && bl2) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(a.m, n2);
            a.logEvent("notification_received", jSONObject2);
        }
    }

    static */
/* synthetic *//*
 List a(GcmNotificationIntentService gcmNotificationIntentService, JSONObject jSONObject) {
        return gcmNotificationIntentService.j(jSONObject);
    }

    static */
/* synthetic *//*
 boolean c() {
        return c;
    }

    static */
/* synthetic *//*
 Context a(GcmNotificationIntentService gcmNotificationIntentService) {
        return gcmNotificationIntentService.e;
    }

    static */
/* synthetic *//*
 boolean a(boolean bl) {
        c = bl;
        return c;
    }

    static {
        c = false;
    }
}

*/
