package com.fei.downloaddemo;


interface IDownloadCallback {
    void onDownloadPrepare(String key);
    void onDownloadRunning(String key,int progress);
    void onDownloadDone(String key);
    void onDownloadError(String key,int errorCode);
    String getKey();
}
