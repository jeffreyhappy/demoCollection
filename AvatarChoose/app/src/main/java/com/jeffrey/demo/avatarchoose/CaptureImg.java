package com.jeffrey.demo.avatarchoose;

/**
 * Created by Li on 2016/9/22.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 *
 * 以下摘自android api
 <h1>CTION_IMAGE_CAPTURE</h1>
 <p>
 Added in API level 3
 String ACTION_IMAGE_CAPTURE
 Standard Intent action that can be sent to have the camera application capture an image and return it.

 The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of LOLLIPOP, this uri can also be supplied through setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling startActivity(Intent).

 Note: if you app targets M and above and declares as using the CAMERA permission which is not granted, then attempting to use this action will result in a SecurityException.

 See also:

 EXTRA_OUTPUT
 Constant Value: "android.media.action.IMAGE_CAPTURE"
 </p>
 */
public class CaptureImg {

    private   final String TAG = CaptureImg.class.getSimpleName();


    private   final String IMAGE_KEY = "captureImageUri";
    Activity  mActivity;
    Uri    imageUriFromCamera ;


    private OnChooseListener  mChooseListener;


    public CaptureImg(Activity activity,OnChooseListener  chooseListener){
        this.mActivity = activity;
        this.mChooseListener = chooseListener;
    }
    public void startCapture(){
        imageUriFromCamera = createImageUri(mActivity);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        mActivity.startActivityForResult(intent, ChooseActivity.REQUEST_CODE_FROM_CAMERA);
    }

    public void onResult(int requestCode, int resultCode, Intent resultData){
        if (resultCode == Activity.RESULT_OK && requestCode == ChooseActivity.REQUEST_CODE_FROM_CAMERA){
                if (mChooseListener != null){
                    Log.i(TAG, "url: " + imageUriFromCamera.toString());
                    String filePath = ChooseUtils.getImageAbsolutePath19(mActivity,imageUriFromCamera);
                    Log.i(TAG, "fileActualPath: " + filePath);
                    mChooseListener.toCrop(Uri.parse("file://" + filePath));
                }
        }
    }



    public void onSave(Bundle  outState){
        if (imageUriFromCamera != null){
            outState.putString(IMAGE_KEY, imageUriFromCamera.toString());
        }
    }
    public void onRestore(Bundle  inState){
        if (inState != null) {
            String savedImage = inState.getString(IMAGE_KEY);
            if (!TextUtils.isEmpty(savedImage)) {
                imageUriFromCamera = Uri.parse(savedImage);
            }
        }
    }


    /**
     * 创建一条图片uri,用于保存拍照后的照片
     */
    private static Uri createImageUri(Context context) {
        String name = "head" + System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }
}
