package com.example.administrator.popupwindow;

import android.view.ViewGroup;

/**
 * Created by Li on 2017/2/16.
 */

public interface AlertViewContentInterface {


    /**
     * getContentLayoutId()中布局和数据绑定
     * @param viewGroup
     */
    void bindContent(ViewGroup viewGroup);

    /**
     * 内容所属的layoutId
     * @return
     */
     int getContentLayoutId() ;
}
