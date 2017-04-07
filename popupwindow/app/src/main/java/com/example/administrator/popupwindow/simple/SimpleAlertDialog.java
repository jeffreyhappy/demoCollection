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

public class SimpleAlertDialog extends DialogFragment {



    private DialogInterface.OnClickListener mCancelClickListener;
    private DialogInterface.OnClickListener mOkClickListener;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 0);

        return builder.create();
    }




}
