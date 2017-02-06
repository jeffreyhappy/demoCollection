package com.wswy.risenumber;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * 未完成版 现在只能显示 不能动
 * Created by Li on 2016/7/19.
 */
public class NumTextView  extends LinearLayout {
    private static final int STOPPED = 0;

    private static final int RUNNING = 1;

    private int mPlayingState = STOPPED;



    private long duration=1500;
//
    private DecimalFormat fnum;

//    /**
//     * 最后一次的text的数量
//     */
//    private int lastTextNum;
    /**
     * 新的text
     */
    private int number;

    /**
     * 最后一次的text
     */
    private String lastText;

    private float textSize; //px
    private int textColor;


    final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE };


    final int[] customAttrs = {android.R.attr.text,android.R.attr.textSize,android.R.attr.textColor};
    public NumTextView(Context context) {
        super(context);
    }

    public NumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
    }

    public NumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs);
    }

    /**
     * 没找到好办法 ，只有忽略了
     * @param context
     * @param attrs
     */
    @SuppressWarnings("ResourceType")
    private void initAttr(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,customAttrs);
        String strText = a.getString(0);
        textSize = a.getDimension(1, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,getResources().getDisplayMetrics()));
        textColor = a.getColor(2, ActivityCompat.getColor(getContext(),R.color.white));
        if (!TextUtils.isEmpty(strText)){
            lastText = strText;
            text(strText,textSize,textColor);
        }
    }

    private void text(String text, float textSize, int textColor){

        setOrientation(LinearLayout.HORIZONTAL);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
        for (int i = 0 ; i < text.length() ; i++){
            TextView tv = new TextView(getContext());
            tv.setBackgroundResource(R.drawable.shape_green);
            tv.setPadding(padding,padding,padding,padding);
            LinearLayout.LayoutParams  lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,margin,0);
            tv.setText(text.substring(i,i+1));
            tv.setLayoutParams(lp);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            tv.setTextColor(textColor);
            addView(tv);
        }
    }


    public void setTextWithNumber(int number){
        withNumber(number).start();
    }
    private void setText(String text){
        int lastTextNum = lastText.length();
//        lastText = text;
        if (lastTextNum != text.length()){

            removeAllViews();
            setOrientation(LinearLayout.HORIZONTAL);
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
            for (int i = 0 ; i < text.length() ; i++){
                TextView tv = new TextView(getContext());
                tv.setBackgroundResource(R.drawable.shape_green);
                tv.setPadding(padding,padding,padding,padding);
                LinearLayout.LayoutParams  lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,margin,0);
                tv.setText(text.substring(i,i+1));
                tv.setLayoutParams(lp);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
                tv.setTextColor(textColor);
                addView(tv);
            }
        }else {
            for (int i = 0 ; i < lastTextNum ;i++){
                TextView tv = (TextView) getChildAt(i);
                tv.setText(text.substring(i,i+1));
            }
        }
    }

    /**
     *
     * @param number  新的数量
     * @return
     */
    private NumTextView withNumber(int number) {

        this.number=number;
//        numberType=2;

        return this;
    }

    public void start() {
        setText(fnum.format(number));
        if (!isRunning()) {
            mPlayingState = RUNNING;
            runFloat();
        }
    }

    public boolean isRunning() {
        return (mPlayingState == RUNNING);
    }

    static int sizeOfInt(int x) {
        for (int i = 0;; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }


    private void runFloat(){
        if (TextUtils.isEmpty(lastText)){
            lastText = "0";
        }
        setText(fnum.format(number));
//
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(Float.parseFloat(lastText), number);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                setText(fnum.format(Float.parseFloat(valueAnimator.getAnimatedValue().toString())));
                if (valueAnimator.getAnimatedFraction()>=1){
                    mPlayingState = STOPPED;
                    lastText = String.valueOf(number);
                }
            }


        });
        valueAnimator.start();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fnum=   new DecimalFormat("##0");
    }

    public String getLastText() {
        return lastText;
    }
}
