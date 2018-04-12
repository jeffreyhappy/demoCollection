## 实现一个简单版的ScrollView
复习一遍View的相关方法
1.  宽高测量
1.  子View的布局
1.  跟随手指滑动  
1.  快速滑动后会有惯性滑动
1.  滑动不能越界


###测量宽高
不重写onMeasure的话，如果我们在xml的宽高写的是固定值如100dp，那么我们的宽高是100dp，因为测量模式是这个MeasureSpec.EXACTLY
但是如果宽高写的wrap_content，那么ScrollerLayout会占满父控件。
因为wrap_content对应的测量模式和match_parent一样都是MeasureSpec.AT_MOST。这个MeasureSpec.AT_MOST是在onMeasure中确定最终的宽高要用到的
我们期望的宽高是：
宽：min(屏幕宽度，最宽的子View的宽度)
高：min(屏幕高度，子View高度的和)
结合xml中设定的值
宽：
    
    if(宽度固定值){
      return 宽度固定值
    }else if(wrap_content){
      return min(屏幕宽度，最宽的子View的宽度)  
    }else if(match_parent){
      return 屏幕宽度
    }

高：
    
    if(高度固定值){
        return 高度固定值
      }else if(wrap_content){
        return min(屏幕高度，子View高度的和)  
      }else if(match_parent){
        return 屏幕高度
      }
      
具体代码如下

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int childCount = getChildCount();
            int childMaxWidth = 0;
            int childTotalHeight = 0;
            for (int i = 0 ; i < childCount ; i++){
                measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
                int childWidth  = getChildAt(i).getMeasuredWidth();
                int childHeight  = getChildAt(i).getHeight();
                if (childWidth > childMaxWidth){
                    childMaxWidth = childWidth;
                }
                childTotalHeight += childHeight;
            }
            mTotalChildHeight = childTotalHeight;
    
            int ensureWidth  = MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            if (widthMode == MeasureSpec.AT_MOST){
                ensureWidth = childMaxWidth;
            }
            //在安卓艺术探索中，AT_MOST的意思是想要多大就多大，就是wrap_content
            //而exactly就是准确的，固定的数值和match_parent就是固定的
            int ensureHeight = MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (heightMode == MeasureSpec.AT_MOST){
                ensureHeight = childTotalHeight;
            }
    
            setMeasuredDimension(ensureWidth,ensureHeight);
        }


#### 子View的布局
子View的布局是在ViewGroup的onLayout中完成的,需要确保已经在onMeasure中调用measureChild来确定子View的测量宽高，不然子View宽高都是0

    @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            if (changed){
                //竖向滑动，所以子View是竖着排列的
                int childCount = getChildCount();
                int top = 0;
                for (int i = 0 ; i < childCount ;i ++){
                    View childView  = getChildAt(i);
                    childView.layout(0,top,childView.getMeasuredWidth(),top+childView.getMeasuredHeight());
                    top += childView.getMeasuredHeight();
                }
            }
        }
        


#### 跟随手指滑动  
需要处理点击事件的分发
View的点击事件入口是dispatchTouchEvent，在dispatchTouchEvent中会调用自己的onInterceptTouchEvent来判断是否拦截这次的事件，如果不拦截就遍历子View，交给子View的dispatchTouchEvent

    public boolean dispatchTouchEvent(MotionEvent ev) {
        ··········
        // Check for interception.
        final boolean intercepted;
        if (actionMasked == MotionEvent.ACTION_DOWN
                        || mFirstTouchTarget != null) {
            final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
            if (!disallowIntercept) {
                 //先交给自己的onInterceptTouchEvent
                        intercepted = onInterceptTouchEvent(ev);
                        ev.setAction(action); // restore action in case it was changed
            } else {
                        intercepted = false;
            }
        } else {
                    // There are no touch targets and this action is not an initial down
                    // so this view group continues to intercept touches.
                    intercepted = true;
        }    
        ............
        //如果自己的onInterceptTouchEvent返回了false，在遍历ziView处理
        if (!canceled && !intercepted) {
            ..........
             final int childrenCount = mChildrenCount;
             if (newTouchTarget == null && childrenCount != 0) {
                 for (int i = childrenCount - 1; i >= 0; i--) {
                    final int childIndex = getAndVerifyPreorderedIndex(
                                            childrenCount, i, customOrder);
                    final View child = getAndVerifyPreorderedView(
                                            preorderedList, children, childIndex);   
                    ........
                    //这里面最终调用了 child.dispatchTouchEvent(transformedEvent);
                    if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign))
                    ........ 
                                                 
             }
        }
    }
