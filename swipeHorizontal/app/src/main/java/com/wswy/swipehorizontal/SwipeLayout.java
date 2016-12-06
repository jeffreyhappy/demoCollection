package com.wswy.swipehorizontal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * Created by Li on 2016/12/6.
 */

public class SwipeLayout extends ViewGroup {
    public SwipeLayout(Context context) {
        super(context);
        initStepOne();
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStepOne();
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStepOne();
    }


    private  float objectX;
    private  float objectY;
    private  int objectH;
    private  int objectW;
    private  int parentWidth;
    private  Object dataObject;
    private  float halfWidth;
    private float BASE_ROTATION_DEGREES;
    private float aPosX;
    private float aDownTouchX;
    private float aDownTouchY;

    private static final int INVALID_POINTER_ID = -1;

    // The active pointer is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    private View frame = null;


    // private final Object obj = new Object();
    private boolean isAnimationRunning = false;
    private float MAX_COS = (float) Math.cos(Math.toRadians(45));
    // 支持左右滑
    private boolean isNeedSwipe = true;

    private int animDuration = 300;
    private float scale;
    private GestureDetectorCompat mGesture;

    enum MoveStatus{
        none,
        horizontal,
        vertical
    }

    private  MoveStatus  mMoveStatus;
    private int mTouchSlop;
    private void initStepOne(){
        mGesture = new GestureDetectorCompat(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

    }

    private void initStepTwo(){
        frame = this;
        this.objectX = frame.getX();
        this.objectY = frame.getY();
        this.objectW = frame.getWidth();
        this.objectH = frame.getHeight();
        this.halfWidth = objectW / 2f;
        this.parentWidth = ((ViewGroup) frame.getParent()).getWidth();
        this.mTouchSlop = ViewConfiguration.get(frame.getContext()).getScaledTouchSlop();
    }

    public void setIsNeedSwipe(boolean isNeedSwipe) {
        this.isNeedSwipe = isNeedSwipe;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("fei scroll","onInterceptTouchEvent true");

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("fei scroll","dispatchTouchEvent true");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("fei scroll","onTouchEvent ");

        mGesture.onTouchEvent(event);
        try {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    initStepTwo();
                    // Save the ID of this pointer
                    mActivePointerId = event.getPointerId(0);
                    final float x = event.getX(mActivePointerId);
                    final float y = event.getY(mActivePointerId);
                    // Remember where we started
                    aDownTouchX = x;
                    aDownTouchY = y;
                    aPosX = frame.getX();
                    mMoveStatus = MoveStatus.none;
//                    isSingleTapUp = true;
//                    frame.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    // Extract the index of the pointer that left the touch sensor
                    final int pointerIndex = (event.getAction() &
                            MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = event.getPointerId(pointerIndex);
                    if (pointerId == mActivePointerId) {
                        // This was our active pointer going up. Choose a new
                        // active pointer and adjust accordingly.
                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                        mActivePointerId = event.getPointerId(newPointerIndex);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
//                    if (mMoveStatus == MoveStatus.horizontal){
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                        Log.d("fei scroll","move horizontal");
//                        return true;
//                    }else if (mMoveStatus == MoveStatus.vertical){
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        Log.d("fei scroll","move vertical");
//                        return false;
//                    }
                    // Find the index of the active pointer and fetch its position
                    final int pointerIndexMove = event.findPointerIndex(mActivePointerId);
                    final float xMove = event.getX(pointerIndexMove);
                    final float yMove = event.getY(pointerIndexMove);
                    // from http://android-developers.blogspot.com/2010/06/making-sense-of-multitouch.html
                    // Calculate the distance moved
                    final float dx = xMove - aDownTouchX;
                    final float dy = yMove - aDownTouchY;

                    float angle = Math.abs(Math.abs(dy) / Math.abs(dx));
                    angle = (float) Math.toDegrees(Math.atan(angle));
                    if (mMoveStatus == MoveStatus.none){
                        if (angle< 45){ //横着的
                            if (Math.abs(dx) > 0){
                                mMoveStatus = MoveStatus.horizontal;
                            }
                        }else {//竖着的
                            if (Math.abs(dy) > 0){
                                mMoveStatus = MoveStatus.vertical;
                            }
                        }
                    }
                    Log.d("fei scroll","angle = " + angle + " dx = " + dx + "dy = " + dy +  " status = " + mMoveStatus);
                    // Move the frame
                    aPosX += dx;
                    // calculate the rotation degrees
                    float distObjectX = aPosX - objectX;
                    // in this area would be code for doing something with the view as the frame moves.
                    if (isNeedSwipe && mMoveStatus != MoveStatus.vertical) {
                        frame.setX(aPosX);
                        getParent().requestDisallowInterceptTouchEvent(true);
                        Log.d("fei scroll","swipe true posX = " + aPosX);
                        return true;
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        Log.d("fei scroll","swipe false");
                        return false;
                    }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    frame.getParent().requestDisallowInterceptTouchEvent(false);
                    //mActivePointerId = INVALID_POINTER_ID;
                    int pointerCount = event.getPointerCount();
                    int activePointerId = Math.min(mActivePointerId, pointerCount - 1);
                    mActivePointerId = INVALID_POINTER_ID;
                    resetCardViewOnStack(event);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    private boolean  isHorizontalScroll(){
        return mMoveStatus == MoveStatus.horizontal ;
    }
    private float getScrollProgress() {
        float dx = aPosX - objectX;
        return Math.min(dx, 400f) / 400f;
    }


    private float getScrollXProgressPercent() {
        if (movedBeyondLeftBorder()) {
            return -1f;
        } else if (movedBeyondRightBorder()) {
            return 1f;
        } else {
            float zeroToOneValue = (aPosX + halfWidth - leftBorder()) / (rightBorder() - leftBorder());
            return zeroToOneValue * 2f - 1f;
        }
    }

    private boolean resetCardViewOnStack(MotionEvent event) {
        if (isNeedSwipe) {
            final int duration = 200;
            if (movedBeyondLeftBorder()) {
                // Left Swipe
                onSelected(true, getExitPoint(-objectW), duration);
            } else if (movedBeyondRightBorder()) {
                // Right Swipe
                onSelected(false, getExitPoint(parentWidth), duration);
            } else {
                frame.animate()
                        .setDuration(animDuration)
                        .setInterpolator(new OvershootInterpolator(1.5f))
                        .x(objectX)
                        .start();
                scale = getScrollProgress();
                this.frame.postDelayed(animRun, 0);
                aPosX = 0;
                aDownTouchX = 0;
            }
        } else {
        }
        return false;
    }

    private Runnable animRun = new Runnable() {
        @Override
        public void run() {
            if (scale > 0) {
                scale = scale - 0.1f;
                if (scale < 0)
                    scale = 0;
                frame.postDelayed(this, animDuration / 20);
            }
        }
    };

    private boolean movedBeyondLeftBorder() {
        return aPosX + halfWidth < leftBorder();
    }

    private boolean movedBeyondRightBorder() {
        return aPosX + halfWidth > rightBorder();
    }


    public float leftBorder() {
        return parentWidth / 4f;
    }

    public float rightBorder() {
        return 3 * parentWidth / 4f;
    }


    public Object getDataObject(){
        return dataObject;
    }

    public void onSelected(final boolean isLeft, float exitY, long duration) {
        isAnimationRunning = true;
        float exitX;
        if (isLeft) {
            exitX = -objectW - getRotationWidthOffset();
        } else {
            exitX = parentWidth + getRotationWidthOffset();
        }

        this.frame.animate()
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .translationX(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isLeft) {
//                            mFlingListener.onCardExited();
//                            mFlingListener.leftExit(dataObject);
                        } else {
//                            mFlingListener.onCardExited();
//                            mFlingListener.rightExit(dataObject);
                        }
                        isAnimationRunning = false;
                    }
                }).start();
    }

    /**
     * Starts a default left exit animation.
     */
//    public void selectLeft() {
//        if (!isAnimationRunning)
//            selectLeft(animDuration);
//    }

    /**
     * Starts a default left exit animation.
     */
//    public void selectLeft(long duration) {
//        if (!isAnimationRunning)
//            onSelected(true, objectY, duration);
//    }

    /**
     * Starts a default right exit animation.
     */
//    public void selectRight() {
//        if (!isAnimationRunning)
//            selectRight(animDuration);
//    }

    /**
     * Starts a default right exit animation.
     */
//    public void selectRight(long duration) {
//        if (!isAnimationRunning)
//            onSelected(false, objectY, duration);
//    }
//
    private float getExitPoint(int exitXPoint) {
        float[] x = new float[2];
        x[0] = objectX;
        x[1] = aPosX;

        float[] y = new float[2];
        y[0] = objectY;

        LinearRegression regression = new LinearRegression(x, y);

        //Your typical y = ax+b linear regression
        return (float) regression.slope() * exitXPoint + (float) regression.intercept();
    }


    /**
     * When the object rotates it's width becomes bigger.
     * The maximum width is at 45 degrees.
     * <p>
     * The below method calculates the width offset of the rotation.
     */
    private float getRotationWidthOffset() {
        return objectW / MAX_COS - objectW;
    }


    public void setRotationDegrees(float degrees) {
        this.BASE_ROTATION_DEGREES = degrees;
    }


}
