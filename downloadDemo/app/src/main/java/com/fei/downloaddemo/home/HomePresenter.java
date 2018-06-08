package com.fei.downloaddemo.home;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.fei.downloaddemo.IDownloadCallback;
import com.fei.downloaddemo.data.DownloadClientHelper;
import com.fei.downloaddemo.download.DownloadCode;

public class HomePresenter {
    private static final  String TAG = HomePresenter.class.getSimpleName();
    private IDownloadCallback mCallback;
    private HomeView  mView;
    private DownloadClientHelper mDbHelper;
    public HomePresenter(HomeView homeView, Context context){
        this.mView = homeView;
        this.mDbHelper = new DownloadClientHelper(context);
        mCallback = new IDownloadCallback.Stub() {
            @Override
            public void onDownloadPrepare(String key) throws RemoteException {
                mView.onItemPrepare(key);
                mDbHelper.insertOneHistory(key, DownloadCode.DownloadStatus.PREPARE);
                mDbHelper.printAllRecord();
            }

            @Override
            public void onDownloadFileSize(String key ,int size) throws RemoteException {
                Log.d(TAG,"onDownloadFileSize " + key + " " + size);
                mDbHelper.updateTotalSize(key,size);
                mDbHelper.printAllRecord();
            }

            @Override
            public void onDownloadRunning(String key, int progress,boolean first) throws RemoteException {
                mView.onItemProgress(key,progress);
                if (first){
                    mDbHelper.updateStatus(key, DownloadCode.DownloadStatus.DOWNLOAD);
                }
            }

            @Override
            public void onDownloadDone(String key) throws RemoteException {
                mView.onItemDone(key);
                mDbHelper.updateStatus(key, DownloadCode.DownloadStatus.FINISH);
            }

            @Override
            public void onDownloadError(String key, int errorCode) throws RemoteException {

                mDbHelper.updateStatus(key, DownloadCode.DownloadStatus.ERROR);
            }

            @Override
            public String getKey() throws RemoteException {
                return null;
            }
        };
    }


    public IDownloadCallback getCallback() {
        return mCallback;
    }
}
