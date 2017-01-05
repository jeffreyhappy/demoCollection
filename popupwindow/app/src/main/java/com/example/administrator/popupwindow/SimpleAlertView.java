package com.example.administrator.popupwindow;

/**
 * Created by Li on 2017/1/3.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 只支持在底部弹出,还有个坑，返回键要自己处理
 * Created by Sai on 15/8/9.
 * 精仿iOSAlertViewController控件
 * 点击取消按钮返回 －1，其他按钮从0开始算
 */
public class SimpleAlertView {
    public enum Style{
        ActionSheet,
        Alert
    }
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    public static final int HORIZONTAL_BUTTONS_MAXCOUNT = 2;
    public static final int CANCELPOSITION = -1;//点击取消按钮返回 －1，其他按钮从0开始算


    private String cancel;
    private ArrayList<String> mDatas = new ArrayList<String>();

    protected WeakReference<Context> contextWeak;
    private ViewGroup contentContainer;
    private ViewGroup decorView;//fragment的根View
    private ViewGroup rootView;//AlertView 的 根View
    private DialogFragment dialogFragment;//所依附的dialogfragment
    private Style style = Style.Alert;

    private boolean isShowing;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.CENTER;



    public SimpleAlertView(DialogFragment dialogFragment , Context context, ViewGroup container){
        this.contextWeak = new WeakReference<>(context);
        this.decorView = container;
        this.dialogFragment = dialogFragment;
        initViews();
        init();
        initEvents();
    }


    protected void initViews(){
        Context context = contextWeak.get();
        if(context == null) return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        decorView = (ViewGroup) ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_popup_view, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
                params.gravity = Gravity.BOTTOM;
                contentContainer.setLayoutParams(params);
                gravity = Gravity.BOTTOM;
                initActionSheetViews(layoutInflater);


    }

    public int getContentLayoutId(){
        return R.layout.layout_child_view;
    }

    public void bindContent(ViewGroup viewGroup){

    }

    protected void initActionSheetViews(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(getContentLayoutId(),contentContainer);
        bindContent(viewGroup);

    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }
    protected void initEvents() {
    }


    public View getView(){
        return rootView;
    }
    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        isShowing = true;
//        decorView.addView(view);
        contentContainer.startAnimation(inAnim);
    }
    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }
    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null && isShowing;
    }

    public void dismiss() {
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        contentContainer.startAnimation(outAnim);
    }

    public void dismissImmediately() {
//        decorView.removeView(rootView);
//        isShowing = false;

        dialogFragment.dismissAllowingStateLoss();

    }

    public Animation getInAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

        int res = getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

        int res = getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

//    public SimpleAlertView setOnDismissListener(OnDismissListener onDismissListener) {
//        this.onDismissListener = onDismissListener;
//        return this;
//    }

//    class OnTextClickListener implements View.OnClickListener{
//
//        private int position;
//        public OnTextClickListener(int position){
//            this.position = position;
//        }
//        @Override
//        public void onClick(View view) {
//            if(onItemClickListener != null)onItemClickListener.onItemClick(this,position);
//            dismiss();
//        }
//    }
    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            dismissImmediately();
            dialogFragment.dismiss();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 主要用于拓展View的时候有输入框，键盘弹出则设置MarginBottom往上顶，避免输入法挡住界面
     */
//    public void setMarginBottom(int marginBottom){
//        Context context = contextWeak.get();
//        if(context == null) return;
//
//        int margin_alert_left_right = context.getResources().getDimensionPixelSize(com.bigkoo.alertview.R.dimen.margin_alert_left_right);
//        params.setMargins(margin_alert_left_right,0,margin_alert_left_right,marginBottom);
//        contentContainer.setLayoutParams(params);
//    }
    public SimpleAlertView setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        }
        else{
            view.setOnTouchListener(null);
        }
        return this;
    }


    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };



    static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom :R.anim.slide_out_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.fade_in_center : R.anim.fade_out_center;
        }
        return -1;
    }
}

