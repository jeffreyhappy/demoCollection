package com.fei.downloaddemo.download;

public interface DownloadListener {
    void onDownloadPrepare(String key);
    void onDownloadRunning(String key,int progress);
    void onDownloadDone(String key);
    void onDownloadError(String key,int errorCode);
}
