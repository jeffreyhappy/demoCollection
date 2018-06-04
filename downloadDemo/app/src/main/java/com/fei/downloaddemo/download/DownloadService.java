package com.fei.downloaddemo.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import aidl.IDownloadListener;

/**
 * 创建日期：2018/6/4 on 15:14
 * 描述:
 * 作者:Li
 */

public class DownloadService extends Service {
    public static final String PARAM_URL = "url";

    private IBinder binder = new IDownloadListener.Stub() {

        @Override
        public void startDownload(String url) throws RemoteException {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(PARAM_URL);
        DownloadManager.getInstance().addNewJob(url);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
