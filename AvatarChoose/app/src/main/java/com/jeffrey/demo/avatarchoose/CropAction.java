package com.jeffrey.demo.avatarchoose;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Li on 2016/10/27.
 */

public class CropAction implements CropActionBase{


    private Uri uriClipUri = null;
    private Activity activity;
    public CropAction(Activity activity){
        this.activity = activity;
    }

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
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式，一般设为Bitmap格式及图片类型
        intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
        File cropImg = new File(activity.getExternalFilesDir("img"),"cropTmp");
        uriClipUri = FileUtils.getUriForN(activity,cropImg.getPath());
        //Android 对Intent中所包含数据的大小是有限制的，一般不能超过 1M，否则会使用缩略图 ,所以我们要指定输出裁剪的图片路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //将存储图片的uri读写权限授权给相机应用
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, uriClipUri , Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriClipUri);

        activity.startActivityForResult(intent, ChooseActivity.REQUEST_CROP);
    }

    public Bitmap onResult(int requestCode, int resultCode, Intent resultData){
        if (resultCode == Activity.RESULT_OK && requestCode == ChooseActivity.REQUEST_CROP && resultData != null){
            try {
                return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uriClipUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
