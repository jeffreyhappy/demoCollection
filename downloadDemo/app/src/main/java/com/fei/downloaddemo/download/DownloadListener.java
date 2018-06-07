package com.fei.downloaddemo.download;

public interface DownloadListener {
    void onDownloadPrepare(String key);
    void onDownloadRunning(String key,int progress,boolean first);
    void onDownloadDone(String key);
    void onDownloadError(String key,int errorCode);
    void onDownloadFileSize(String key,int size);
}
