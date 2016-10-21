/*
 * Decompiled with CFR 0_115.
 *
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *//*

package com.fourtyseven.notificationpoc.notificationextra;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

//
public class BitmapCreator  {
    private static HashMap a = new HashMap();

    public static File getFile(Context context, String string) {
        String string2 = "mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(string2 + File.separator + string);
    }

    private static synchronized void b(Context context) {
        File file = getFile(context, "qgsdk");
        String[] arrstring = file.list();
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            File file2 = new File(file.getAbsolutePath() + File.separator + arrstring[i2]);
            file2.delete();
        }
    }

    protected static void a(Context context) {
        a = new HashMap();
        BitmapCreator.b(context);
    }

    private static Bitmap a(String string) {
        byte[] arrby = c(string);
        Bitmap bitmap = null;
        for (int i2 = 1; i2 <= 64; i2 *= 2) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = i2;
                bitmap = BitmapFactory.decodeStream((InputStream)byteArrayInputStream, (Rect)null, (BitmapFactory.Options)options);
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            catch (OutOfMemoryError var4_4) {
              //  ab.a(s.a, "ImageUtility", "oom trying again : " + i2);
                continue;
            }
        }
       */
/* ab.a(s.a, "ImageUtility", "bmp height : " + bitmap.getHeight());
        ab.a(s.a, "ImageUtility", "bmp width : " + bitmap.getWidth());*//*

        if (bitmap == null || bitmap.getHeight() == 0 || bitmap.getWidth() == 0) {

        }
        return bitmap;
    }

    protected static synchronized Bitmap a(Context context, String string, String string2) {
        if (string == null || string.isEmpty()) {
            try {
                throw new IOException("invalid sound url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string3 = string.replaceAll("[^A-Za-z0-9]", "");
        //ab.a(s.a, "ImageUtility", "name: " + string3);
        File file = getFile(context, string2);
        File file2 = new File(file.getAbsolutePath() + File.separator + string3);
        if (!file2.getParentFile().exists() && !file2.getParentFile().mkdir()) {
           // ab.a(s.a, "ImageUtility", "Invalid directory: loading image in to memory");
            return BitmapCreator.a(string);
        }
        Bitmap bitmap = null;
        if (file2.exists()) {
            for (int i2 = 1; i2 <= 64; i2 *= 2) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = i2;
                    bitmap = BitmapFactory.decodeStream((InputStream)fileInputStream, (Rect)null, (BitmapFactory.Options)options);
                   */
/* ab.a(s.a, "ImageUtility", "successful decoding : " + i2);
                    ab.a(s.a, "ImageUtility", "successful decoding height: " + bitmap.getHeight());
                    ab.a(s.a, "ImageUtility", "successful decoding width: " + bitmap.getWidth());*//*

                    fileInputStream.close();
                    break;
                }
                catch (Exception var8_9) {
                   // ab.a(s.a, "ImageUtility", "oom trying again : " + i2);
                    continue;
                }
            }
           */
/* ab.a(s.a, "ImageUtility", "successful decoding img height : " + bitmap.getHeight());
            ab.a(s.a, "ImageUtility", "successful decoding img width: " + bitmap.getWidth());*//*

            return bitmap;
        }
        Closeable closeable = null;
        try {
            bitmap = BitmapCreator.a(string);
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
            Bitmap bitmap2 = bitmap;
            return bitmap2;
        }
        catch (Exception var8_12) {
           */
