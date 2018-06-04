package com.fei.downloaddemo.home;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.fei.downloaddemo.ItemClickListener;
import com.fei.downloaddemo.R;

/**
 * 创建日期：2018/6/4 on 11:53
 * 描述:
 * 作者:Li
 */

public class HomeListClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private ItemClickListener mItemClickListener;
    private RecyclerView mRv;

    private final String TAG = HomeListClickListener.class.getSimpleName();

    public HomeListClickListener(Context context,ItemClickListener listener){
        this.mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = mRv.findChildViewUnder(e.getX(),e.getY());
                RecyclerView.LayoutManager layoutManager = mRv.getLayoutManager();
                int position = layoutManager.getPosition(childView);
                View clickChildView = findChildView(childView,mItemClickListener.listenChildID(),e);
                if (clickChildView != null){
                    mItemClickListener.onChildItemClick(clickChildView,position);
                }else {
                    mItemClickListener.onItemClick(childView,position);
                }
                Log.d(TAG,"find click child view = " + (clickChildView == null ? false:true));
                return true;
            }
        });
        this.mItemClickListener = listener;
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mRv = rv;
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    private View findChildView(View parentView,int[] childViewID,MotionEvent e){
        View findView = null;
        for (int i = 0 ; i < childViewID.length ; i++){
            View childView = parentView.findViewById(childViewID[i]);
            parentView.getTop();
            int[] oneRect = new int[2];
            Log.d(TAG,"parentView top " + parentView.getTop() +" childView top = " + childView.getTop());
            Log.d(TAG,"parentView left " + parentView.getLeft() +" childView left = " + childView.getLeft());

            Log.d(TAG,"click " + e.getX() + " " + e.getY());
            Log.d(TAG,"childView " + oneRect[0] + " " + oneRect[1]);
            int left = childView.getLeft();
            int right = left+ childView.getWidth();
            int top = parentView.getTop() + childView.getTop();
            int bottom = top+childView.getHeight();

            if ( e.getX() > left && e.getX() < right && e.getY() > top  && e.getY() < bottom){
                findView =  childView;
            }
        }
        return findView;

    }


}
