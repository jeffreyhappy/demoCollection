package com.jeffrey.color.divider;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Li on 2017/2/28.
 */

public interface DividerHelperInterface {
    boolean showTop();
    boolean showBottom();
    Drawable getDivider();

    void drawTop(Canvas c, RecyclerView parent, View child);
    void drawBottom(Canvas c,RecyclerView parent, View child);
    void drawLeft(Canvas c,RecyclerView parent, View child);
    void drawRight(Canvas c,RecyclerView parent, View child);

}
