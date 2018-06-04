package com.fei.downloaddemo;

import android.view.View;

/**
 * 创建日期：2018/6/4 on 14:29
 * 描述:
 * 作者:Li
 */

public interface ItemClickListener{
    void onItemClick(View view , int position);
    void onChildItemClick(View view ,int position);
    int[] listenChildID();
}