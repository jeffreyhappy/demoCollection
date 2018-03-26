package com.fei.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/3/26 on 10:14
 * 描述:
 * 作者:Li
 */

public class TabIndicator extends FrameLayout  implements ViewPager.OnPageChangeListener{

    private String TAG = TabIndicator.class.getSimpleName();
    TabAdapter mAdapter;
    LinearLayout mTabContainer;
    HorizontalScrollView mTabScrollView;
    PositionIndicator   mPositionIndicator;
    ViewPager mViewPager;

    int lastSelectedPosition;

    List<ChildPosition> mChildPosition ;

    public TabIndicator(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public TabIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TabIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    public void setAdapter(TabAdapter adapter){
        this.mAdapter = adapter;
        mChildPosition =  new ArrayList<>();
        recreate();
    }

    private void init(Context context,AttributeSet attrs){
        View root = LayoutInflater.from(context).inflate(R.layout.layout_indicator_root,this);
        mTabContainer = root.findViewById(R.id.ll_tab_container);
        mTabScrollView = root.findViewById(R.id.sv_tab_container);
        mPositionIndicator = root.findViewById(R.id.position_container);
        this.lastSelectedPosition = 0;
        recreate();
        if (attrs!=null){
            TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.TabIndicator);
            boolean showPosIndicator = typedArray.getBoolean(R.styleable.TabIndicator_posIndicatorShow,false);
            int posIndicatorColor = typedArray.getColor(R.styleable.TabIndicator_posIndicatorColor, Color.BLUE);
            float posIndicatorHeight= typedArray.getDimension(R.styleable.TabIndicator_posIndicatorHeight,0);
            if (showPosIndicator){
                mPositionIndicator.setVisibility(View.VISIBLE);
                mPositionIndicator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)posIndicatorHeight));
                mPositionIndicator.setColor(posIndicatorColor);
            }else {
                mPositionIndicator.setVisibility(View.GONE);
            }
        }
    }

    private void recreate(){
        if (mAdapter == null || mAdapter.getCount() <=0){
            return;
        }
        int count = mAdapter.getCount();
        for (int i = 0 ; i < count ;i ++){
            mTabContainer.addView(mAdapter.getView(mTabContainer,i));
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        mChildPosition.clear();
        for (int i = 0 ; i < mTabContainer.getChildCount();i++){
            View child = mTabContainer.getChildAt(i);
            int childLeft= child.getLeft();
            int childRight = child.getRight();
            mChildPosition.add(new ChildPosition(childLeft,childRight));
        }

        if (this.mViewPager != null){
            this.onPageSelected(mViewPager.getCurrentItem());
        }

        Log.d(TAG,"onLayout");

    }

    public void bindViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != lastSelectedPosition){
            View child = mTabContainer.getChildAt(lastSelectedPosition);
            if (child != null){
                child.setSelected(false);
            }
        }
        View child = mTabContainer.getChildAt(position);
        child.setSelected(true);

        moveChildViewIntoBound(position);
        int positionLeft = child.getLeft()-mTabScrollView.getScrollX();
        int positionRight = positionLeft + child.getWidth();
        mPositionIndicator.setRect(positionLeft,positionRight);
        this.lastSelectedPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


//    /**
//     * 在左边 返回 -1  右边返回 1 ，中间返回0
//     * @param childPos
//     * @return
//     */
//    private int isOutOfBound(int childPos){
//        int parentWidth = mTabScrollView.getWidth();
//        int parentScrollX = mTabScrollView.getScrollX();
//        int leftBound = parentScrollX;
//        int rightBound = parentScrollX + parentWidth;
//        ChildPosition childPosition = mChildPosition.get(childPos);
//        if (childPosition.left < leftBound){
//            return -1;
//        }
//
//        if (childPosition.right > rightBound){
//            return 1;
//        }
//        return 0;
//
//    }

    private void moveChildViewIntoBound(int childPos){
        int parentWidth = mTabScrollView.getWidth();
        int parentScrollX = mTabScrollView.getScrollX();
        int leftBound = parentScrollX;
        int rightBound = parentScrollX + parentWidth;
        ChildPosition childPosition = mChildPosition.get(childPos);
        if (childPosition.left < leftBound){
            mTabScrollView.scrollTo(leftBound + (childPosition.left - leftBound),0);
        }

        if (childPosition.right > rightBound){
            mTabScrollView.scrollTo(leftBound + (childPosition.right - rightBound),0);
        }
    }



    private static class ChildPosition{
        private int left;
        private int right;

        public ChildPosition(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }
    }
}
