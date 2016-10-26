package com.jeffrey.demo.gesture;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Li on 2016/10/25.
 */

public class TestHorizontalScrollView extends HorizontalScrollView {
    private static final String DEBUG_TAG = TestHorizontalScrollView.class.getSimpleName();
    private int touchSlop ;
    private int mTouchSlopSquare ;

    float mStartX;
    float mStartY;
    private  boolean  needCatchTouch = false;
    public TestHorizontalScrollView(Context context) {
        super(context);
    }

    public TestHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(DEBUG_TAG,"dispatchTouchEvent ") ;
//        return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(DEBUG_TAG,"onInterceptTouchEvent" );
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onInterceptTouchEvent(ev);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());

        touchSlop = viewConfiguration.getScaledTouchSlop();
        mTouchSlopSquare = touchSlop * touchSlop;

        final int action = ev.getAction();
        final boolean pointerUp =
                (action & MotionEventCompat.ACTION_MASK) == MotionEventCompat.ACTION_POINTER_UP;
        final int skipIndex = pointerUp ? MotionEventCompat.getActionIndex(ev) : -1;

        // Determine focal point
        float sumX = 0, sumY = 0;
        final int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) continue;
            sumX += ev.getX(i);
            sumY += ev.getY(i);
        }
        final int div = pointerUp ? count - 1 : count;
        final float focusX = sumX / div;
        final float focusY = sumY / div;
        Log.d(DEBUG_TAG,"focusX = " + focusX + " focuxY = " + focusY);
        switch (action & MotionEventCompat.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mStartX  = focusX;
                mStartY  = focusY;
                // Cancel long press and taps
                getParent().requestDisallowInterceptTouchEvent(false);
                needCatchTouch = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = (int) (focusX - mStartX);
                final int deltaY = (int) (focusY - mStartY);
//                int distance = (deltaX * deltaX) + (deltaY * deltaY);
                int distance = (Math.abs(deltaX) + Math.abs(deltaY));
                Log.d(DEBUG_TAG,"start scrollview  deltaX =" + deltaX + " deltaY =" + deltaY);
                if (needCatchTouch){
                    Log.d(DEBUG_TAG,"onInterceptTouchEvent needCatchTouch");
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return super.onInterceptTouchEvent(ev);
                }
                if (distance > touchSlop) {
                    if (Math.abs(deltaX) > Math.abs(deltaY)/2 && Math.abs(deltaX) >touchSlop/2&& Math.abs(deltaY)<touchSlop/2){
                        Log.d(DEBUG_TAG,"horizontal scroll");
                        Log.d(DEBUG_TAG,"horizontal deltaX =" + deltaX + " deltaY =" + deltaY);
                        getParent().requestDisallowInterceptTouchEvent(true);
                        needCatchTouch = true;
                        return super.onInterceptTouchEvent(ev);
                    }else {
                        Log.d(DEBUG_TAG,"vertical scroll");
                        Log.d(DEBUG_TAG,"vertical deltaX =" + deltaX + " deltaY =" + deltaY);

                        return super.onInterceptTouchEvent(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    needCatchTouch = false;
                    break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(DEBUG_TAG,"onTouchEvent");
//        if (needCatchTouch){
//            Log.d(DEBUG_TAG,"onTouchEvent needCatchTouch");
//            return true;
//        }
        return super.onTouchEvent(ev);
//        return super.onTouchEvent(ev);
//        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
//
//        touchSlop = viewConfiguration.getScaledTouchSlop();
//        mTouchSlopSquare = touchSlop * touchSlop;
//
//        final int action = ev.getAction();
//        final boolean pointerUp =
//                (action & MotionEventCompat.ACTION_MASK) == MotionEventCompat.ACTION_POINTER_UP;
//        final int skipIndex = pointerUp ? MotionEventCompat.getActionIndex(ev) : -1;
//
//        // Determine focal point
//        float sumX = 0, sumY = 0;
//        final int count = ev.getPointerCount();
//        for (int i = 0; i < count; i++) {
//            if (skipIndex == i) continue;
//            sumX += ev.getX(i);
//            sumY += ev.getY(i);
//        }
//        final int div = pointerUp ? count - 1 : count;
//        final float focusX = sumX / div;
//        final float focusY = sumY / div;
//        Log.d(DEBUG_TAG,"focusX = " + focusX + " focuxY = " + focusY);
//        switch (action & MotionEventCompat.ACTION_MASK){
//            case MotionEvent.ACTION_DOWN:
//                mStartX  = focusX;
//                mStartY  = focusY;
//                // Cancel long press and taps
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final int deltaX = (int) (focusX - mStartX);
//                final int deltaY = (int) (focusY - mStartY);
//                int distance = (deltaX * deltaX) + (deltaY * deltaY);
//                Log.d(DEBUG_TAG,"start scrollview  deltaX =" + deltaX + " deltaY =" + deltaY);
//
//                if (distance > mTouchSlopSquare) {
//                    if (Math.abs(deltaX) > Math.abs(deltaY)){
//                        Log.d(DEBUG_TAG,"horizontal scroll");
//                        Log.d(DEBUG_TAG,"horizontal deltaX =" + deltaX + " deltaY =" + deltaY);
//
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                        return super.onTouchEvent(ev);
//                    }else {
//                        Log.d(DEBUG_TAG,"vertical scroll");
//                        Log.d(DEBUG_TAG,"vertical deltaX =" + deltaX + " deltaY =" + deltaY);
//
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        return super.onTouchEvent(ev);
//                    }
//                }
//                break;
//        }
//
//        return super.onTouchEvent(ev);
    }
}
