package com.example.administrator.popupwindow.complex;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/**
 * Created by Li on 2017/1/5.
 */

public abstract class BottomTopDialogFragment extends DialogFragment implements BottomTopViewInterface {

    BottomTopView bottomTopView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomTopView = new BottomTopView(this, getContext(), container, this);
        bottomTopView.setCancelable(isCancelable());
        return bottomTopView.getView();
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
        contentShow();
    }


    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (bottomTopView != null){
            bottomTopView.setCancelable(cancelable);
        }
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }

    public void onDismissWithAnim(){
        dismissWithAnim();
    }

    protected void contentShow(){
        bottomTopView.show();
    }


    private BottomTopView getBottomTopView(){
        return bottomTopView;
    }



    private CustomCancelDialog initDialog() {
        //这个style只有视图，没有外边框，和标题栏等
        setStyle(STYLE_NO_FRAME, 0);

        CustomCancelDialog dialog = new CustomCancelDialog(getActivity(), getTheme());
        dialog.setBackPressed(new OnBottomTopBackListener() {
            @Override
            public void OnBackPressed() {
                dismissWithAnim();
            }
        });
        return dialog;
    }

    private void dismissWithAnim(){
        BottomTopView simpleAlertView = getBottomTopView();
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



}
