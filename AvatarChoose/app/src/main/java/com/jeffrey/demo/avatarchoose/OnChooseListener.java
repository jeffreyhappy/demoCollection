package com.jeffrey.demo.avatarchoose;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Li on 2016/9/22.
 */

public interface OnChooseListener {
    void onChooseDone(Bitmap bitmap);
    void toCrop(Uri uri);
}
