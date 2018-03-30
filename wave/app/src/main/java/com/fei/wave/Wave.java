package com.fei.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 创建日期：2018/3/29 on 11:47
 * 描述:
 * 作者:Li
 */

public class Wave extends View {
    private Paint mPaintRed;
    private Paint mPaintBlue;
    private int mWaveLength ;
    private int mOffset;
    private ValueAnimator mValueAnimator;

    private String TAG = Wave.class.getSimpleName();
    public Wave(Context context) {
        super(context);
        init();
    }

    public Wave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Log.d(TAG,"init");
        mPaintRed = new Paint();
        mPaintRed.setColor(ActivityCompat.getColor(getContext(),R.color.color1));
        mPaintBlue = new Paint();
        mPaintBlue.setColor(ActivityCompat.getColor(getContext(),R.color.color2));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow");
        //当前的view从window上分离时会调用该方法
        if (mValueAnimator != null){
            mValueAnimator.cancel();
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG,"onLayout");
        super.onLayout(changed, left, top, right, bottom);
        //波长大一点才看起来波动的真
        mWaveLength = getWidth()*2;
        //onLayout在view的创建中会多次调用
        updateX();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //总共画两个波长，从左边-1个波长开始画
        drawWave(canvas, -1 * mWaveLength + mOffset,getHeight()/2, mPaintRed);
        //要错落的话 就需要起始位置不一样
        drawWave(canvas,  (int)(-1.5 * mWaveLength)  + mOffset,getHeight()/2,mPaintBlue);
    }

    private void drawWave(Canvas canvas,int startX,int startY,Paint paint){
        Path path = new Path();
        path.moveTo(startX,startY);
        //每次只画半个波长
        //循环4次就是画两个波长的长度
        for (int i = 0 ; i < 4;i++){
            int thisStartX = startX + i* mWaveLength/2;
            int thisStartY = startY;
            int thisMiddleX  = thisStartX + mWaveLength/4;
            int thisMiddleY  ;
            int thisEndX  =thisStartX + mWaveLength/2;
            int thisEndY  = thisStartY;
            if (i % 2 == 0){
                thisMiddleY = thisStartY - getHeight()/4;
            }else {
                thisMiddleY = thisStartY + getHeight()/4;
            }
            path.quadTo(thisMiddleX,thisMiddleY,thisEndX,thisEndY);
//            Log.d(TAG,String.format("thisStartX %d ,thisStartY %d thisMiddleX %d thisMiddleY %d  thisEndX %d thisEndY %d",thisStartX,thisStartY,thisMiddleX,thisMiddleY,thisEndX,thisEndY));
        }
        path.lineTo(getWidth(),getHeight());
        path.lineTo(startX,getHeight());
        canvas.drawPath(path, paint);

    }


    private void updateX(){
        if (mValueAnimator == null){
            Log.d(TAG,"updateX mValueAnimator init");
            mValueAnimator = ValueAnimator.ofFloat(0,mWaveLength);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mOffset = (int) value;
                    postInvalidate();
                }
            });
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setDuration(1000);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (!mValueAnimator.isStarted()&& !mValueAnimator.isRunning()){
            Log.d(TAG,"updateX start");
            mValueAnimator.start();
        }else {
            Log.d(TAG,"updateX already start");
        }
    }





}
