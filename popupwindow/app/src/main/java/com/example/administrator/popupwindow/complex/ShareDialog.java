package com.example.administrator.popupwindow.complex;

import android.view.ViewGroup;

import com.example.administrator.popupwindow.R;

/**
 * Created by Li on 2017/2/16.
 */

public class ShareDialog extends AlertDialog {
    @Override
    public void bindContent(ViewGroup viewGroup) {

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.layout_alert_share;
    }

}
