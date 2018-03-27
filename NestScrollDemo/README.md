![截图](https://github.com/jeffreyhappy/demoCollection/blob/master/NestScrollDemo/ScreenRecord.gif)  

## 使用NestedScrollView优化嵌套RecyclerView
在开发中经常会遇到ScrollView嵌套RecyclerView的情况。
例如界面需要一个banner，一段介绍文字，还有个列表，banner要可以划出界面，介绍文字要滑动后固定在顶部

开发中有两种解决办法 ：  
1 整个页面使用RecyclerView,根据类型返回不同的ViewHolder，这也是我正常用的，这次学习下下面的方法
2 使用NestedScrollView 包裹RecyclerView.(这个可以直接使用，但是需要点小优化)

##### NestedScrollView
NestedScrollView 和scrollView一样的使用，直接包裹一个子控件就可以了，它实现了 NestedScrollingParent, NestedScrollingChild2这两个方法

实现NestedScrollingParent的意思就是 我是个嵌套滑动的父控件，我可以和子滑动控件一起处理滑动事件。NestedScrollView嵌套RecyclerView主要就是关注这个
实现NestedScrollingChild2的意思是  我是个嵌套滑动的子控件，我滑动的时候要告诉父嵌套滑动控件，滑动之前要问问他是否消耗滑动事件。消耗掉的话 我就不滑动了，这个是NestedScrollView作为子控件的时候关注的

而RecyclerView则是实现了NestedScrollingChild2 他只能作为滑动的嵌套子控件

    在滑动前通知父控件，如果父控件消耗了滑动距离 则返回的consumed里面的值就不为0
    abstract boolean	dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type)
    Dispatch one step of a nested scroll in progress before this view consumes any portion of it.
    
    滑动的时候告诉父控件，因为NestedScrollView和RecylerView里面已经处理好了，我们这次没用到
    abstract boolean	dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type)
    Dispatch one step of a nested scroll in progress.
    
    是否有嵌套的滑动父控件
    abstract boolean	hasNestedScrollingParent(int type)
    Returns true if this view has a nested scrolling parent for the given input type.
    
    告诉父控件开始滑动了，如果有父滑动控件，并且父滑动控件想要和子控件一起处理滑动的话，就会返回True
    abstract boolean	startNestedScroll(int axes, int type)
    Begin a nestable scroll operation along the given axes, for the given input type.
    
    停止嵌套滑动了
    abstract void	stopNestedScroll(int type)
    Stop a nested scroll in progress for the given input type.

RecyclerView中有个成员变量

    private NestedScrollingChildHelper mScrollingChildHelper;
    private NestedScrollingChildHelper getScrollingChildHelper() {
            if (mScrollingChildHelper == null) {
                mScrollingChildHelper = new NestedScrollingChildHelper(this);
            }
            return mScrollingChildHelper;
        }
        
对应的NestedScrollView里面
    
    private final NestedScrollingParentHelper mParentHelper;
    mParentHelper = new NestedScrollingParentHelper(this);

NestedScrollingChildHelper和NestedScrollingParentHelper都是系统提供的帮助类，已经封装好滑动调用逻辑，我们的关注点其实是在接口的回调上面。例如NestedScrollView的接口NestedScrollingParent

    abstract int	getNestedScrollAxes()
    Return the current axes of nested scrolling for this NestedScrollingParent.
    
    abstract boolean	onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
    Request a fling from a nested scroll.
    
    abstract boolean	onNestedPreFling(View target, float velocityX, float velocityY)
    React to a nested fling before the target view consumes it.
    
    abstract void	onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    React to a nested scroll in progress before the target view consumes a portion of the scroll.
    
    abstract void	onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    React to a nested scroll in progress.
    
    abstract void	onNestedScrollAccepted(View child, View target, int axes)
    React to the successful claiming of a nested scroll operation.
    
    abstract boolean	onStartNestedScroll(View child, View target, int axes)
    React to a descendant view initiating a nestable scroll operation, claiming the nested scroll operation if appropriate.
    
    abstract void	onStopNestedScroll(View target)
    React to a nested scroll operation ending.

要实现效果的话：
1 当banner在顶部的时候 不管手指在哪滑动，都是NestedScrollView滑动
2 当banner已经划过顶部的时候，手指在RecyclerView中滑动的时候，是RecyclerView滑动

我们demo中这个阀值就是banner的高度，上面说的是相应切换，其实并没有，只是父控件有没有消耗掉滑动距离的问题。子控件滑动前都会告诉父控件，父控件消耗掉了话，子控件就不做响应
在RecyclerView的OnTouchEvent中
     
     if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset, TYPE_TOUCH)) {
        dx -= mScrollConsumed[0];
        dy -= mScrollConsumed[1];
        vtev.offsetLocation(mScrollOffset[0], mScrollOffset[1]);
        // Updated the nested offsets
        mNestedOffsets[0] += mScrollOffset[0];
        mNestedOffsets[1] += mScrollOffset[1];
     }

dx和dy都要减去父控件消耗的距离，如果父控件把滑动距离全消耗掉了的话，那么RecyclerView就不会滑动了
我继承了NestedScrollView并重写了OnNestedPreScroll,逻辑是如果NestedScrollView的滑动距离没有超过阀值，NestedScrollView就消耗掉全部的距离，超过了就全交给子控件自己处理。
只要做这一件事就可以了  就是这么简单
      public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
            super.onNestedPreScroll(target, dx, dy, consumed);
                
            if (mScrollY < mParentScrollHeight) {
                consumed[0] = dx;
                consumed[1] = dy;
                scrollBy(0, dy);
            }
    
            Log.d(TAG,"dx " + dx + " dy "+ dy +  " " + consumed[0]  + " " + consumed[1] + " scrollY " + mScrollY);
        }

还有个问题是NestedScrollView嵌套RecyclerView的话，滑动问题解决了，但是RecyclerView会绘制出所有的item,如果列表很大的话就完蛋了，所以我们需要固定RecyclerView的高度。
高度就是rootView的高度-栏目类型view的高度

     rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                  rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                  int rvNewHeight = rootView.getHeight() - topView2.getHeight();
                  rv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,rvNewHeight));
     }
     
另外还遇到个问题，NestedScrollView嵌套RecyclerView时，固定高度后打开界面时会自动滑到底部。只需要在NestedScrollView的子view中加入        android:descendantFocusability="blocksDescendants"