/* ab.a(s.a, "ImageUtility", "Error ", var8_12);
            try {
                throw new IOException(var8_12);
            } catch (IOException e) {
                e.printStackTrace();
            }*//*

        }
        finally {
           // ab.a(closeable);
        }
        return bitmap;
    }

    protected static Bitmap b(Context context, String string) {
        return BitmapCreator.a(context, string, "qgsdk");
    }

    protected static void c(Context context, String string) {
        BitmapCreator.b(context, string, "qgsdkres");
    }

    protected static synchronized void b(Context context, String string, String string2) {
        if (string == null || string.isEmpty()) {
            try {
                throw new IOException("invalid sound url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string3 = string.replaceAll("[^A-Za-z0-9]", "");
        //ab.a(s.a, "ImageUtility", "name: " + string3);
        File file = BitmapCreator.getFile(context, string2);
        File file2 = new File(file.getAbsolutePath() + File.separator + string3);
        if (!file2.getParentFile().exists() && !file2.getParentFile().mkdir()) {
          //  ab.a(s.a, "ImageUtility", "Invalid directory: loading sound in to memory");
        }
        if (file2.exists()) {
           // ab.a(s.a, "ImageUtility", "dskCache: " + string);
            return;
        }
        Closeable closeable = null;
        try {
            int n2;
            byte[] arrby = c(string);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] arrby2 = new byte[8192];
            while ((n2 = byteArrayInputStream.read(arrby2)) != -1) {
                fileOutputStream.write(arrby2, 0, n2);
            }
            byteArrayInputStream.close();
            fileOutputStream.close();
        }
        catch (Exception var7_8) {
            //ab.a(s.a, "ImageUtility", "Error ", var7_8);
            //throw new IOException(var7_8);
        }
        finally {
            //ab.a(closeable);
        }
    }
    protected static byte[] c(String var0) {
        URL var1 = null;
        try {
            var1 = new URL(var0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection var2 = null;
        try {
            var2 = (HttpURLConnection)var1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double var3 = 0.5D;
        double var5 = 0.5D;
        double var7 = 1.5D;
        Random var15 = new Random();
        int var16 = 0;
        byte var17 = 3;
        ByteArrayOutputStream var18 = null;

        do {
            try {
                var2.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int var19 = 0;
            try {
                var19 = var2.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(var19 == 200) {
                InputStream var20 = null;
                try {
                    var20 = var2.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var18 = new ByteArrayOutputStream();
                byte[] var21 = new byte[8192];
                boolean var22 = false;

                int var24;
                try {
                    while((var24 = var20.read(var21)) != -1) {
                        var18.write(var21, 0, var24);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    var18.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            if(500 <= var19 && var19 < 600) {
                double var9 = var3 * (1.0D - var5);
                double var11 = var3 * (1.0D + var5);
                long var13 = (long)((var9 + (var11 - var9) * var15.nextDouble()) * 1000.0D);

                try {
                    Thread.sleep(var13);
                } catch (Exception var23) {
                    throw new RuntimeException("Thread Interrupted");
                }
                var3 *= var7;
                ++var16;
                var2.disconnect();
            }
        } while(var16 < var17);

        return var18 == null?(new ByteArrayOutputStream()).toByteArray():var18.toByteArray();
    }

    protected static String d(Context context, String string) {
        if (string == null || string.isEmpty()) {
            try {
                throw new IOException("invalid sound url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string2 = string.replaceAll("[^A-Za-z0-9]", "");
        //ab.a(s.a, "ImageUtility", "name: " + string2);
        File file = BitmapCreator.getFile(context, "qgsdkres");
        return file.getAbsolutePath() + File.separator + string2;
    }

    public static Bitmap c(Context context, String string, String string2) {
        String string3 = string.replaceAll("[^A-Za-z0-9]", "");
        File file = BitmapCreator.getFile(context, string2);
        File file2 = new File(file.getAbsolutePath() + File.separator + string3);
        if (!file2.getParentFile().exists() && !file2.mkdir()) {
          //  ab.a(s.a, "ImageUtility", "Invalid directory: loading image in to memory");
        }
        Bitmap bitmap = BitmapFactory.decodeFile((String)file2.getAbsolutePath());
       // ab.a(s.a, "ImageUtility", "dskCache: " + string);
        return bitmap;
    }

    protected static Bitmap e(Context context, String string) {
        Bitmap bitmap;
        if (!a.containsKey(string)) {
            bitmap = BitmapCreator.b(context, string);
            //ab.a(s.a, "ImageUtility", "noCache: " + string);
            a.put(string, bitmap);
        } else {
           // ab.a(s.a, "ImageUtility", "memCache: " + string);
            bitmap = (Bitmap)a.get(string);
        }
        return bitmap;
    }

    protected static Bitmap a(Bitmap bitmap, Context context) {
        if (Build.VERSION.SDK_INT <= 19) {
            Resources resources = context.getResources();
            int n2 = (int)TypedValue.applyDimension((int)1, (float)64.0f, (DisplayMetrics)resources.getDisplayMetrics());
            return BitmapCreator.b(bitmap, n2, n2);
        }
        return bitmap;
    }

    protected static Bitmap a(Context context, String string, Float f2) {
        String string2 = string + f2;
        if (a.containsKey(string2)) {
           // ab.a(s.a, "ImageUtility", "from cache " + string);
            return (Bitmap)a.get(string2);
        }
        Bitmap bitmap = BitmapCreator.b(context, string);
        bitmap = BitmapCreator.a(bitmap, f2.floatValue());
        a.put(string2, bitmap);
        return bitmap;
    }

    protected static Bitmap a(Context context, String string, Float f2, String string2) {
        String string3 = string + f2;
        if (a.containsKey(string3)) {
            //ab.a(s.a, "ImageUtility", "from cache " + string);
            return (Bitmap)a.get(string3);
        }
        Bitmap bitmap = BitmapCreator.a(context, string, string2);
        bitmap = BitmapCreator.a(bitmap, f2.floatValue());
        a.put(string3, bitmap);
        return bitmap;
    }

    protected static Bitmap a(Bitmap bitmap, float f2) {
        int n2;
        int n3;
        int n4 = bitmap.getHeight();
        int n5 = bitmap.getWidth();
        float f3 = (float)n5 / (float)n4;
       // ab.a(s.a, "ImageUtility", "Aspect ratios required: %s present: %s", Float.valueOf(f2), Float.valueOf(f3));
       // ab.a(s.a, "ImageUtility", "image width: %s height: %s", n5, n4);
        if (f2 == f3) {
            return bitmap;
        }
        if ((float)n4 * f2 > (float)n5) {
            n2 = (int)(f2 * (float)n4);
            n3 = n4;
        } else {
            n2 = n5;
            n3 = (int)((float)n5 / f2);
        }
      //  ab.a(s.a, "ImageUtility", "final width: %s height: %s", n2, n3);
        return BitmapCreator.a(bitmap, n2, n3);
    }

    private static Bitmap a(Bitmap bitmap, int n2, int n3) {
        try {
            Bitmap bitmap2 = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            int n4 = 0;
            int n5 = 0;
            if (bitmap.getWidth() < n2) {
                n4 = n2 - bitmap.getWidth();
            }
            if (bitmap.getHeight() < n3) {
                n5 = n3 - bitmap.getHeight();
            }
            canvas.drawBitmap(bitmap, (float)(n4 / 2), (float)(n5 / 2), null);
            return bitmap2;
        }
        catch (OutOfMemoryError var3_5) {
            bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)(bitmap.getWidth() / 2), (int)(bitmap.getHeight() / 2), (boolean)true);
            return BitmapCreator.a(bitmap, n2 / 2, n3 / 2);
        }
    }

   */
/* private static Bitmap b(Bitmap bitmap, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7 = bitmap.getHeight();
        if (n7 > (n6 = bitmap.getWidth())) {
            n4 = n6 * n3 / n7;
            n5 = n3;
            if (n4 > n2) {
                n4 = n2;
                n5 = n7 * n2 / n6;
            }
        } else {
            n4 = n2;
            n5 = n7 * n2 / n6;
            if (n5 > n3) {
                n5 = n3;
                n4 = n6 * n3 / n7;
            }
        }
        bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n4, (int)n5, (boolean)true);
        n4 = n4 > n6 ? n6 : n4;
        n5 = n5 > n7 ? n7 : n5;
        bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n4, (int)n5, (boolean)true);
        return BitmapCreator.a(bitmap, n2, n3);
    }*//*


}
*/
