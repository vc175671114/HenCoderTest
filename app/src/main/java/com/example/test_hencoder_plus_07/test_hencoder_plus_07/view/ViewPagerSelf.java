package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class ViewPagerSelf extends ViewGroup {

    boolean scrolling;

    OverScroller overScroller;
    ViewConfiguration viewConfiguration;
    //速度追踪器,实时追踪速度
    VelocityTracker velocityTracker = VelocityTracker.obtain();
    float minVelocity;
    float maxVelocity;

    public ViewPagerSelf(Context context, AttributeSet attrs) {
        super(context, attrs);

        overScroller = new OverScroller(context);
        viewConfiguration = ViewConfiguration.get(context);
        //获取最小的快速滑动速度
        minVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 0) {
                child.layout(0, 0, getWidth(), getHeight());
            } else {
                child.layout(getWidth(), 0, getWidth() * 2, getHeight());
            }
        }
    }

    float downX;
    float originalOffsetX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //每次按下的时候去做速度重置
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        //一旦有新的事件，就存起来，当你想要知道速度的时候，就可以调用他去获得
        velocityTracker.addMovement(event);
        boolean result = false;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                scrolling = false;
                downX = event.getX();
                originalOffsetX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                float scrollX = downX - event.getX();
                if (!scrolling) {
                    //判断滑动的距离是否足够大到可以视为滑动
                    if (Math.abs(scrollX) > viewConfiguration.getScaledPagingTouchSlop()) {
                        scrolling = true;
                        //通知父view不要拦截
                        getParent().requestDisallowInterceptTouchEvent(true);
                        result = true;
                    }
                }
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //每次按下的时候去做速度重置
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        //一旦有新的事件，就存起来，当你想要知道速度的时候，就可以调用他去获得
        velocityTracker.addMovement(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                originalOffsetX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                //因为是使用scrollTo方法，所以此时要加原始偏移，而不是减
                float scrollX = downX - event.getX() + originalOffsetX;
                if (scrollX > getWidth()) {
                    scrollX = getWidth();
                } else if (scrollX < 0) {
                    scrollX = 0;
                }
                scrollTo((int) scrollX, 0);
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 如果不想计算速度，也可以用ScaleGestureDetector中的onScroll和onFling
                 */
                //计算速度
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                //获取横向速度
                float vx = velocityTracker.getXVelocity();
                scrollX = getScrollX();
                int targetPage;
                //minVelocity表示惯性滑动的最小值，如果小于这个值则认为惯性滑动停止了
                if (Math.abs(vx) < minVelocity) {
                    //如果不是快滑，用这个判断
                    targetPage = scrollX > getWidth() / 2 ? 1 : 0;
                } else {
                    //如果是快滑，用这个判断：
                    // 向左滑，速度小于0，落到第二页；
                    // 向右滑，速度大于0，落到第一页。
                    targetPage = vx < 0 ? 1 : 0;
                }
                int scrollDistance = targetPage == 1 ? (getWidth() - (int) scrollX) : -(int) scrollX;
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0);
                postInvalidateOnAnimation();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.getCurrX(), 0);
            postInvalidateOnAnimation();
        }
    }
}
