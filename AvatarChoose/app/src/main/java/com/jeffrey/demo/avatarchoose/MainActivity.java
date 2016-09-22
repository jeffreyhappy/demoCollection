package com.jeffrey.demo.avatarchoose;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 *  <p>1 继承ChooseActivity</p>
 *  <p>2 调用toLocalChoose()或者toCameraChoose()</p>
 *  <p>3 获取返回
 *      public void onChooseDone(Uri uri) {
 Glide.with(this).load(uri).into(mIv);
 }</p>
 *
 */
public class MainActivity extends ChooseActivity implements View.OnClickListener {

    private ImageView  mIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        mIv = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                toLocalChoose();
                break;
            case R.id.button2:
                toCameraChoose();
                break;
        }
    }


    @Override
    public void onChooseDone(Uri uri) {
        Glide.with(this).load(uri).into(mIv);
    }

}
