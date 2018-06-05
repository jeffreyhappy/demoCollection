package com.fei.downloaddemo.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fei.downloaddemo.IDownloadCallback;
import com.fei.downloaddemo.IDownloadService;


/**
 * 创建日期：2018/6/4 on 15:14
 * 描述:
 * 作者:Li
 */

public class DownloadService extends Service {
    public static final String PARAM_URL = "url";
    private RemoteCallbackList<IDownloadCallback> mCallbackList = new RemoteCallbackList<>();

    private final String TAG = DownloadService.class.getSimpleName();

    private IBinder binder = new IDownloadService.Stub() {
        @Override
        public void addListener(IDownloadCallback callback) throws RemoteException {
            mCallbackList.register(callback);

        }

        @Override
        public void removeListener(IDownloadCallback callback) throws RemoteException {
            mCallbackList.unregister(callback);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(PARAM_URL);
        DownloadObserver.getInstance().registerListener(mCallbackList);
        DownloadManager.getInstance().addNewJob(url);
        return START_NOT_STICKY;
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        DownloadObserver.getInstance().registerListener(mCallbackList);
        return binder;
    }




//    private void startListener(){
//        DownloadObserver.getInstance().registerListener(new DownloadListener() {
//            @Override
//            public void onDownloadPrepare(String key) {
//                for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
//                    try {
//                        mCallbackList.getBroadcastItem(i).onDownloadPrepare(key);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onDownloadRunning(String key, int progress) {
//                Log.d(TAG,"onDownloadRunning " + key + " " + progress);
//                for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
//                    try {
//                        mCallbackList.getBroadcastItem(i).onDownloadRunning(key,progress);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onDownloadDone(String key) {
//                Log.d(TAG,"onDownloadDone " + key );
//                for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
//                    try {
//                        mCallbackList.getBroadcastItem(i).onDownloadDone(key);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onDownloadError(String key, int errorCode) {
//                Log.d(TAG,"onDownloadError " + key + " " + errorCode );
//                for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
//                    try {
//                        mCallbackList.getBroadcastItem(i).onDownloadError(key,errorCode);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });
//    }


}
