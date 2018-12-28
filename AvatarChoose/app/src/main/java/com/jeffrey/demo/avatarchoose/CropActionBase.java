package com.jeffrey.demo.avatarchoose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

public interface CropActionBase {
     void clipPhoto(Activity activity, Uri uri);
    Bitmap onResult(int requestCode, int resultCode, Intent resultData);
}
