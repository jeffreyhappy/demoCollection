package com.fei.downloaddemo.download;

/**
 * 创建日期：2018/6/4 on 15:23
 * 描述:
 * 作者:Li
 */

public class DownloadObj {
    private String url;
    private int    status;
    private String filePath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
