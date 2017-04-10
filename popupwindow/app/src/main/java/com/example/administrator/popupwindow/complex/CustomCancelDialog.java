package com.example.administrator.popupwindow.complex;

/**
 * Created by Li on 2017/4/10.
 */

import android.app.Dialog;
import android.content.Context;

/**
 * 自定义响应取消的对话dialog,
 * 我们需要在用户点击了返回按钮后，先把动画显示完，再消失
 */
public  class CustomCancelDialog extends Dialog {

    private OnBottomTopBackListener backPressed;
    private Boolean cancelFlag;
    public CustomCancelDialog(Context context) {
        super(context);
    }

    public CustomCancelDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomCancelDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setBackPressed(OnBottomTopBackListener backPressed) {
        this.backPressed = backPressed;
    }

    @Override
    public void setCancelable(boolean cancelFlag) {
        super.setCancelable(cancelFlag);
        this.cancelFlag = cancelFlag;
    }

    @Override
    public void onBackPressed() {

        if (!cancelFlag){
            return;
        }

        if (backPressed != null){
            //有自定义的返回事件处理，就让我们自己处理
            backPressed.OnBackPressed();
        }else {
            super.onBackPressed();
        }

    }
}
