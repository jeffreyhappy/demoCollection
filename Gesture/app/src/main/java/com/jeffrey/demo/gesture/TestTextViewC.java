package com.jeffrey.demo.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Li on 2016/10/25.
 */

public class TestTextViewC extends TextView {
    private static final String DEBUG_TAG = TestTextViewC.class.getSimpleName();

    public TestTextViewC(Context context) {
        super(context);
    }

    public TestTextViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextViewC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(DEBUG_TAG,"dispatchTouchEvent" ) ;

        super.dispatchTouchEvent(ev);
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(DEBUG_TAG,"onTouchEvent");
//        super.onTouchEvent(event);
        return true;
    }
}
