package com.example.administrator.popupwindow.simple;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.popupwindow.R;


/**
 * Created by Li on 2017/3/6.
 */

public class SimpleCustomerFullAlertDialog extends DialogFragment {



    public static DialogFragment getInstance(String msg,String ok,String cancel){
        SimpleCustomerFullAlertDialog simpleAlertDialog = new SimpleCustomerFullAlertDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("ok",ok);
        args.putString("cancel",cancel);
        simpleAlertDialog.setArguments(args);
        return simpleAlertDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //对于全屏这个非常重要
        //这个去掉了阴影背景，和上下边距
        setStyle(STYLE_NO_FRAME, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_simple_dialog,null);
        TextView tvMsg   = (TextView) contentView.findViewById(R.id.tv_msg);
        Button btnOk   = (Button) contentView.findViewById(R.id.btn_ok);
        Button btnCancel   = (Button) contentView.findViewById(R.id.btn_cancel);



        String msg  =  getArguments().getString("msg");
        String cancel  =  getArguments().getString("cancel");
        String ok  =  getArguments().getString("ok");
        if (!TextUtils.isEmpty(msg)){
            tvMsg.setText(msg);
        }

        if (!TextUtils.isEmpty(cancel)){
            btnCancel.setText(cancel);

        }
        if (!TextUtils.isEmpty(ok)){
            btnOk.setText(ok);
        }



        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null )
        {
            //这个去掉了左右边距
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //透明背景
            dialog.getWindow().setLayout(width, height);//全屏
            dialog.getWindow().getDecorView().setPadding(0,0,0,0);//全屏
        }
    }
}
