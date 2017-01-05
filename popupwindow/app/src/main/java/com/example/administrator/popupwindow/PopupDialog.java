package com.example.administrator.popupwindow;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PopupDialog extends DialogFragment {

    SimpleAlertView simpleAlertView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        simpleAlertView = new SimpleAlertView(this,getContext(),container);
        View  view = simpleAlertView.getView();


        simpleAlertView.setCancelable(isCancelable());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //透明背景
            dialog.getWindow().setLayout(width, height);//全屏
            dialog.getWindow().getDecorView().setPadding(0,0,0,0);//全屏
        }
        simpleAlertView.show();
    }


    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (simpleAlertView != null){
            simpleAlertView.setCancelable(cancelable);
        }

    }
}