接管触摸事件有两个方法
    
1 在onInterceptTouchEvent中监听到滑动且滑动距离大于阀值就返回true
2 所有子View都不处理触摸事件的时候，在OnTouch里返回True
接管后，就可以滑动了，滑动用的scrollTo(int x,int y)和scrollBy(int x, int y)，他们都是对自己的内容进行操作的
scrollTo是滑动到指定位置，scrollBy是根据当前位置滑动多少其实scrollBy里面也是调用的scrollTo

简略代码：       

    public boolean onTouchEvent(MotionEvent event){
     ......
     switch (event.getAction()){
        float mYMove = event.getRawY() - mLastY;
        //视图正方向滑动的时候 Y是正数
        //但是手指滑动的差值是负数
        scrollBy(0, (int) mYMove * -1);
        mLastY = event.getRawY();
     }
    }
这里面就是要注意下滑动的方向和mYMove之间的关系

#### 快速滑动后会有惯性滑动
当手指滑动很快的时候，手指离开屏幕后界面还要再惯性滑动下，这里面用到了两个类
1 VelocityTracker  用来计算手指滑动速度 如果大于某个阀值的时候 就需要来惯性滑动
2 Scroller   用来实现惯性新滑动  
VelocityTracker的流程：

    mTracker = VelocityTracker.obtain(); //获取对象
    mTracker.addMovement(event); //把触摸时间记录下来
    mTracker.computeCurrentVelocity(1000,mMaximumVelocity); //计算当前的速度
    mTracker.getYVelocity() //获取Y方向的速度值

Scroller 的流程

    //1 初始化
    mScroller = new Scroller(getContext());
    //2 调用惯性滑动方法
    mScroller.fling(0,getScrollY(),0,(int)mTracker.getYVelocity()*-1,0,0,0,getScrollRange());
    //3 重写computeScroll方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        //4 判断是否计算滑动
        if (mScroller.computeScrollOffset()){
            //5 获取滑动的X,Y距离，然后调用scrollTo
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
    
然后就是把这两个组合起来就可以实现惯性滑动    




#### 滑动不能越界
在使用scrollTo的时候 很容易就把子view滑出界面了。所以需要在滑动边界的时候不调用scrollTo，然后再ScrollView源码里看到了这个
    
    private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0,
                        child.getHeight() - (getHeight() - mPaddingBottom - mPaddingTop));
            }
            return scrollRange;
        }

因为ScrollView就只能有一个子View,所以滑动范围简化来看就是 
   
   *滑动最大距离=子View的高度-ScrollView的高度*

在上面的onMeasure我们已经算出子View的高度和所以ScrollerLayout的滑动范围
    
        /**
         * 从ScrollView代码里找到的 源码牛逼
         * 可以滑动的距离= 内容的总高度-父view的高度
         * @return
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
    
                scrollRange = Math.max(0,
                        mTotalChildHeight - getHeight());
            }
            return scrollRange;
        }

然后在onTouch的ACTION_MOVE事件中
        
                float mYMove = event.getRawY() - mLastY;
                if (mYMove < 0 ){
                    //往上滑动不要超过下边界
                    int range = getScrollRange();
                    if (getScrollY() >= range){
                        scrollTo(0,range);
                        return true;
                    }
                }else{
                    //往下滑动不要超过上边界
                    if (getScrollY() <= 0){
                        scrollTo(0, 0);
                        return true;
                    }
                }      
                  
这样就搞出了个简单版的ScrollView了 