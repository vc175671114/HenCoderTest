package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final int BITMAP_WIDTH = (int) Utils.dp2px(300);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    boolean isBig = false;
    //放大的倍数
    float scaleNum = 1.5f;
    float smallScale;
    float bigScale;
    float offsetX;
    float offsetY;
    GestureDetectorCompat gestureDetector;
    ObjectAnimator objectAnimator;
    OverScroller scroller;
    onFlingRunnable onFlingRunnable;

    float scalingFraction;

    public float getScalingFraction() {
        return scalingFraction;
    }

    public void setScalingFraction(float scalingFraction) {
        this.scalingFraction = scalingFraction;
        invalidate();
    }

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), BITMAP_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        scroller = new OverScroller(context);
        onFlingRunnable = new onFlingRunnable();
    }

    //必须有这个方法才能使gestureDetector获取点击事件的处理权
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //强转成float，是因为取int会影响比较，如1.3和1.4全都取成1
        if ((float) getWidth() / bitmap.getWidth() > (float) getHeight() / bitmap.getHeight()) {
            bigScale = (float) getWidth() / bitmap.getWidth() * scaleNum;
            smallScale = (float) getHeight() / bitmap.getHeight();
        } else {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * scaleNum;
        }
    }

    private ObjectAnimator getAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "scalingFraction", 0f, 1f);
        }
        return objectAnimator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //*scalingFraction是因为要在缩小的时候将偏移变为0
        canvas.translate(offsetX * scalingFraction, offsetY * scalingFraction);
        float scale = smallScale + (bigScale - smallScale) * scalingFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2,
                (getHeight() - bitmap.getHeight()) / 2, paint);
    }

    //onDown的返回值必须改为true，否则不会消费时间，其他的返回值没有用
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    //distanceX手指向右滑，值为负，效果为图像向右移动，translateX为正，
    // 所以offsetX -= distanceX,distanceX为每一次移动的值，不是总值，所以要累加
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        System.out.println("distanceX=" + distanceX);
        if (isBig) {
            offsetX -= distanceX;
            offsetY -= distanceY;
            fixOffset();

            invalidate();
        }
        return false;
    }

    //修复边界
    private void fixOffset() {
        //手指右滑，右移，负值时，取移动和左边界最大值
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        //手指左滑，左移，正值时，取移动和右边界最小值
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);

        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (isBig) {
            int minX = (int) (-(bitmap.getWidth() * bigScale - getWidth()) / 2);
            int maxX = (int) ((bitmap.getWidth() * bigScale - getWidth()) / 2);
            int minY = (int) (-(bitmap.getHeight() * bigScale - getHeight()) / 2);
            int maxY = (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2);

            int overX = (int) Utils.dp2px(50);
            int overY = (int) Utils.dp2px(50);
            //minX和maxX表示左移（负值）和右移（正值）的最大距离;overX表示弹性距离
            //起始原点为offsetX，offsetX。每次移动后都会重新计算
            scroller.fling((int) offsetX, (int) offsetX, (int) velocityX, (int) velocityY,
                    minX, maxX, minY, maxY, overX, overY);

            postOnAnimation(onFlingRunnable);
        }
        return false;
    }

    class onFlingRunnable implements Runnable {

        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        isBig = !isBig;
        if (isBig) {
            //放大前点击位置坐标，减去放大后对应位置的坐标（乘一个放大和缩小的比例）
            offsetX = (event.getX() - getWidth() / 2) - (event.getX() - getWidth() / 2) * bigScale / smallScale;
            offsetY = (event.getY() - getHeight() / 2) - (event.getY() - getHeight() / 2) * bigScale / smallScale;
            fixOffset();
            getAnimator().start();
        } else {
            getAnimator().reverse();
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
