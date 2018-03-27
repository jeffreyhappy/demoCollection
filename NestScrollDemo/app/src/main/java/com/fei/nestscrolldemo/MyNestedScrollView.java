package com.fei.nestscrolldemo;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 创建日期：2018/3/26 on 16:23
 * 描述:
 * 作者:Li
 */

public class MyNestedScrollView extends NestedScrollView {
    private String TAG = MyNestedScrollView.class.getSimpleName();
    final int MAX_SCROLL_LENGTH = 400;
    /**
     * 该控件滑动的高度，高于这个高度后交给子滑动控件
     */
    int mParentScrollHeight ;
    int mScrollY ;
    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollHeight(int scrollLength) {
        this.mParentScrollHeight = scrollLength;
    }




    /**
     * 子控件告诉父控件 开始滑动了
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * 如果有就返回true
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);

        if (mScrollY < mParentScrollHeight) {
            consumed[0] = dx;
            consumed[1] = dy;
            scrollBy(0, dy);
        }

        Log.d(TAG,"dx " + dx + " dy "+ dy +  " " + consumed[0]  + " " + consumed[1] + " scrollY " + mScrollY);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
    }



}
