package com.fourtyseven.notificationpoc;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by dell on 9/29/2016.
 */
public class App extends Application {
    private static final String TAG = "ObjectPreference";
    private static ComplexPreferences complexPrefenreces = null;
    private static LruCache<String, Bitmap> mMemoryCache;

    @Override
    public void onCreate() {
        super.onCreate();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        complexPrefenreces = ComplexPreferences.getComplexPreferences(getBaseContext(), "abhan", MODE_PRIVATE);
        android.util.Log.i(TAG, "Preference Created.");
    }

    public static ComplexPreferences getComplexPreference() {
        if (complexPrefenreces != null) {
            return complexPrefenreces;
        }
        return null;
    }


    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}