package com.fei.downloaddemo.download;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建日期：2018/6/4 on 15:18
 * 描述:
 * 作者:Li
 */

public class DownloadManager {
    private static final String TAG = DownloadManager.class.getSimpleName();
    private static DownloadManager manager;

    private ExecutorService mExeccutor =  Executors.newFixedThreadPool(3);


    public static DownloadManager getInstance(){
        if (manager == null){
            synchronized (DownloadManager.class){
                if (manager == null){
                    manager = new DownloadManager();
                }
            }
        }
        return manager;
    }


    public void addNewJob(String url){
        //1 当前是否正在下载
        //2 是否已经下载完成
        //3 是否暂停

        //现在就直接下载
        Log.d(TAG,"addNewJob url =  " + url);

        DownloadObj downloadObj = new DownloadObj();
        downloadObj.setUrl(url);
        downloadObj.setStatus(DownloadCode.DownloadStatus.NONE);
        downloadObj.setFilePath(getFilePath(url));

        doDownload(downloadObj);
        Log.d(TAG,"addNewJob filePath = " + downloadObj.getFilePath());
    }



    public String getFilePath(String url){
        String fileName ;
        String[] strings = url.split("/");
        String lastString = strings[strings.length -1];
        if (lastString.contains(".apk")){
            fileName = lastString;
        }else {
            fileName = lastString+".apk";
        }
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return new File(downloadDir,fileName).getPath();
    }


    private void doDownload(DownloadObj downloadObj){
        DownloadWorker worker = new DownloadWorker(downloadObj,DownloadObserver.getInstance());
        mExeccutor.execute(worker);
    }



}
