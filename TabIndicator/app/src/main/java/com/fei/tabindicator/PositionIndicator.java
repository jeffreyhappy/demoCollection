package com.fei.tabindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建日期：2018/3/26 on 13:46
 * 描述:
 * 作者:Li
 */

public class PositionIndicator extends View {
    private int mLeft = 0;
    private int mRight = 0;
    private Paint mPaint;
    private int mColor;
    public PositionIndicator(Context context) {
        super(context);
        init();
    }

    public PositionIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PositionIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setRect(int left,int right){
        this.mLeft = left;
        this.mRight = right;
        invalidate();
    }


    public void setColor(int mColor) {
        this.mColor = mColor;
        this.mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mColor == 0){
            return;
        }
        canvas.drawRect(mLeft,0,mRight,getHeight(),mPaint);
    }

    private void init(){
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }
}
