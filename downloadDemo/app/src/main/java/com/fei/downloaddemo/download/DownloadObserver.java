package com.fei.downloaddemo.download;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.fei.downloaddemo.IDownloadCallback;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class DownloadObserver implements DownloadListener {

    private final String TAG = DownloadObserver.class.getSimpleName();
    private static DownloadObserver observer;
//    private ConcurrentHashMap<String,DownloadNotifyObj> mDownloadNotifyInfo =  new ConcurrentHashMap<>();
//    private ArrayList<DownloadListener> mListeners = new ArrayList<>();
    private RemoteCallbackList<IDownloadCallback> mCallbackList;
    public static DownloadObserver getInstance(){
        if (observer == null){
            synchronized (DownloadObserver.class){
                if (observer == null){
                    observer = new DownloadObserver();
                }
            }
        }
        return observer;
    }

    public void registerListener(RemoteCallbackList<IDownloadCallback> callback){
        mCallbackList = callback;
    }

    public void unregisterListener(){
        mCallbackList = null;
    }


    public void onDownloadPrepare(String key) {


        mCallbackList.beginBroadcast();
        for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
            try {

                mCallbackList.getBroadcastItem(i).onDownloadPrepare(key);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();
    }

    @Override
    public void onDownloadRunning(String key, int progress,boolean first) {
        mCallbackList.beginBroadcast();
        for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
            try {
                mCallbackList.getBroadcastItem(i).onDownloadRunning(key,progress,first);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();
    }

    @Override
    public void onDownloadDone(String key) {
        mCallbackList.beginBroadcast();
        for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
            try {
                mCallbackList.getBroadcastItem(i).onDownloadDone(key);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();
    }

    @Override
    public void onDownloadError(String key, int errorCode) {
        mCallbackList.beginBroadcast();
        for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
            try {
                mCallbackList.getBroadcastItem(i).onDownloadError(key,errorCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();

    }

    @Override
    public void onDownloadFileSize(String key, int size) {
        mCallbackList.beginBroadcast();
        for (int i = 0 ;i < mCallbackList.getRegisteredCallbackCount();i++){
            try {
                mCallbackList.getBroadcastItem(i).onDownloadFileSize(key,size);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();
    }


//    private DownloadListener findListener(String key){
//        for ()
//    }
}
