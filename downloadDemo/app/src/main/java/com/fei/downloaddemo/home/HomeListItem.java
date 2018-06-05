package com.fei.downloaddemo.home;

/**
 * 创建日期：2018/6/4 on 11:38
 * 描述:
 * 作者:Li
 */

public class HomeListItem {
    private String name;
    private String size;
    private String url;
    private int    status;
    private int    progress;


    public HomeListItem(String name, String size, String url) {
        this.name = name;
        this.size = size;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
