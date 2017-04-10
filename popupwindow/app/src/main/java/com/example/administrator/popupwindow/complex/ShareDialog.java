package com.example.administrator.popupwindow.complex;

import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.popupwindow.R;

/**
 * Created by Li on 2017/2/16.
 */

public class ShareDialog extends BottomTopDialogFragment {
    @Override
    public void bindContent(ViewGroup viewGroup) {
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDismissWithAnim();
            }
        });
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.layout_alert_share;
    }

}
