package com.jeffrey.demo.avatarchoose;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * 具体使用的时候只需要继承他就可以了
 *
 *
 * 关于图片选择android api中有示例文档
 * 选取本地图片https://developer.android.com/guide/topics/providers/document-provider.html
 * Created by Li on 2016/9/22.
 */

public abstract class ChooseActivity extends AppCompatActivity implements OnChooseListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private int REQUEST_PERMISSION_FOR_LOCAL_CHOOSE = 1;
    private int REQUEST_PERMISSION_FOR_CAMERA_CHOOSE = 2;

    public static final int READ_REQUEST_CODE = 211;
    public static final int  REQUEST_CODE_FROM_CAMERA  = 311;
    public static final int REQUEST_CROP    = 311;


    private ChooseLocalImg mChooseLocalImg;
    private CaptureImg mCaptureImg;
    private CropAction   mCropAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCropAction = new CropAction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mChooseLocalImg != null) {
            mChooseLocalImg.onResult(requestCode, resultCode, data);
        }
        if (mCaptureImg != null) {
            mCaptureImg.onResult(requestCode, resultCode, data);
        }

        if (resultCode == Activity.RESULT_OK && mCropAction != null){
            Bitmap bitmap = mCropAction.onResult(requestCode,resultCode,data);
            onChooseDone(bitmap);
        }
    }

    @Override
    public void toCrop(Uri uri) {
        mCropAction.clipPhoto(this,uri);
    }

    /**
     * 从本地相册选择
     */
    protected  void toLocalChoose(){
        if (mChooseLocalImg == null) {
            mChooseLocalImg = new ChooseLocalImg(this, this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mChooseLocalImg.toChoose();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_FOR_LOCAL_CHOOSE);
        }
    }

    /**
     * 拍照选择
     */
    protected  void toCameraChoose(){
        if (mCaptureImg == null) {
            mCaptureImg = new CaptureImg(this, this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mCaptureImg.startCapture();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISSION_FOR_CAMERA_CHOOSE);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mCaptureImg != null) {
            mCaptureImg.onSave(outState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        if (mCaptureImg != null) {
            mCaptureImg.onRestore(inState);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_FOR_LOCAL_CHOOSE) {
            //这里我们就请求一个权限  所以grantResult的大小应该为1
            if (grantResults.length == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mChooseLocalImg.toChoose();
                } else {
                    Toast.makeText(this, "请允许权限", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == REQUEST_PERMISSION_FOR_CAMERA_CHOOSE) {
            //这里我们就请求两个个权限  所以grantResult的大小应该为2
            if (grantResults.length == 2) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    mCaptureImg.startCapture();
                } else {
                    Toast.makeText(this, "请允许权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
