package com.jeffrey.demo.retrofitdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by li on 2015/12/3.
 */
public class DeviceUtils {

    private static boolean  checkPermission(Context context, String permission){
        if (ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    permission)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//                int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{permission},
//                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
            return false;
        }
        return true;

    }
    /**
     * 获取设备的IMEI
     * @return
     */
    public static String getImei(Activity context){

        String deviceID = null;
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)){
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceID =  tm.getDeviceId();
        }
        if (TextUtils.isEmpty(deviceID)){
            deviceID = "default" + System.currentTimeMillis();
        }
        return deviceID;
    }

    /**
     * 获取应用的包信息
     * @param context
     * @return   null 没有获取到
     */
    public static PackageInfo getPackageInfo(Context context){
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return pi;
    }

    public static String getVersionName(Context context){
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null){
            return packageInfo.versionName;
        }
        return "";
    }


    public static int  getVersionCode(Context context){
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null){
            return packageInfo.versionCode;
        }
        return 0;
    }


    /**
     * 获取apk的渠道号
     * @param context
     * @return  default-没拿到
     */
    public static String getAppChannel(Context context)  {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "default";
        }


        String mainfestChannel =" default";
        if (appInfo.metaData != null){
            Object o = appInfo.metaData.getString("my_channel","default");
            mainfestChannel = o.toString();
        }

        return mainfestChannel;
    }

    /**
     * 获取设备的mac地址
     * @param context
     * @return
     */
    public static String getMac(Context context){
        String mac = "not find";
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null){
            if(wifi.getConnectionInfo()!= null){
               mac = wifi.getConnectionInfo().getMacAddress();
            }
        }
        return mac;
    }


    public static int getDeviceWidth(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point point  = new Point();
        wm.getDefaultDisplay().getSize(point);
        Log.d("fei","getDeviceWidth  x = " + point.x);
        return point.x;
    }


    public static int getDeviceHeight(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
//        Point  point  = new Point();
//        wm.getDefaultDisplay().getSize(point);
        Log.d("fei", "getDeviceHeight  x = " + dm.heightPixels);
        return dm.heightPixels;
    }

}
