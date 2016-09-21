package com.jeffrey.demo.okhttpdemo;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * 基于系统的DownloadManager的实现
 * Created by Li on 2016/9/21.
 */

public class DownloadHelperSys {

    public static final int DOWNLOAD_STATUS_NEED_LOAD = 1;
    public static final int DOWNLOAD_STATUS_RUNNING = 2;
    public static final int DOWNLOAD_STATUS_LOADED = 3;


    private static final String apkUrl = "http://192.168.0.160:3456/myapp.apk";
    private static final String YOUR_APP_NAME = "appName";
    private static final String APP_NEW_VERSION = "1.1.1";

    public static void downloadBySys(Context context){
        // parse url
        Uri mUri = Uri.parse(apkUrl);

        int status = getDownloadStatus(context,targetFileName());
        if (status == DOWNLOAD_STATUS_LOADED){
            Log.d("DownloadHelperSys","DownloadHelperSys DOWNLOAD_STATUS_LOADED");
            return ;
        }
        // create request
        DownloadManager.Request r = new DownloadManager.Request(mUri);

        // set request property
        String apkName =targetFileName();
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // create manager
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // key code, set mine type
        r.setMimeType("application/vnd.android.package-archive");

        // add to queue
        dm.enqueue(r);
    }

    /**
     * 判断当前版本文件下载状态
     *
     * @param context
     * @param apkName
     * @return
     */
    private static int getDownloadStatus(Context context, String apkName) {
        DownloadManager.Query query = new DownloadManager.Query();
        DownloadManager dm = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
        Cursor c = dm.query(query);

        if (!c.moveToFirst()) {
            // 无下载内容
            return DOWNLOAD_STATUS_NEED_LOAD;
        }

        do {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
            if (title.equals(apkName)) {
                // 如果下载列表中文件是当前版本应用，则继续判断下载状态
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    // 如果已经下载，返回状态，同时直接提示安装
                    String uri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    return DOWNLOAD_STATUS_LOADED;
                } else if (status == DownloadManager.STATUS_RUNNING
                        || status == DownloadManager.STATUS_PAUSED
                        || status == DownloadManager.STATUS_PENDING) {
                    return DOWNLOAD_STATUS_RUNNING;
                } else {
                    // 失败也视为可以再次下载
                    return DOWNLOAD_STATUS_NEED_LOAD;
                }
            }
        } while (c.moveToNext());

        return DOWNLOAD_STATUS_NEED_LOAD;
    }

    /**
     * 根据下载队列id获取下载Uri
     *
     * @param enqueueId
     * @return null-获取不到
     */
    public static Uri getDownloadUriById(Context context, long enqueueId) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(enqueueId);
        DownloadManager dm = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
        Cursor c = dm.query(query);
        if (c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                String uri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                return Uri.parse(uri);
            }
        }
        return null;
    }
    private static String targetFileName(){
        return YOUR_APP_NAME + APP_NEW_VERSION + ".apk";
    }
}
