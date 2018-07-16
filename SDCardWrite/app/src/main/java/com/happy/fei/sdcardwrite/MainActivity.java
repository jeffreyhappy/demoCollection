package com.happy.fei.sdcardwrite;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private static final String TEST_FILE_NAME = "rw.txt";
    private static final String TEST_WRITE_INFO = "hello rw";
    SDCardRW mRW;


    private TextView tvInfoAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);

        mRW = new SDCardRW(this);
        findViewById(R.id.btn_open_dir).setOnClickListener(this);
        findViewById(R.id.btn_create_file).setOnClickListener(this);
        findViewById(R.id.btn_write_file).setOnClickListener(this);
        findViewById(R.id.btn_read_file).setOnClickListener(this);
        tvInfoAction  = findViewById(R.id.tv_info_action);

        showInfo();
        blog();
    }

    private void blog(){
        File externalCacheDir = getExternalCacheDir();
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File[] externalFilesDirs = getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        Log.d(TAG,"externalCacheDir " + externalCacheDir.getPath());
        Log.d(TAG,"externalFilesDir " + externalFilesDir.getPath());
        for (int i = 0 ; i < externalFilesDirs.length ; i ++){
            Log.d(TAG,"externalCacheDir " + i + " " + externalFilesDirs[i].getPath());
        }
        Log.d(TAG,"externalStorageDirectory " + externalStorageDirectory.getPath());
        Log.d(TAG,"externalStoragePublicDirectory " + externalStoragePublicDirectory.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_dir:
                mRW.requestRootDirPermission();
                break;
            case R.id.btn_create_file:
                Boolean createSuccess = mRW.createFileIfNotExist(TEST_FILE_NAME);
                if (createSuccess){
                    tvInfoAction.setText("创建成功");
                }else {
                    tvInfoAction.setText("创建失败");
                }
                break;
            case R.id.btn_write_file:
                String  writeInfo = TEST_WRITE_INFO + System.currentTimeMillis();
                Boolean writeSuccess = mRW.writeFile(TEST_FILE_NAME,writeInfo);
                if (writeSuccess){
                    tvInfoAction.setText("写入成功 内容" + writeInfo);
                }else {
                    tvInfoAction.setText("写入失败");
                }
                break;
            case R.id.btn_read_file:
                String readInfo = mRW.readFile(TEST_FILE_NAME);
                if (TextUtils.isEmpty(readInfo)){
                    tvInfoAction.setText("读取失败");
                }else {
                    tvInfoAction.setText("读取成功 内容 " + readInfo);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRW.handleActivityResult(requestCode,resultCode,data);
        showInfo();
    }


    private void showInfo(){
        String info = "";
        if (mRW.havePermission()){
            info = "已经给sd卡根目录授权过了,可以在sd卡根目录下读写创建文件了" ;
        }else {
            info = "还没有给外置sd卡授权,请点击打开目录授权,选中sd卡的根目录后点击确认";
        }
        TextView tv = findViewById(R.id.tv_info);
        tv.setText(info);
    }
}
