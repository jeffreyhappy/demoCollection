package com.example.administrator.popupwindow.complex;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/**
 * Created by Li on 2017/1/5.
 */

public abstract class AlertDialog extends DialogFragment implements AlertViewContentInterface {

    SimpleAlertView simpleAlertView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        simpleAlertView = initView(container);
        return simpleAlertView.getView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return initDialog();
    }



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null )
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



    private SimpleAlertView initView(ViewGroup container){
        SimpleAlertView simpleAlertView = new SimpleAlertView(this, getContext(), container, this);
        simpleAlertView.setCancelable(isCancelable());
        return simpleAlertView;
    }

    private SimpleAlertView getSimpleAlertView(){
        return simpleAlertView;
    }



    private CustomCancelDialog initDialog() {
        //这个style只有视图，没有外边框，和标题栏等
        setStyle(STYLE_NO_FRAME, 0);

        CustomCancelDialog dialog = new CustomCancelDialog(getActivity(), getTheme());
        dialog.setBackPressed(new OnBackPressed() {
            @Override
            public void OnBackPressed() {
                SimpleAlertView simpleAlertView = getSimpleAlertView();
                if (simpleAlertView == null) {
                    dismiss();
                    return;
                }
                //1 先让界面显示动画
                //2 动画完成后直接dismiss()就可以了
                simpleAlertView.dismiss(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        return dialog;
    }


    /**
     * 自定义响应取消的对话dialog,
     * 我们需要在用户点击了返回按钮后，先把动画显示完，再消失
     */
    public static class CustomCancelDialog extends Dialog{

        private OnBackPressed backPressed;
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

        public void setBackPressed(OnBackPressed backPressed) {
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


    public interface OnBackPressed{
        void OnBackPressed();
    }
}
