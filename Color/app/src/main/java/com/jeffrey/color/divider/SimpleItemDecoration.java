package com.jeffrey.color.divider;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public  class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    DividerHelperInterface dividerHelper;



    public SimpleItemDecoration(DividerHelperInterface builder){
        this.dividerHelper = builder;
    }
    /**
     * 拿到drawable后 给 outRect设置下大小，以后绘制就在这个范围里画
     * 测试了下，设置top和bottom效果一样的
     * update:  top 和bottom 不一样，top是往上,可以画第一个的head getDivider
     *          bottom可以画最后一个的bottom  getDivider
     * update: top bottom left right 指的是item的上下左右边距
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        //1 获取对应view的实际位置，然后让子类确定具体需要多大的空间
        if (parent.getChildAdapterPosition(view) ==  RecyclerView.NO_POSITION) {
            return;
        }
        int childPosition = parent.getChildAdapterPosition(view);
//        setItemOffsets(outRect,childPosition,parent);
        int totalCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        Drawable dividerDrawable = dividerHelper.getDivider();


        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {

            if (dividerHelper.showTop() && childPosition == 0){
                outRect.top = dividerDrawable.getIntrinsicHeight();
            }

            if ((childPosition == totalCount-1 && dividerHelper.showBottom())
                    || childPosition < totalCount -1){
                outRect.bottom = dividerDrawable.getIntrinsicHeight();
            }

//            Log.d("fei "," getItemOffsets pos=" + childPosition + " top =" + outRect.top + " bottom =" + outRect.bottom);

        } else {
            outRect.left = dividerDrawable.getIntrinsicWidth();
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }

        int orientation = getOrientation(parent) ;
        int validChildCount = parent.getChildCount();//屏幕上显示的item count

        //1 遍历屏幕上的所有view，确定实际位置
        //2 调用子类，获取对应的位置和分割drawable
        //3 画出来
        for (int i = 0; i < validChildCount; i++) {
            View childView = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(childView); //这才是真正的adapter 位置

            //显示顶部分割线
            if (childPosition == 0 && dividerHelper.showTop()){
                if (orientation == OrientationHelper.VERTICAL){
                    dividerHelper.drawTop(c,parent,childView);
                }else {
                    dividerHelper.drawLeft(c,parent,childView);
                }
            }

            int totalCount  = parent.getAdapter().getItemCount();

            if ((childPosition == totalCount-1 && dividerHelper.showBottom())||
                    childPosition < totalCount -1){
                if (orientation == OrientationHelper.VERTICAL) {
                    //显示底部分割线
                    dividerHelper.drawBottom(c, parent, childView);
                }else {
                    dividerHelper.drawRight(c, parent, childView);

                }

            }

        }

    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else if(parent.getLayoutManager() instanceof GridLayoutManager){
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager.");
        }
    }




}