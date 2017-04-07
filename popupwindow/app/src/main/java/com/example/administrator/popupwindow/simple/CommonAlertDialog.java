package com.example.administrator.popupwindow.simple;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wswy.chechengwang.R;

/**
 * Created by Li on 2017/3/6.
 */

public class CommonAlertDialog extends DialogFragment {



    private DialogInterface.OnClickListener mCancelClickListener;
    private DialogInterface.OnClickListener mOkClickListener;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 0);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_common,null);
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

    public static class Builder{
        private String msg ;
        private String cancel ;
        private String ok ;
        private DialogInterface.OnClickListener mCancelClickListener;
        private DialogInterface.OnClickListener mOkClickListener;

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setOk(String ok) {
            this.ok = ok;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }


        public Builder setCancelListener(DialogInterface.OnClickListener mCancelClickListener) {
            this.mCancelClickListener = mCancelClickListener;
            return this;
        }

        public Builder setOkListener(DialogInterface.OnClickListener mOkClickListener) {
            this.mOkClickListener = mOkClickListener;
            return this;
        }


        public CommonAlertDialog create(){
            CommonAlertDialog commonAlertDialog = new CommonAlertDialog();
            Bundle bundle  = new Bundle();
            bundle.putString("msg",msg);
            bundle.putString("ok",ok);
            bundle.putString("cancel",cancel);
            commonAlertDialog.setArguments(bundle);

            commonAlertDialog.setOkClickListener(mOkClickListener);
            commonAlertDialog.setCancelClickListener(mCancelClickListener);
            return commonAlertDialog;
        }
    }




}
