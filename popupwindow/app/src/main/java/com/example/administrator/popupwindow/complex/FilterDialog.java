package com.example.administrator.popupwindow.complex;

import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.popupwindow.R;

/**
 * Created by Li on 2017/2/16.
 */

public class FilterDialog extends BottomTopDialogFragment {

    View belowView ;
    @Override
    public void bindContent(ViewGroup viewGroup) {

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.layout_alert_share;
    }

    @Override
    public int getGravity() {
        return Gravity.TOP;
    }


    @Override
    protected void contentShow() {
        bottomTopView.showBelow(belowView);
    }

    public void show(FragmentManager manager, String tag, View belowView) {
        this.belowView = belowView;
        show(manager, tag);
    }
}
