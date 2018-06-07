package com.fei.downloaddemo.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

@Entity(tableName = DownloadHistory.TABLE_NAME)
public class DownloadHistory {

    public static final String TABLE_NAME = "tb_download_history";

    @PrimaryKey
    @NonNull
    private String url;
    private int status;
    private int total_size;


    public static DownloadHistory fromContentValue(ContentValues cv){
        DownloadHistory downloadHistory = new DownloadHistory();
        downloadHistory.url = cv.getAsString("url");
        downloadHistory.status = cv.getAsInteger("status");
        if (cv.containsKey("total_size")){
            downloadHistory.total_size = cv.getAsInteger("total_size");
        }
        return downloadHistory;
    }

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

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }
}
