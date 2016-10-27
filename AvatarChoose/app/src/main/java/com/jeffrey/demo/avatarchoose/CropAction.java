package com.jeffrey.demo.avatarchoose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Li on 2016/10/27.
 */

public class CropAction {

    public void clipPhoto(Activity activity,Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
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
        if (resultCode == Activity.RESULT_OK && requestCode == ChooseActivity.REQUEST_CROP){
            Bundle extras = resultData.getExtras();
            if (extras != null) {
                Bitmap bitmap = extras.getParcelable("data");
                return bitmap;
            }
        }
        return null;
    }
}
