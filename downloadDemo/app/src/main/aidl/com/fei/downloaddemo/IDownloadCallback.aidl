package com.fei.downloaddemo;


interface IDownloadCallback {
    void onDownloadPrepare(String key);
    void onDownloadFileSize(String key ,int size);
    /*
     first: 下载中的第一个成功块，用来告诉前端，开始下载了
    */
    void onDownloadRunning(String key,int progress,boolean first);
    void onDownloadDone(String key);
    void onDownloadError(String key,int errorCode);
    String getKey();
}
