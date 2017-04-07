package com.example.administrator.popupwindow.simple;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.popupwindow.R;


/**
 * Created by Li on 2017/3/6.
 */

public class SimpleCustomerAlertDialog extends DialogFragment {



    public static DialogFragment getInstance(String msg,String ok,String cancel){
        SimpleCustomerAlertDialog simpleAlertDialog = new SimpleCustomerAlertDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("ok",ok);
        args.putString("cancel",cancel);
        simpleAlertDialog.setArguments(args);
        return simpleAlertDialog;
    }


    private DialogInterface.OnClickListener mCancelClickListener;
    private DialogInterface.OnClickListener mOkClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 0);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_simple_dialog,null);
        builder.setView(contentView);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelClickListener != null){
                    mCancelClickListener.onClick(getDialog(),v.getId());
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOkClickListener.onClick(getDialog(),v.getId());
            }
        });
        return builder.create();
    }


    public void setCancelClickListener(DialogInterface.OnClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
    }

    public void setOkClickListener(DialogInterface.OnClickListener mOkClickListener) {
        this.mOkClickListener = mOkClickListener;
    }



}
