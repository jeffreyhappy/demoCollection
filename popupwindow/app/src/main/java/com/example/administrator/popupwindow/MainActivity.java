package com.example.administrator.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.popupwindow.complex.ShareDialog;
import com.example.administrator.popupwindow.simple.SimpleAlertDialog;
import com.example.administrator.popupwindow.simple.SimpleCustomerAlertDialog;
import com.example.administrator.popupwindow.simple.SimpleCustomerFullAlertDialog;
import com.example.administrator.popupwindow.simple.SimpleMDAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                showDialog();
//            }
//        });
//
//        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                showDialogNoCancel();
//            }
//        });
    }

    public  void click(View view){
        switch (view.getId()){
            case R.id.btn:
                showDialog();
                break;
            case R.id.btn2:
                showDialogNoCancel();
                break;
            case R.id.btn3:
                showDialogFragment();
                break;
            case R.id.btn4:
                showMDDialogFragment();
                break;
            case R.id.btn5:
                showCustomerDialogFragment();
                break;
            case R.id.btn6:
                showSimpleCustomerFullAlertDialog();
                break;
        }
    }

    private void showDialog(){
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.show(getSupportFragmentManager(),"popupDialog");
        shareDialog.setCancelable(true);
    }


    private void showDialogNoCancel(){
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.show(getSupportFragmentManager(),"popupDialog");
        shareDialog.setCancelable(false);
    }

    private void showDialogFragment(){
        SimpleAlertDialog simpleAlertDialog = (SimpleAlertDialog) SimpleAlertDialog.getInstance("这是标题","这是一个最简单的对话框");
        simpleAlertDialog.show(getSupportFragmentManager(),"simpleAlertDialog");
    }


    private void showMDDialogFragment(){
        SimpleMDAlertDialog simpleMDAlertDialog = (SimpleMDAlertDialog) SimpleMDAlertDialog.getInstance("这是标题","这是一个material design的对话框");
        simpleMDAlertDialog.show(getSupportFragmentManager(),"SimpleMDAlertDialog");
    }

    private void showCustomerDialogFragment(){
        SimpleCustomerAlertDialog simpleCustomerAlertDialog = (SimpleCustomerAlertDialog) SimpleCustomerAlertDialog.getInstance("这是一个自定义样式的对话框","确定","取消");
        simpleCustomerAlertDialog.show(getSupportFragmentManager(),"SimpleCustomerAlertDialog");
    }

    private void showSimpleCustomerFullAlertDialog(){
        SimpleCustomerFullAlertDialog simpleCustomerFullAlertDialog = (SimpleCustomerFullAlertDialog) SimpleCustomerFullAlertDialog.getInstance("这是一个自定义样式的对话框","确定","取消");
        simpleCustomerFullAlertDialog.show(getSupportFragmentManager(),"SimpleCustomerFullAlertDialog");
    }
}
