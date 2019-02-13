package www.lixiangfei.top.multiviewlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MultiViewLayout extends ViewGroup {
    final int MAX_COLUMN = 2;
    final int ONE_PAGE_NUM = 4;
    final float DEFAULT_DIVIDER_HEIGHT = 0;
    final int DEFAULT_DIVIDER_COLOR = Color.TRANSPARENT;
    private float mRowDividerHeight;
    private float mColumnDividerHeight;
    private ColorDrawable mRowDividerDrawable;
    private ColorDrawable mColumnDividerDrawable;
    private int contentWidth;
    private int mTouchSlop;
    private float mXDown;
    private float mLastX;

    private boolean fingerLeft2Right = false;

    /**
     * 用来完成惯性滑动
     */
    private Scroller mScroller;


    public MultiViewLayout(Context context) {
        super(context);
        init(context, null);
    }

    public MultiViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller  = new Scroller(context);
        int columnDividerColor;
        int rowDividerColor;
        if (attrs == null) {
            mColumnDividerHeight = DEFAULT_DIVIDER_HEIGHT;
            columnDividerColor = DEFAULT_DIVIDER_COLOR;
            mRowDividerHeight = DEFAULT_DIVIDER_HEIGHT;
            rowDividerColor = DEFAULT_DIVIDER_COLOR;
            mRowDividerDrawable = new ColorDrawable(rowDividerColor);
            mColumnDividerDrawable = new ColorDrawable(columnDividerColor);
        } else {
            TypedArray a =
                    context.obtainStyledAttributes(attrs, R.styleable.MultiViewLayout);
            mColumnDividerHeight = a.getDimension(R.styleable.MultiViewLayout_columnDividerHeight, 0);
            columnDividerColor = a.getColor(R.styleable.MultiViewLayout_columnDividerColor, Color.TRANSPARENT);
            mRowDividerHeight = a.getDimension(R.styleable.MultiViewLayout_rowDividerHeight, 0);
            rowDividerColor = a.getColor(R.styleable.MultiViewLayout_rowDividerColor, Color.TRANSPARENT);
            mRowDividerDrawable = new ColorDrawable(rowDividerColor);
            mColumnDividerDrawable = new ColorDrawable(columnDividerColor);
            a.recycle();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int currentPage = 1;
        int parentWidth = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            child.layout(left, top, left + childWidth, top + childHeight);
            left = (int) (left + childWidth + mColumnDividerHeight);
            //换行
            if (left >= parentWidth*currentPage) {
                left = parentWidth*(currentPage-1);
                top = (int) (top + childHeight + mRowDividerHeight);
            }
            //换页
            if ((i+1) % ONE_PAGE_NUM == 0 && i != 0 && i != getChildCount() -1) {
                currentPage++;
                left = getWidth() * (currentPage - 1);
                top = 0;
            }
            contentWidth = getMeasuredWidth() * currentPage;
        }
        ensureScrollWhenLayoutChange();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int ensureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int ensureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int visibleChild = getChildCount();

        //测量子View宽高
        if (ensureHeight > 0 && ensureWidth > 0 && visibleChild > 0) {
            //行
            int row = visibleChild / MAX_COLUMN;
            if (visibleChild % MAX_COLUMN > 0) {
                row++;
            }
            //最后一行的列
            int lastColumn = visibleChild % MAX_COLUMN;
            //只有一行的时候,特殊处理下
            if (row == 1) {
                int childHeightSpec = MeasureSpec.makeMeasureSpec(ensureHeight, MeasureSpec.EXACTLY);
                int childWidthSpec = MeasureSpec.makeMeasureSpec(ensureWidth, MeasureSpec.EXACTLY);
                //只有1个子元素
                if (lastColumn == 1) {
                    getChildAt(0).measure(childHeightSpec, childWidthSpec);
                } else {
                    //一行的话就平分
                    doMeasureChild(ensureHeight, ensureWidth, visibleChild, 1);
                }
            } else {
                doMeasureChild(ensureHeight, ensureWidth, MAX_COLUMN, ONE_PAGE_NUM/MAX_COLUMN);
            }
        }

        //自己的宽高设置下
        setMeasuredDimension(ensureWidth, ensureHeight);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mLastX = mXDown;

                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = ev.getRawX();
                if (Math.abs(mLastX - mXDown) > mTouchSlop) {
                    return true;
                }
                break;
        }
        boolean returnResult = super.onInterceptTouchEvent(ev);

        return returnResult;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                float mXMove = event.getRawX() - mLastX;
                if (mXMove < 0) {
                    fingerLeft2Right = false;
                    //往右滑动不要超过下边界
                    int maxScrollWidth = contentWidth - getMeasuredWidth();
                    if (getScrollX() >= maxScrollWidth) {
                        scrollTo(maxScrollWidth, 0);
                        return true;
                    }
                } else {
                    fingerLeft2Right = true;
                    //往左滑动不要超过上边界
                    if (getScrollX() <= 0) {
                        scrollTo(0, 0);
                        return true;
                    }
                }
                //视图正方向滑动的时候 Y是正数
                //但是手指滑动的差值是负数
                scrollBy((int) mXMove * -1, 0);
                mLastX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:

                int haveScrollX = getScrollX();
                int scrollXInOnePage = haveScrollX % getWidth();
                int scrollSize ;
                if (fingerLeft2Right){
                    if (scrollXInOnePage < 2 * getWidth()/3){
                        scrollSize = -1 * scrollXInOnePage;
                    }else {
                        scrollSize = getWidth()-scrollXInOnePage;
                    }
                }else {
                    if (scrollXInOnePage < getWidth()/3){
                        scrollSize = -1 * scrollXInOnePage;
                    }else {
                        scrollSize = getWidth()-scrollXInOnePage;
                    }
                }
