package com.fei.downloaddemo.home;

public interface HomeView {
    void onItemProgress(String key ,int progress);
    void onItemPrepare(String key);
    void onItemDone(String key);
}
