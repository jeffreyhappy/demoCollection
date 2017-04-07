package com.jeffrey.color.divider;

import android.graphics.drawable.Drawable;

/**
 * Created by Li on 2017/2/28.
 */

public class DividerBuilder {
    private boolean showTop = false;
    private boolean showBottom = false;
    private Drawable divider ;


    public DividerBuilder showTop(boolean showTop){
        this.showTop = showTop;
        return this;
    }


    public DividerBuilder showBottom(boolean showTop){
        this.showTop = showTop;
        return this;
    }


    public DividerBuilder divider(Drawable  drawable){
        this.divider = drawable;
        return this;
    }


    public SimpleItemDecoration build(){
        DividerHelper helper = new DividerHelper(divider,showTop,showBottom);
        SimpleItemDecoration simpleItemDecoration = new SimpleItemDecoration(helper);
        return simpleItemDecoration;
    }
}