//                if (scrollXInOnePage < getWidth()/3){
//                    scrollSize = -1 * scrollXInOnePage;
//                }else {
//                    scrollSize = getWidth()-scrollXInOnePage;
//                }
                mScroller.startScroll(getScrollX(),0,scrollSize,0,300);
                invalidate();
                break;
        }

        /**
         * 作为事件的终结者，如果有子View消费了事件，就不会走到这里，子View什么都没干，ScrollerLayout来接管
         */
        return true;
    }

    private void doMeasureChild(int ParentHeight, int ParentWidth, int column, int row) {
        int canUseHeight = (int) (ParentHeight - (row - 1) * mRowDividerHeight);
        int canUseWidth = (int) (ParentWidth - (column - 1) * mColumnDividerHeight);
        int childHeightSpec = MeasureSpec.makeMeasureSpec(canUseHeight / row, MeasureSpec.EXACTLY);
        int childWidthSpec = MeasureSpec.makeMeasureSpec(canUseWidth / column, MeasureSpec.EXACTLY);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthSpec, childHeightSpec);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //这里是画分割线
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getRight() != getMeasuredWidth()) {
                mColumnDividerDrawable.setBounds(childView.getRight(), childView.getTop(), (int) (childView.getRight() + mColumnDividerHeight), childView.getBottom());
                mColumnDividerDrawable.draw(canvas);
            }

            if (childView.getBottom() != getMeasuredHeight() && childView.getLeft() == 0) {
                mRowDividerDrawable.setBounds(childView.getLeft(), childView.getBottom(), getMeasuredWidth(), (int) (childView.getBottom() + mRowDividerHeight));
                mRowDividerDrawable.draw(canvas);
            }
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void ensureScrollWhenLayoutChange(){
        if (contentWidth == 0 && getScrollX() == 0){
            return;
        }

        if (contentWidth <= getScrollX()){
            mScroller.startScroll(getScrollX(),0,-1 * getMeasuredWidth(),0);
        }
    }

    private int getScrollRange() {
        return getWidth();
    }
}
