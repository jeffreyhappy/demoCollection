package com.jeffrey.demo.avatarchoose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * Android N 需要通过FileProvider才能访问文件
     *
     * @param context
     * @param originUri
     * @return
     */
    public static Uri getUriForN(Context context, String originUri) {
        Uri parseUri = Uri.parse(originUri);
        File file = new File(parseUri.getPath());
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            uri = FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID + ".fileprovider", file);
        } else {
            uri = Uri.parse(originUri);
        }
        return uri;
    }

//    public static String fromUri(Context context,Uri uri){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.(context,BuildConfig.APPLICATION_ID + ".fileprovider", file);
//        } else {
//            uri = Uri.parse(originUri);
//        }
//    }

    public static Bitmap decodeFile(String filePath) throws IOException {
        Bitmap b = null;
        int IMAGE_MAX_SIZE = 600;

        File f = new File(filePath);
        if (f == null){
            return null;
        }
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = new FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();

        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        fis = new FileInputStream(f);
        b = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();
        return b;
    }




}
