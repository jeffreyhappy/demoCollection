package com.jeffrey.demo.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 竖着的自己处理，横着的不拦着，给子控件处理
 * Created by Li on 2016/10/26.
 */

public class TestScrollView extends ScrollView {

    GestureDetector  mGesture;
    public TestScrollView(Context context) {
        super(context);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mGesture = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return (Math.abs(distanceY) > Math.abs(distanceX));
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean needIntercept = super.onInterceptTouchEvent(ev) && mGesture.onTouchEvent(ev);
        Log.d("fei","TestScrollView needIntercept = " + needIntercept);
        return needIntercept;
    }
}
