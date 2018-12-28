package com.jeffrey.demo.avatarchoose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

/**
 * 6.0 and below
 * Created by Li on 2016/10/27.
 */

public class CropAction6 implements CropActionBase {

    public void clipPhoto(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //Android N need set permission to uri otherwise system camera don't has permission to access file wait crop
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, ChooseActivity.REQUEST_CROP);
    }

    public Bitmap onResult(int requestCode, int resultCode, Intent resultData){
        if (resultCode == Activity.RESULT_OK && requestCode == ChooseActivity.REQUEST_CROP && resultData != null){
            Bundle extras = resultData.getExtras();
            if (extras != null) {
                Bitmap bitmap = extras.getParcelable("data");
                return bitmap;
            }
        }
        return null;
    }
}
