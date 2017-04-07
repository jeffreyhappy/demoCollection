//package com.jeffrey.color.divider;
//
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//
///**
// * Created by Li on 2016/12/27.
// */
//
//public  abstract class SimpleDividerItemDecoration extends DividerItemDecoration {
//
//    private boolean showHead = true;
//    private boolean showBottom = true;
//
//
//    @Override
//    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
//
//        Rect bounds = new Rect(0, 0, 0, 0);
//        int transitionX = (int) ViewCompat.getTranslationX(child);
//        int transitionY = (int) ViewCompat.getTranslationY(child);
//        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//        /**
//         * vertical :
//         * left = parent.左padding + transitionX
//         * right= parent.width - parent.右padding + transitionX 因为正常是填满的
//         * top  = child的底 + child的bottomMargin + transition  我们的divider都是画在child下面的
//         * bottom = top + divider的高
//         *
//         *
//         * horizontal :
//         * left = child的右边界 + 右marign + transitionX
//         * right = left + divider的宽
//         * top  = child.top + 上marign + transitionY  正常高度填满
//         * bottom = parent的高 - parent的底marign + transitionY
//         */
//        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
//            if (position == 0 && showHead){
//                bounds =  boundsTop(position,parent,child);
//            }
//
//            int totalCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
//            if (position == totalCount-1 && !showBottom){
//                boundsBottom(position,parent,child);
//            }
//
//
//            boundsBottom(position,parent,child);
//
//
//        }else{
//            Drawable dividerDrawable = getDrawableDivider(position,parent);
//            bounds.top = parent.getPaddingTop() +
//                    params.topMargin + transitionY;
//            bounds.bottom = parent.getHeight() - parent.getPaddingBottom() -
//                    params.bottomMargin + transitionY;
//
//            bounds.left = child.getRight() + params.rightMargin + transitionX;
//            bounds.right = bounds.left + dividerDrawable.getIntrinsicWidth();
//        }
//
//        Log.d("fei","getDividerBound position = " + position);
//        return bounds;
//    }
//
//    @Override
//    protected void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
//        Drawable dividerDrawable = getDrawableDivider(position,parent);
//
//
//        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
//            outRect.bottom = dividerDrawable.getIntrinsicHeight();
//        } else {
//            outRect.left = dividerDrawable.getIntrinsicWidth();
//        }
//    }
//
//    private int getOrientation(RecyclerView parent) {
//        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
//            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
//            return layoutManager.getOrientation();
//        } else if(parent.getLayoutManager() instanceof GridLayoutManager){
//            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
//            return layoutManager.getOrientation();
//        } else {
//            throw new IllegalStateException(
//                    "DividerItemDecoration can only be used with a LinearLayoutManager.");
//        }
//    }
//
//    private Rect boundsBottom(int position, RecyclerView parent, View child){
//        Rect bounds = new Rect(0, 0, 0, 0);
//        int transitionX = (int) ViewCompat.getTranslationX(child);
//        int transitionY = (int) ViewCompat.getTranslationY(child);
//        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//        Drawable dividerDrawable = getDrawableDivider(position,parent);
//        int dividerSize = dividerDrawable.getIntrinsicHeight();
//
//        bounds.left = parent.getPaddingLeft() + transitionX;
//        bounds.right = parent.getWidth() - parent.getPaddingRight() + transitionX;
//
//        bounds.top = child.getBottom() + params.bottomMargin + transitionY;
//        bounds.bottom = bounds.top + dividerSize;
//        return bounds;
//    }
//
//
//    private Rect boundsTop(int position, RecyclerView parent, View child){
//        Rect bounds = new Rect(0, 0, 0, 0);
//        int transitionX = (int) ViewCompat.getTranslationX(child);
//        int transitionY = (int) ViewCompat.getTranslationY(child);
//        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//        Drawable dividerDrawable = getDrawableDivider(position,parent);
//        int dividerSize = dividerDrawable.getIntrinsicHeight();
//
//        bounds.left = parent.getPaddingLeft() + transitionX;
//        bounds.right = parent.getWidth() - parent.getPaddingRight() + transitionX;
//
//        bounds.top = child.getTop() + params.topMargin + transitionY+dividerSize;
//        bounds.bottom = child.getTop();
//        return bounds;
//    }
//
//    @Override
//    protected abstract Drawable getDrawableDivider(int position, RecyclerView parent) ;
//
//    public void setShowHead(boolean showHead) {
//        this.showHead = showHead;
//    }
//
//    public void setShowBottom(boolean showBottom) {
//        this.showBottom = showBottom;
//    }
//}
