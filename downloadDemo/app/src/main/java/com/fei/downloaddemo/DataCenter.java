package com.fei.downloaddemo;

import com.fei.downloaddemo.home.HomeListItem;

import java.util.ArrayList;

/**
 * 创建日期：2018/6/4 on 11:38
 * 描述:
 * 作者:Li
 */

public class DataCenter {

    public  static ArrayList<HomeListItem> getHomeListItems(){
        HomeListItem item = new HomeListItem("QQ","14.2M","http://app.mianfeiapp.com.cn/2015/09/09/955efcce71d654.apk");
        HomeListItem item2 = new HomeListItem("开心消消乐","97.44M","http://app.mianfeiapp.com.cn/38/5719e7326894c_1.apk");
        HomeListItem item3 = new HomeListItem("全民枪战","273.37M","http://app.mianfeiapp.com.cn/6c/570460c66a2a8.apk");
        ArrayList<HomeListItem> list = new ArrayList<>();
        list.add(item);
        list.add(item2);
        list.add(item3);
        return list;
    }
}
