package com.jeffrey.demo.avatarchoose;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Li on 2016/9/22.
 */

public class ChooseLocalImg {
    private String TAG = ChooseLocalImg.class.getSimpleName();

    private Activity mActivity;
    private OnChooseListener  mChooseLisener;
    public ChooseLocalImg(Activity context,OnChooseListener listener){
        this.mActivity = context;
        this.mChooseLisener = listener;
    }


    public void toChoose(){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//
//        // Filter to only show results that can be "opened", such as a
//        // file (as opposed to a list of contacts or timezones)
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        // Filter to show only images, using the image MIME data type.
//        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
//        // To search for all documents available via installed storage providers,
//        // it would be "*/*".
//        intent.setType("image/*");

        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        mActivity.startActivityForResult(intent, ChooseActivity.READ_REQUEST_CODE);
    }


    public void onResult(int requestCode, int resultCode, Intent resultData){
        if (resultCode == Activity.RESULT_OK && requestCode == ChooseActivity.READ_REQUEST_CODE){
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                if (mChooseLisener != null){
                    String fileActualPath = ChooseUtils.getImageAbsolutePath19(mActivity,uri);
                    Log.i(TAG, "fileActualPath: " + fileActualPath);
                    mChooseLisener.toCrop(Uri.parse("file://"+fileActualPath));
                }
            }
        }
    }
}
