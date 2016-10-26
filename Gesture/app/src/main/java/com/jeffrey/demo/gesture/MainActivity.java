package com.jeffrey.demo.gesture;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final String DEBUG_TAG = MainActivity.class.getSimpleName();
    GestureDetectorCompat mGesture;
    private int touchSlop ;
    private int mTouchSlopSquare ;

    float mStartX;
    float mStartY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGesture = new GestureDetectorCompat(this,this);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);

        touchSlop = viewConfiguration.getScaledTouchSlop();
        mTouchSlopSquare = touchSlop * touchSlop;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        mGesture.onTouchEvent(ev);
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
//        Log.d(DEBUG_TAG,"focusX = " + focusX + " focuxY =" + focusY);
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
//                if (distance > mTouchSlopSquare) {
//                    if (Math.abs(deltaX) > Math.abs(deltaY)){
//                        Log.d(DEBUG_TAG,"horizontal scroll");
//
//                    }else {
//                        Log.d(DEBUG_TAG,"vertical scroll");
//
//                    }
//                    Log.d(DEBUG_TAG,"start scrollview  deltaX =" + deltaX + " deltaY =" + deltaY);
//                }
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(DEBUG_TAG,"onDown: " + e.toString());
        Log.d(DEBUG_TAG,"onDown: X = " + e.getX() + " Y= " +e.getY());
        mStartX = e.getX();
        mStartY = e.getY();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onShowPress: " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(DEBUG_TAG,"onSingleTapUp: " + e.toString());
        Log.d(DEBUG_TAG,"onSingleTapUp: X = " + e.getX() + " Y= " +e.getY());

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        Log.d(DEBUG_TAG, "onScroll: distanceX" + distanceX + " distanceY "+distanceY + " touchSlop" );

        Log.d(DEBUG_TAG, "onScroll : Math.abs " + Math.abs(e2.getX() - e1.getX()) + "distance" + (touchSlop) );
        if (Math.abs((e2.getX() - e1.getX())*(e2.getX() - e1.getX())) > (touchSlop *touchSlop) && Math.abs((e2.getY() - e1.getY())*(e2.getY() - e1.getY())) > (touchSlop * touchSlop)){
            if ((e2.getX() - e1.getX())> (e2.getY() - e1.getY())){
                Log.d(DEBUG_TAG, "onScroll horizontial: distanceX " + distanceX + "distanceY" + distanceY);
            }else {
                Log.d(DEBUG_TAG, "onScroll vertical: distanceX " + distanceX + "distanceY" + distanceY);
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + e1.toString()+e2.toString());
        Log.d(DEBUG_TAG, "onFling: velocityX " + velocityX+ " velocityY " + velocityY);
        return false;
    }
}
