package com.fei.tabindicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * 创建日期：2018/3/26 on 10:27
 * 描述:
 * 作者:Li
 */

public interface TabAdapter {
    int getCount();
    View getView(ViewGroup parentView ,int position);

}
