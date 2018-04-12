package com.fei.scrollerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 创建日期：2018/4/11 on 14:16
 * 描述:
 * 作者:Li
 */

/**
 * 1 滑动边界处理 done
 * 2 高度测量   done
 * 3 惯性滑动
 */

public class ScrollerLayout extends ViewGroup {
    private String TAG = ScrollerLayout.class.getSimpleName();
    private int mTouchSlop ;
    private  float mYDown;
    private  float mLastY;
    /**
     *
     */
    private int mTotalChildHeight;

    private int mMinimumVelocity;
    private int mMaximumVelocity;
    /**
     * 用来计算滑动速度，如果大于某个阀值的时候 就需要来惯性滑动
     */
    private VelocityTracker mTracker;

    /**
     * 用来完成惯性滑动
     */
    private Scroller mScroller;

    /**
     * 是否是从onInterceptTouchEvent接管触摸事件的
     * 走ScrollerLayout的onTouch有两条路
     * 1 onInterceptTouchEvent返回true , 我这里是按下时返回false，滑动后返回true，所以OnTouch的时候不会受到按下消息。
     * 2 onInterceptTouchEvent一直返回的false,onTouch会接收到全套消息
     * 但是呢 ViewGroup的是这样写的，如果一开始按下的目标下有按钮，就返回true了，所以这两个情况都会出现，我初始化VelocityTracker就得处理一下
        public boolean onInterceptTouchEvent(MotionEvent ev) {
         if (ev.isFromSource(InputDevice.SOURCE_MOUSE)
             && ev.getAction() == MotionEvent.ACTION_DOWN
             && ev.isButtonPressed(MotionEvent.BUTTON_PRIMARY)
             && isOnScrollbarThumb(ev.getX(), ev.getY())) {
             return true;
         }
         return false;
         }
     */
    private boolean isTakeOverFromIntercept;

    public ScrollerLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mScroller = new Scroller(getContext());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 分发的时候 先调用dispatch,如果dispatch会调用onIntercept,
     * 返回true的话就结束了 走true的onTouch
     * 返回false的话就走子view的OnIntercept
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent " + ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mYDown = ev.getRawY();
                mLastY = mYDown;
                isTakeOverFromIntercept = false;

                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = ev.getRawY();
                if (Math.abs(mLastY - mYDown)> mTouchSlop){
                    Log.d(TAG,"onInterceptTouchEvent return true");
                    isTakeOverFromIntercept = true;

                    return true;
                }
                break;
        }
        boolean returnResult = super.onInterceptTouchEvent(ev);
        Log.d(TAG,"onInterceptTouchEvent return result " + returnResult);

        return returnResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent " + event.getAction());
        if (mTracker != null){
            mTracker.addMovement(event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //这里我们要开始接管点击事件了
                mTracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTakeOverFromIntercept && mTracker == null){
                    mTracker = VelocityTracker.obtain();
                }
                float mYMove = event.getRawY() - mLastY;
                if (mYMove < 0 ){
                    //往上滑动不要超过下边界
                    Log.d(TAG," getScrollY() " + getScrollY() + " actualHeight " + mTotalChildHeight);
                    int range = getScrollRange();
                    if (getScrollY() >= range){
                        scrollTo(0,range);
                        return true;
                    }
                }else{
                    //往下滑动不要超过上边界
                    if (getScrollY() <= 0){
                        scrollTo(0, 0);
                        return true;
                    }
                }
                //视图正方向滑动的时候 Y是正数
                //但是手指滑动的差值是负数
                scrollBy(0, (int) mYMove * -1);
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                mTracker.computeCurrentVelocity(1000,mMaximumVelocity);
                float currentVelocity = Math.abs(mTracker.getYVelocity());
                Log.d(TAG,"Velocity currentVelocity = " + currentVelocity );
                Log.d(TAG,"Velocity mMaximumVelocity = " + mMaximumVelocity );
                Log.d(TAG,"Velocity mMinimumVelocity = " + mMinimumVelocity );

                if (currentVelocity > mMinimumVelocity){
                    Log.d(TAG,"Velocity 需要惯性滑动" );
//                    int maxY = mTotalChildHeight - getScrollY()+1000 > mTotalChildHeight ? mTotalChildHeight : getScrollY()+1000;
                    mScroller.fling(0,getScrollY(),0,(int)mTracker.getYVelocity()*-1,0,0,0,getScrollRange());
                    invalidate();
                }else {
                    Log.d(TAG,"Velocity 不需要惯性滑动" );
                }

                mTracker.recycle();
                mTracker = null;
                break;
        }

        /**
         * 作为事件的终结者，如果有子View消费了事件，就不会走到这里，子View什么都没干，ScrollerLayout来接管
         */
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int childMaxWidth = 0;
        int childTotalHeight = 0;
        for (int i = 0 ; i < childCount ; i++){
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
            int childWidth  = getChildAt(i).getMeasuredWidth();
            int childHeight  = getChildAt(i).getHeight();
            if (childWidth > childMaxWidth){
                childMaxWidth = childWidth;
            }
            childTotalHeight += childHeight;
        }
        mTotalChildHeight = childTotalHeight;

        int ensureWidth  = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST){
            ensureWidth = childMaxWidth;
        }
        //在安卓艺术探索中，AT_MOST的意思是想要多大就多大，就是wrap_content
        //而exactly就是准确的，固定的数值和match_parent就是固定的
        int ensureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST){
            ensureHeight = childTotalHeight;
        }

        setMeasuredDimension(ensureWidth,ensureHeight);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            //竖向滑动，所以子View是竖着排列的
            int childCount = getChildCount();
            int top = 0;
            for (int i = 0 ; i < childCount ;i ++){
                View childView  = getChildAt(i);
                childView.layout(0,top,childView.getMeasuredWidth(),top+childView.getMeasuredHeight());
                top += childView.getMeasuredHeight();
            }
        }
    }

    /**
     * 从ScrollView代码里找到的 源码牛逼
     * 可以滑动的距离= 内容的总高度-父view的高度
     * @return
     */
    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {

            scrollRange = Math.max(0,
                    mTotalChildHeight - getHeight());
        }
        return scrollRange;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG,"computeScroll " + mScroller.computeScrollOffset() + " X = " + mScroller.getCurrX() + " Y " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
