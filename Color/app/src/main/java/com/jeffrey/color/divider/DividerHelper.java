package com.jeffrey.color.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jeffrey.color.R;

/**
 * Created by Li on 2017/2/28.
 */

public class DividerHelper implements DividerHelperInterface {
    private Drawable dividerDrawable;

    private boolean showTop = false;
    private boolean showBottom = false;

    public DividerHelper(Drawable dividerDrawable,boolean showTop,boolean showBottom){
        this.dividerDrawable = dividerDrawable;
        this.showTop = showTop;
        this.showBottom = showBottom;
    }

    public void setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
    }

    public void setShowTop(boolean showTop) {
        this.showTop = showTop;
    }

    public void setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
    }

    @Override
    public boolean showTop() {
        return showTop;
    }

    @Override
    public boolean showBottom() {
        return showBottom;
    }

    @Override
    public Drawable getDivider() {
        return dividerDrawable;
    }

    @Override
    public void drawTop(Canvas c, RecyclerView parent, View child) {
        Rect rect = boundsTop(parent,child);

        Drawable mDivider = getDivider();
        mDivider.setBounds(rect);
        mDivider.draw(c);
    }

    @Override
    public void drawBottom(Canvas c,RecyclerView parent, View child) {
        Rect rect = boundsBottom(parent,child);

        Drawable mDivider = getDivider();
        mDivider.setBounds(rect);
        mDivider.draw(c);
    }

    @Override
    public void drawLeft(Canvas c, RecyclerView parent, View child) {
        Rect rect = boundsLeft(parent,child);

        Drawable mDivider = getDivider();
        mDivider.setBounds(rect);
        mDivider.draw(c);
    }

    @Override
    public void drawRight(Canvas c, RecyclerView parent, View child) {
        Rect rect = boundsRight(parent,child);

        Drawable mDivider = getDivider();
        mDivider.setBounds(rect);
        mDivider.draw(c);
    }


    private Rect boundsBottom(RecyclerView parent, View child){
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        Drawable dividerDrawable = getDivider();
        int dividerSize = dividerDrawable.getIntrinsicHeight();

        bounds.left = parent.getPaddingLeft() + transitionX;
        bounds.right = parent.getWidth() - parent.getPaddingRight() + transitionX;

        bounds.top = child.getBottom() + params.bottomMargin + transitionY;
        bounds.bottom = bounds.top + dividerSize;
        return bounds;
    }


    private Rect boundsTop( RecyclerView parent, View child){
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        Drawable dividerDrawable = getDivider();
        int dividerSize = dividerDrawable.getIntrinsicHeight();

        bounds.left = parent.getPaddingLeft() + transitionX;
        bounds.right = parent.getWidth() - parent.getPaddingRight() + transitionX;

        bounds.top = child.getTop() - params.topMargin + transitionY-dividerSize;
        bounds.bottom = child.getTop();
        return bounds;
    }

    private Rect boundsLeft( RecyclerView parent, View child){
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        Drawable dividerDrawable = getDivider();
        int dividerSize = dividerDrawable.getIntrinsicHeight();

        bounds.left = child.getLeft() + transitionX - dividerSize - params.leftMargin;
        bounds.right = child.getLeft() + transitionX ;

        bounds.top = child.getTop()+ transitionY + parent.getPaddingTop();
        bounds.bottom = child.getBottom()  + transitionY - parent.getPaddingBottom();
        return bounds;
    }


    private Rect boundsRight( RecyclerView parent, View child){
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        Drawable dividerDrawable = getDivider();
        int dividerSize = dividerDrawable.getIntrinsicHeight();

        bounds.left = child.getRight() + transitionX + params.rightMargin;
        bounds.right = child.getRight() + transitionX + params.rightMargin + dividerSize;

        bounds.top = child.getTop()+ transitionY + parent.getPaddingTop();
        bounds.bottom = child.getBottom()  + transitionY - parent.getPaddingBottom();
        return bounds;
    }
}
